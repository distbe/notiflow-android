package be.dist.notiflow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationManagerCompat
import be.dist.notiflow.pages.MainView
import be.dist.notiflow.theme.setThemeContent
import com.google.accompanist.insets.statusBarsPadding
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemeContent {
            Surface(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                MainView()
            }
        }

        initChannel()

        if (!permissionGranted()) {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }
    }

    private fun initChannel() {
        if (NotiflowPreference.channel.isEmpty()) {
            val raw = "${Date().time}"
            val md = MessageDigest.getInstance("SHA-256")
            md.update(raw.toByteArray())
            val hex = String.format("%064x", BigInteger(1, md.digest()))
            NotiflowPreference.channel = hex.slice(IntRange(0, 6))
        }
    }

    private fun permissionGranted(): Boolean {
        val sets = NotificationManagerCompat.getEnabledListenerPackages(this)
        return sets.contains(packageName)
    }

}
