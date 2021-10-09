package be.dist.notiflow.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import be.dist.notiflow.theme.Grey800
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


object DialogState {

    // -- State & Flow
    val _showUpdateDialog = MutableStateFlow(false)
    val showUpdateDialog: StateFlow<Boolean> = _showUpdateDialog
    // --

}


@Composable
fun DialogCompose() {
    val context = LocalContext.current
    val openUpdateDialog = DialogState.showUpdateDialog.collectAsState(false)

    if (openUpdateDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = "권한 필요")
            },
            text = {
                Text("봇이 동작하기 위해서 NOTIFICATION_LISTENER 권한이 필요합니다. 설정에서 확인해주세요.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        DialogState._showUpdateDialog.value = false

                        try {
                            context.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                        } catch (ignore: Exception) {
                            Toast.makeText(context, "권한을 설정할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Grey800)
                ) {
                    Text(
                        text = "설정",
                        color = Color.White
                    )
                }
            },
        )
    }
}
