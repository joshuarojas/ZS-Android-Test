package com.samuelchowi.zsinterview.ui.utility

import com.samuelchowi.zsinterview.domain.NetworkState
import com.samuelchowi.zsinterview.ui.main.MainUIState

fun NetworkState.transform(): MainUIState = MainUIState(
    isNetworkEnabled = isEnabled,
    hasWifiConnection = hasWifiConnection?.toString() ?: "Unknown",
    hasCellularConnection = hasCellularConnection?.toString() ?: "Unknown",
    activeTransport = activeTransport,
    transportName = transportName,
    upstreamBandwidth = upstreamBandwidth?.let { "$it kbps" } ?: "Unknown",
    downstreamBandwidth = downstreamBandwidth?.let { "$it kbps" } ?: "Unknown",
)