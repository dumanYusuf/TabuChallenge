package com.dumanyusuf.tabuchallenge.presentation.game_screan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.model.Words
import com.dumanyusuf.tabuchallenge.domain.use_case.game_screan.GameScreanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameScreanUseCase: GameScreanUseCase
) : ViewModel() {
    private val TAG = "GameViewModel"
    // İlk takımdan alınan kelimeleri saklayacağımız liste
    private var savedWords: List<Words> = emptyList()
    private val _navigateToNextTeam = MutableStateFlow(false)
    val navigateToNextTeam: StateFlow<Boolean> = _navigateToNextTeam

    private val _navigateToWinTeam = MutableStateFlow(false)
    val navigateToWinTeam: StateFlow<Boolean> = _navigateToWinTeam

    private var isSecondTeam = false
    private var firstTeamScore = 0
    
    private val _currentTeam = MutableStateFlow<TeamName?>(null)
    val currentTeam: StateFlow<TeamName?> = _currentTeam

    private val _wordsState = MutableStateFlow<List<Words>>(emptyList())
    val wordsState: StateFlow<List<Words>> = _wordsState

    private val _currentWordIndex = MutableStateFlow(0)
    val currentWordIndex: StateFlow<Int> = _currentWordIndex

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _passCount = MutableStateFlow<Int>(0)
    val passCount: StateFlow<Int> = _passCount

    private val _time = MutableStateFlow<Int>(0)
    val time: StateFlow<Int> = _time.asStateFlow()

    private val _isGamePaused = MutableStateFlow(false)
    val isGamePaused: StateFlow<Boolean> = _isGamePaused.asStateFlow()

    private var timerJob: kotlinx.coroutines.Job? = null

    init {
        fetchWordsForFirstTeam()
    }

    private fun fetchWordsForFirstTeam() {
        Log.d(TAG, "fetchWordsForFirstTeam() çağrıldı - İlk takım için kelimeler alınıyor")
        viewModelScope.launch {
            try {
                Log.d(TAG, "Firebase'den kelimeler isteniyor...")
                savedWords = gameScreanUseCase.getWords()
                Log.d(TAG, "Firebase'den alınan kelimeler savedWords'e kaydedildi: $savedWords")

                _wordsState.value = savedWords.shuffled()
                Log.d(TAG, "İlk takım için kelimeler karıştırıldı ve _wordsState'e atandı: ${_wordsState.value}")
            } catch (e: Exception) {
                Log.e(TAG, "Kelimeler alınırken hata oluştu: ${e.message}")
            }
        }
    }

    private fun shuffleExistingWordsForSecondTeam() {
        _wordsState.value = savedWords.shuffled()
        Log.d(TAG, "İkinci takım için kelimeler karıştırıldı ve _wordsState güncellendi: ${_wordsState.value}")
    }

    fun timerGame(gameSettings: GameSettings, secondTeam: Boolean = false, firstTeamScore: Int = 0) {
        Log.d(TAG, "timerGame() çağrıldı - secondTeam: $secondTeam, firstTeamScore: $firstTeamScore")
        isSecondTeam = secondTeam
        this.firstTeamScore = firstTeamScore

        if (secondTeam) {
            Log.d(TAG, "İkinci takım başlıyor - Mevcut kelimeler karıştırılacak")
            shuffleExistingWordsForSecondTeam()
            _currentWordIndex.value = 0
            Log.d(TAG, "İkinci takım için currentWordIndex sıfırlandı")
            // İkinci takımın bilgilerini güncelle
            _currentTeam.value = TeamName(teamName = team2Name)
        } else {
            Log.d(TAG, "İlk takım başlıyor")
            // İlk takımın bilgilerini güncelle
            _currentTeam.value = TeamName(teamName = team1Name)
        }

        startTimer(gameSettings.gameTime)
    }

    private fun startTimer(initialTime: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _time.value = initialTime
            while (_time.value > 0) {
                if (!_isGamePaused.value) {
                    delay(1000)
                    _time.value--
                } else {
                    delay(100)
                }
            }

            if (isSecondTeam) {
                _navigateToWinTeam.value = true
            } else {
                _navigateToNextTeam.value = true
            }
        }
    }

    fun pauseGame() {
        _isGamePaused.value = true
    }

    fun resumeGame() {
        _isGamePaused.value = false
    }

    private var team1Name = ""
    private var team2Name = ""

    fun setTeamNames(team1: String, team2: String) {
        team1Name = team1
        team2Name = team2
    }

    fun getWinningTeam(): String {
        val secondTeamScore = _score.value
        return when {
            firstTeamScore > secondTeamScore -> team1Name
            secondTeamScore > firstTeamScore -> team2Name
            else -> "Berabere"
        }
    }

    fun setPassCount(count: Int) {
        _passCount.value = count
    }

    fun onCorrect() {
        _currentWordIndex.value = (_currentWordIndex.value + 1) % _wordsState.value.size
        updateTeamScore(1)
    }

    fun onTabu() {
        _currentWordIndex.value = (_currentWordIndex.value + 1) % _wordsState.value.size
        updateTeamScore(-2)
    }

    fun getCurrentScore(): Int = _score.value

    fun onPass() {
        if (_passCount.value > 0) {
            _currentWordIndex.value = (_currentWordIndex.value + 1) % _wordsState.value.size
            _passCount.value -= 1
        }
    }

    private fun updateTeamScore(scoreChange: Int) {
        _score.value += scoreChange
    }
}