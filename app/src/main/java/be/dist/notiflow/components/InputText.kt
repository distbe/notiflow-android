package be.dist.notiflow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import be.dist.notiflow.theme.Grey400
import be.dist.notiflow.theme.Grey900

@Composable
fun InputText(
    inputValue: MutableState<TextFieldValue>,
    placeHolder: String,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = inputValue.value,
        onValueChange = { inputValue.value = it },
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 13.sp,
                color = Color.Grey400,
                fontWeight = FontWeight.Bold,
            )
        },
        textStyle = TextStyle(
            color = Color.Grey900,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier
            .fillMaxWidth()
            .composed { modifier },
    )
}

@Composable
fun InputText(
    initValue: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val inputValueState = remember { mutableStateOf(initValue) }

    TextField(
        value = inputValueState.value,
        onValueChange = {
            inputValueState.value = it
            onValueChange.invoke(it)
        },
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 13.sp,
                color = Color.Grey400,
                fontWeight = FontWeight.Bold,
            )
        },
        textStyle = TextStyle(
            color = Color.Grey900,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier
            .fillMaxWidth()
            .composed { modifier },
    )
}