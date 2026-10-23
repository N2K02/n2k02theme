@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)
package dev.n2k02.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** App-independent UI model; each app keeps its own update and verification engine. */
sealed interface N2K02UpdateState {
    data object Idle : N2K02UpdateState
    data object Checking : N2K02UpdateState
    data object Current : N2K02UpdateState
    data class Available(val version: String, val notes: String) : N2K02UpdateState
    data class Downloading(val progress: Float?) : N2K02UpdateState
    data class Ready(val version: String) : N2K02UpdateState
    data class Error(val message: String) : N2K02UpdateState
}

@Composable
fun N2K02UpdateCard(
    installedVersion: String,
    state: N2K02UpdateState,
    onCheck: () -> Unit,
    onDownload: () -> Unit,
    onInstall: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    N2K02StatusCard("App updates", "Installed version: $installedVersion", modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            when (state) {
                N2K02UpdateState.Idle, N2K02UpdateState.Current -> {
                    if (state == N2K02UpdateState.Current) Text("You're up to date")
                    N2K02LoadingButton("Check for updates", false, onCheck, Modifier.fillMaxWidth())
                }
                N2K02UpdateState.Checking ->
                    N2K02LoadingButton("Checking", true, onCheck, Modifier.fillMaxWidth())
                is N2K02UpdateState.Available -> {
                    Text("Version ${state.version} available", style = MaterialTheme.typography.titleMedium)
                    if (state.notes.isNotBlank()) Text(state.notes)
                    Button(onClick = onDownload, modifier = Modifier.fillMaxWidth()) { Text("Download update") }
                }
                is N2K02UpdateState.Downloading -> {
                    if (state.progress == null) LinearProgressIndicator(Modifier.fillMaxWidth())
                    else LinearProgressIndicator(progress = { state.progress.coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                    Text(if (state.progress == null) "Downloading…" else "${(state.progress.coerceIn(0f, 1f) * 100).toInt()}% downloaded")
                }
                is N2K02UpdateState.Ready ->
                    Button(onClick = onInstall, modifier = Modifier.fillMaxWidth()) { Text("Install ${state.version}") }
                is N2K02UpdateState.Error -> {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                    OutlinedButton(onClick = onRetry) { Text("Try again") }
                }
            }
        }
    }
}
