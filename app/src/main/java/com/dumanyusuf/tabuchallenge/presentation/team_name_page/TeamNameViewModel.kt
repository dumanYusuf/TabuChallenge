package com.dumanyusuf.tabuchallenge.presentation.team_name_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.use_case.team_name.TeamNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeamNameViewModel @Inject constructor(
    private val teamNameUseCase: TeamNameUseCase
):ViewModel(){

    fun addTeamName(team1:String,team2:String){
        viewModelScope.launch {
            try {
                teamNameUseCase.addTeam(team1, team2)
                Log.e("Başarılı", "Takım başarıyla eklendi.")
            } catch (e: Exception) {
                Log.e("Hata", "Firebase ekleme hatası: ${e.message}")
            }
        }

    }

}
