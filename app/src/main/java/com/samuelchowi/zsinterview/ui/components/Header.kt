package com.samuelchowi.zsinterview.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samuelchowi.zsinterview.R

@Composable
fun Header(isConnected: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(
                id = if (isConnected) R.drawable.wifi_on
                else R.drawable.wifi_off
            ),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            if (isConnected) "Internet connected"
            else "No internet connection"
        )

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            painter = painterResource(
                id = if (isConnected) R.drawable.cellular_internet
                else R.drawable.cellular_no_internet
            ),
            contentDescription = null,
        )
    }
}