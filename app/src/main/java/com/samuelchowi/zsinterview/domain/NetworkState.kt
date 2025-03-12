package com.samuelchowi.zsinterview.domain

data class NetworkState(
    val isEnabled: Boolean = false,
    val hasWifiConnection: Boolean? = false,
    val hasCellularConnection: Boolean? = false,
    val activeTransport: String = "",
    val transportName: String = "",
    val upstreamBandwidth: Int? = 0,
    val downstreamBandwidth: Int? = 0,
)