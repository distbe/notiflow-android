package be.dist.notiflow.pages

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ToggleOff
import androidx.compose.material.icons.rounded.ToggleOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import be.dist.notiflow.NotiflowPreference
import be.dist.notiflow.R
import be.dist.notiflow.components.InputText
import be.dist.notiflow.theme.Grey200
import be.dist.notiflow.theme.Grey400
import be.dist.notiflow.theme.Grey600
import be.dist.notiflow.theme.Grey900


@Composable
fun MainView() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val debugState = remember { mutableStateOf(NotiflowPreference.debug) }
    val botState = remember { mutableStateOf(NotiflowPreference.bot) }

    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Notiflow",
                    fontWeight = FontWeight.Bold,
                    color = Color.Grey400,
                    fontSize = 32.sp,
                    modifier = Modifier
                )
                Text(
                    text = "for Kakao",
                    fontWeight = FontWeight.Bold,
                    color = Color.Grey400,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(top = 36.dp, bottom = 2.dp)
                .requiredHeight(36.dp)
        ) {
            Text(
                text = "🛠 디버깅을 위한 링크",
                fontWeight = FontWeight.Bold,
                color = Color.Grey600,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1.2f)
                    .clickable {
                        debugState.value = !debugState.value
                        NotiflowPreference.debug = debugState.value
                    }
                    .alpha(if (debugState.value) 1f else 0.2f)
            ) {
                Icon(
                    imageVector = if (debugState.value) Icons.Rounded.ToggleOn else Icons.Rounded.ToggleOff,
                    contentDescription = "Toggle",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .requiredHeight(56.dp)
                .padding(horizontal = 22.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Grey200)
                .alpha(if (debugState.value) 1f else 0.2f)
        ) {
            Text(
                text = "https://notiflow.dist.be/?channel=${NotiflowPreference.channel}",
                fontWeight = FontWeight.Bold,
                color = Color.Grey900,
                fontSize = 13.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Debug Link", "https://notiflow.dist.be/?channel=${NotiflowPreference.channel}")
                        clipboard.setPrimaryClip(clip)
                        Toast
                            .makeText(context, "복사되었습니다!", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = "Copy",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(top = 36.dp, bottom = 2.dp)
                .requiredHeight(36.dp)
        ) {
            Text(
                text = "🏃🏻 봇 서버",
                fontWeight = FontWeight.Bold,
                color = Color.Grey600,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1.2f)
                    .clickable {
                        botState.value = !botState.value
                        NotiflowPreference.bot = botState.value
                    }
                    .alpha(if (botState.value) 1f else 0.2f)
            ) {
                Icon(
                    imageVector = if (botState.value) Icons.Rounded.ToggleOn else Icons.Rounded.ToggleOff,
                    contentDescription = "Toggle",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .requiredHeight(56.dp)
                .padding(horizontal = 22.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Grey200)
                .alpha(if (botState.value) 1f else 0.2f)
        ) {
            InputText(
                initValue = NotiflowPreference.serverUrl,
                placeHolder = "봇 서버 URL을 입력해주세요.",
                onValueChange = {
                    NotiflowPreference.serverUrl = it
                },
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/distbe/notiflow-landing"))
                    context.startActivity(intent)
                }
                .padding(vertical = 12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icon_github),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(Color.Grey900),
                modifier = Modifier
                    .requiredSize(width = 20.dp, height = 20.dp)
            )

            Text(
                text = "https://github.com/distbe/notiflow-landing",
                fontWeight = FontWeight.Bold,
                color = Color.Grey900,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(start = 6.dp)
            )
        }

        Spacer(modifier = Modifier.requiredHeight(46.dp))
    }
}
