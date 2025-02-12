package com.dumanyusuf.tabuchallenge.presentation.welcome_page

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {
    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _musicEnabled = MutableStateFlow(true)
    val musicEnabled: StateFlow<Boolean> = _musicEnabled.asStateFlow()

    private val _soundVolume = MutableStateFlow(100)
    val soundVolume: StateFlow<Int> = _soundVolume.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _language = MutableStateFlow("tr")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _vibrationEnabled = MutableStateFlow(true)
    val vibrationEnabled: StateFlow<Boolean> = _vibrationEnabled.asStateFlow()

    private val _difficulty = MutableStateFlow("normal")
    val difficulty: StateFlow<String> = _difficulty.asStateFlow()

    private val _showSoundDialog = MutableStateFlow(false)
    val showSoundDialog: StateFlow<Boolean> = _showSoundDialog.asStateFlow()

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog: StateFlow<Boolean> = _showSettingsDialog.asStateFlow()

    fun toggleSound() {
        _soundEnabled.value = !_soundEnabled.value
    }

    fun toggleMusic() {
        _musicEnabled.value = !_musicEnabled.value
    }

    fun setSoundVolume(volume: Int) {
        _soundVolume.value = volume
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setLanguage(lang: String) {
        _language.value = lang
    }

    fun toggleVibration() {
        _vibrationEnabled.value = !_vibrationEnabled.value
    }

    fun setDifficulty(diff: String) {
        _difficulty.value = diff
    }

    fun toggleSoundDialog() {
        _showSoundDialog.value = !_showSoundDialog.value
    }

    fun toggleSettingsDialog() {
        _showSettingsDialog.value = !_showSettingsDialog.value
    }
}