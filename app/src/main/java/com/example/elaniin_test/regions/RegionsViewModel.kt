package com.example.elaniin_test.regions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaniin_test.R
import com.example.elaniin_test.regions.domain.GetRegionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionsViewModel @Inject constructor(
    private val getRegionsUseCase: GetRegionsUseCase
) : ViewModel() {
    fun getRegions() {
        viewModelScope.launch {
            val regions = getRegionsUseCase()
            if (regions == null) {
                _state.update {
                    it.copy(errorMessage = R.string.get_region_error)
                }
            } else {
                _state.update {
                    it.copy(regions = regions, errorMessage = null)
                }
            }
        }
    }

    private val _state = MutableStateFlow(RegionsState())
    val state = _state.asStateFlow()
}