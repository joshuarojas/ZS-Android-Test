package com.samuelchowi.zsinterview.ui.main

import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelchowi.zsinterview.ui.components.Header
import com.samuelchowi.zsinterview.ui.components.RequestPermission
import com.samuelchowi.zsinterview.ui.components.Text
import com.samuelchowi.zsinterview.ui.theme.ZSInterviewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZSInterviewTheme {
                RequestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) {
                    NetworkContent()
                }
            }
        }
    }
}

@Composable
fun NetworkContent(
    viewmodel: MainViewmodel = viewModel()
) {
    val context = LocalContext.current
    val networkManager = ContextCompat.getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
    val wifiManager = ContextCompat.getSystemService(context, WifiManager::class.java) as WifiManager
    val phoneManager = ContextCompat.getSystemService(context, TelephonyManager::class.java) as TelephonyManager
    viewmodel.checkNetwork(networkManager, wifiManager, phoneManager)

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isNetworkEnabled) {
        snackbarHostState.showSnackbar(
            message = "Network state updated: ${if (uiState.isNetworkEnabled) "Connected" else "Disconnected"}",
            duration = SnackbarDuration.Long
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        if (uiState.isNetworkEnabled) {
            NetworkInformation(
                viewmodel = viewmodel,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            NetworkError()
        }
    }
}

@Composable
fun NetworkInformation(viewmodel: MainViewmodel, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(14.dp),
        ) {
            val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

            Header(true)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Connected: ${uiState.isNetworkEnabled}")
            Text("Wifi: ${uiState.hasWifiConnection}")
            Text("Cellular: ${uiState.hasCellularConnection}")
            Text("Active: ${uiState.activeTransport}")
            Text("Network name: ${uiState.transportName}")
            Text("Upstream: ${uiState.upstreamBandwidth}")
            Text("Downstream: ${uiState.downstreamBandwidth}")
        }
    }
}

@Composable
fun NetworkError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(14.dp),
        ) {
            Header(false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkContentPreview() {
    ZSInterviewTheme {
        NetworkContent()
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkInfoPreview() {
    ZSInterviewTheme {
        Surface {
            NetworkInformation(viewModel())
        }
    }
}