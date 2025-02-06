package com.dumanyusuf.tabuchallenge.domain.use_case.get_list_team

import com.dumanyusuf.tabuchallenge.domain.model.GameSettings
import com.dumanyusuf.tabuchallenge.domain.model.TeamName
import com.dumanyusuf.tabuchallenge.domain.repo.TabuRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListTeamUseCase @Inject constructor(private val repo: TabuRepo) {


    suspend fun getListTeam():Flow<List<TeamName>>{
        return repo.getTeamList()
    }



}