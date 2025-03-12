package com.samuelchowi.zsinterview.ui.main

import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelchowi.zsinterview.data.repository.NetworkCapabilityRepository
import com.samuelchowi.zsinterview.domain.NetworkCapabilityUseCase
import com.samuelchowi.zsinterview.ui.utility.transform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewmodel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    private val networkUseCase: NetworkCapabilityUseCase = NetworkCapabilityUseCase(NetworkCapabilityRepository())

    fun checkNetwork(manager: ConnectivityManager, wifiManager: WifiManager, phoneManager: TelephonyManager) {
        viewModelScope.launch {
            networkUseCase.checkNetwork(manager, wifiManager, phoneManager).collect {
                _uiState.emit(it.transform())
            }
        }
    }
}