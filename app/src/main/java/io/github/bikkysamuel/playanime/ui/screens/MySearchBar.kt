package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp

@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    initialSearchText: String = "",
    hint: String = "",
    requestFocus: Boolean,
    onSearch: (String) -> Unit = {},
    onKeyPress: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf(initialSearchText)
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val focusRequester = remember {
        FocusRequester()
    }

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
            onKeyPress(it)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(text)
        }),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        ),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isHintDisplayed = it.isFocused
            }
            .focusRequester(focusRequester)
    )

    LaunchedEffect(key1 = Unit) {
        if (requestFocus)
            focusRequester.requestFocus()
    }

    if (isHintDisplayed)
        Text(text = hint)
}