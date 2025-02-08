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

    private val _wordsState = MutableStateFlow<List<Words>>(emptyList())
    val wordsState: StateFlow<List<Words>> = _wordsState

    private val _currentWordIndex = MutableStateFlow(0)
    val currentWordIndex: StateFlow<Int> = _currentWordIndex

    private val _currentTeamIndex = MutableStateFlow(0)
    val currentTeamIndex: StateFlow<Int> = _currentTeamIndex

    private val _teamScores = MutableStateFlow(listOf(0, 0))
    val teamScores: StateFlow<List<Int>> = _teamScores

    private val _passCount = MutableStateFlow<Int>(0)
    val passCount: StateFlow<Int> = _passCount

    private val _time=MutableStateFlow<Int>(0)
    val time:StateFlow<Int> =_time.asStateFlow()

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

    fun timerGame(gameSettings: GameSettings) {
        viewModelScope.launch {
            _time.value = gameSettings.gameTime
            while (_time.value > 0) {
                delay(1000)
                _time.value--
            }
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

    fun onPass() {
        if (_passCount.value > 0) {
            _currentWordIndex.value = (_currentWordIndex.value + 1) % _wordsState.value.size
            _passCount.value -= 1
        }
    }

    private fun updateTeamScore(scoreChange: Int) {
        val updatedScores = _teamScores.value.toMutableList()
        updatedScores[_currentTeamIndex.value] += scoreChange
        _teamScores.value = updatedScores
    }
}