package com.samuelchowi.zsinterview.domain

import android.net.NetworkCapabilities

fun NetworkCapabilities?.capability(capability: Int) =
    this?.hasCapability(capability) ?: false

fun NetworkCapabilities?.transport(transport: Int) =
    this?.hasTransport(transport)

fun NetworkCapabilities?.getTransportName(defaultResult: String = "Unknown Network") =
    this?.let {
        when {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
            else -> defaultResult
        }
    } ?: defaultResult

