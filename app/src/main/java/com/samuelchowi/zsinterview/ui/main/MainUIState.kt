package com.samuelchowi.zsinterview.ui.main

data class MainUIState(
    val isNetworkEnabled: Boolean = false,
    val hasWifiConnection: String = "Unknown",
    val hasCellularConnection: String = "Unknown",
    val activeTransport: String = "Unknown",
    val transportName: String = "Unknown",
    val upstreamBandwidth: String? = "Unknown",
    val downstreamBandwidth: String? = "Unknown",
)