package com.samuelchowi.zsinterview.data.repository

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class NetworkCapabilityRepository {

    fun checkNetwork(manager: ConnectivityManager) =
        callbackFlow {
            val callback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(
                        com.samuelchowi.zsinterview.data.model.NetworkCapabilityModel(
                            isEnabled = true,
                            networkCapabilities = manager.getNetworkCapabilities(network)
                        )
                    )
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(com.samuelchowi.zsinterview.data.model.NetworkCapabilityModel(isEnabled = false))
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(com.samuelchowi.zsinterview.data.model.NetworkCapabilityModel(isEnabled = false))
                }

                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    trySend(
                        com.samuelchowi.zsinterview.data.model.NetworkCapabilityModel(
                            isEnabled = true,
                            networkCapabilities = networkCapabilities
                        )
                    )
                }
            }

            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            manager.requestNetwork(networkRequest, callback)

            awaitClose {
                manager.unregisterNetworkCallback(callback)
            }
        }

    fun getTransportName(
        capabilities: NetworkCapabilities?,
        wifiManager: WifiManager,
        phoneManager: TelephonyManager
    ): String {
        return if (capabilities == null) {
            "Unknown"
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            (capabilities.transportInfo as? WifiInfo)?.ssid ?: checkCapabilitiesForTransportName(
                capabilities,
                wifiManager,
                phoneManager
            )
        } else {
            checkCapabilitiesForTransportName(capabilities, wifiManager, phoneManager)
        }
    }

    private fun checkCapabilitiesForTransportName(
        capabilities: NetworkCapabilities,
        wifiManager: WifiManager,
        phoneManager: TelephonyManager
    ): String {
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                @Suppress("DEPRECATION") val wifiInfo = wifiManager.connectionInfo

                if (wifiInfo.ssid != "<unknown ssid>") {
                    wifiInfo.ssid.trim('"')
                } else {
                    "Unknown"
                }
            }

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                return phoneManager.networkOperatorName ?: "Unknown Carrier"
            }

            else -> "Unknown"
        }
    }
}