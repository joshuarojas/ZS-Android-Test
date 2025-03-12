package com.samuelchowi.zsinterview.data.model

import android.net.NetworkCapabilities

data class NetworkCapabilityModel(
    val isEnabled: Boolean = false,
    val networkCapabilities: NetworkCapabilities? = null
)