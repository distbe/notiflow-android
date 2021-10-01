package be.dist.notiflow.services

import android.app.Notification
import android.app.PendingIntent.CanceledException
import android.app.RemoteInput
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.util.Patterns
import be.dist.notiflow.NotiflowPreference
import be.dist.notiflow.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class NotificationService : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (null == sbn)
            return

        val id = sbn.id
        val time = sbn.postTime

        val notification = sbn.notification
        val extras = notification.extras

        val notiPackageName = sbn.packageName
        val notiTitle = extras.getString(Notification.EXTRA_TITLE)
        val notiMessage = extras.getCharSequence(Notification.EXTRA_TEXT)
        val notiSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)

        if (notiPackageName != "com.kakao.talk")
            return
        if (null == notiMessage)
            return

        Log.wtf("🚨", "id: ${id}")
        Log.wtf("🚨", "time: ${time}")

        Log.wtf("🚨", "noti_PackageName: ${notiPackageName}")
        Log.wtf("🚨", "notiTitle: ${notiTitle}")
        Log.wtf("🚨", "notiText: ${notiMessage}")
        Log.wtf("🚨", "notiSubText: ${notiSubText}")

        scope.launch {
            if (!NotiflowPreference.debug) {
                return@launch
            }

            try {
                val reqBody = RequestBody(
                    `package` = notiPackageName,
                    name = notiTitle ?: "",
                    message = notiMessage.toString(),
                    title = notiSubText?.toString()
                )
                val reply = debugApi.sendDebug(NotiflowPreference.channel, reqBody).reply ?: return@launch
                val replyAction = notification.actions.first {
                    null != it.remoteInputs &&
                        it.remoteInputs.isNotEmpty() &&
                        it.title.toString().contains(Regex("Reply|reply|답장"))
                }
                sendReply(reply, replyAction)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        scope.launch {
            if (!NotiflowPreference.bot) {
                return@launch
            }
            val url = NotiflowPreference.serverUrl
            if (url.isEmpty()) {
                return@launch
            }
            if (!Patterns.WEB_URL.matcher(url).matches()) {
                return@launch
            }

            val uri = Uri.parse(url)
            botBaseUrl = "${uri.scheme}://${uri.host}"
            val path = url.split(botBaseUrl)[1]

            try {
                val reqBody = RequestBody(
                    `package` = notiPackageName,
                    name = notiTitle ?: "",
                    message = notiMessage.toString(),
                    title = notiSubText.toString()
                )

                val reply = botApi.sendBot(path, reqBody).reply ?: return@launch
                val replyAction = notification.actions.first {
                    null != it.remoteInputs &&
                        it.remoteInputs.isNotEmpty() &&
                        it.title.toString().contains(Regex("Reply|reply|답장"))
                }
                sendReply(reply, replyAction)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendReply(value: String, action: Notification.Action) {
        val sendIntent = Intent()
        val msg = Bundle()

        for (inputable in action.remoteInputs) {
            msg.putCharSequence(inputable.resultKey, value)
        }
        RemoteInput.addResultsToIntent(action.remoteInputs, sendIntent, msg)

        try {
            action.actionIntent.send(this, 0, sendIntent)
        } catch (e: CanceledException) {
        }
    }

}