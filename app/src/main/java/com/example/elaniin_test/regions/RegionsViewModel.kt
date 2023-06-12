package com.example.elaniin_test.regions

import androidx.lifecycle.ViewModel
import com.example.elaniin_test.regions.domain.GetRegionsUseCase
import com.example.elaniin_test.sign_in.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegionsViewModel @Inject constructor(
    private val getRegionsUseCase: GetRegionsUseCase
) : ViewModel() {
    fun getRegions() {
        _state.update {
            it.copy(
                regions = getRegionsUseCase(),
                errorMessage = null
            )
        }
    }

    private val _state = MutableStateFlow(RegionsState())
    val state = _state.asStateFlow()
}