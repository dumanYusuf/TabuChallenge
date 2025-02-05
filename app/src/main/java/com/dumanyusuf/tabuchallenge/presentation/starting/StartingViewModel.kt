package com.dumanyusuf.tabuchallenge.presentation.starting

import androidx.lifecycle.ViewModel
import com.dumanyusuf.tabuchallenge.domain.use_case.team_name.TeamNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val teamNameUseCase: TeamNameUseCase
):ViewModel() {




}