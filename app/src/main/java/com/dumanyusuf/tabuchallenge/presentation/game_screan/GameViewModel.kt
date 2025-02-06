package com.dumanyusuf.tabuchallenge.presentation.game_screan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.use_case.get_list_team.GetListTeamUseCase
import com.dumanyusuf.tabuchallenge.domain.use_case.team_name.TeamNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val getListTeamUseCase: GetListTeamUseCase,
):ViewModel() {

    private val _teamList=MutableStateFlow<List<TeamName>>(emptyList())
    val teamList:StateFlow<List<TeamName>> = _teamList.asStateFlow()


    init {
        getListTeam()
    }

    fun getListTeam() {
        viewModelScope.launch {
            try {
               getListTeamUseCase.getListTeam().collect{
                   _teamList.value=it
               }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}