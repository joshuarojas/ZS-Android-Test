package com.samuelchowi.zsinterview.domain

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import com.samuelchowi.zsinterview.data.repository.NetworkCapabilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NetworkCapabilityUseCase(
    private val repository: NetworkCapabilityRepository
) {

    fun checkNetwork(
        manager: ConnectivityManager,
        wifiManager: WifiManager,
        phoneManager: TelephonyManager
    ): Flow<NetworkState> {
        return repository.checkNetwork(manager).map {
            NetworkState(
                isEnabled = it.isEnabled && it.networkCapabilities.capability(NetworkCapabilities.NET_CAPABILITY_VALIDATED),
                hasWifiConnection = it.networkCapabilities.transport(NetworkCapabilities.TRANSPORT_WIFI),
                hasCellularConnection = it.networkCapabilities.transport(NetworkCapabilities.TRANSPORT_CELLULAR),
                activeTransport = it.networkCapabilities.getTransportName(),
                transportName = repository.getTransportName(it.networkCapabilities, wifiManager, phoneManager),
                upstreamBandwidth = it.networkCapabilities?.linkUpstreamBandwidthKbps,
                downstreamBandwidth = it.networkCapabilities?.linkDownstreamBandwidthKbps
            )
        }
    }
}