package com.dumanyusuf.tabuchallenge.presentation.game_screan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.Words
import com.dumanyusuf.tabuchallenge.domain.use_case.game_screan.GameScreanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameScreanUseCase: GameScreanUseCase
) : ViewModel() {
    private val _navigateToNextTeam = MutableStateFlow(false)
    val navigateToNextTeam: StateFlow<Boolean> = _navigateToNextTeam

    private val _navigateToWinTeam = MutableStateFlow(false)
    val navigateToWinTeam: StateFlow<Boolean> = _navigateToWinTeam

    private var isSecondTeam = false
    private var firstTeamScore = 0

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
        fetchWords()

    }

    private fun fetchWords() {
        viewModelScope.launch {
            try {
                val words = gameScreanUseCase.getWords()
                _wordsState.value = words.shuffled()
                Log.d("GameViewModel", "Fetched words: $_wordsState")
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error fetching words", e)
            }
        }
    }

    fun timerGame(gameSettings: GameSettings, secondTeam: Boolean = false, firstTeamScore: Int = 0) {
        isSecondTeam = secondTeam
        this.firstTeamScore = firstTeamScore

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
                    delay(100) // Check pause state every 100ms
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

    fun getWinningTeam(): String {
        val secondTeamScore = _score.value
        return when {
            firstTeamScore > secondTeamScore -> "Takım 1"
            secondTeamScore > firstTeamScore -> "Takım 2"
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
        updateTeamScore(-1)

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