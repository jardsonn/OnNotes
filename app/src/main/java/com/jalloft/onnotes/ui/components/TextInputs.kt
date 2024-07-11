package com.jalloft.onnotes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalloft.onnotes.R
import com.jalloft.onnotes.common.Validations


@Composable
fun TextField(
    value: String,
    placeholder: String,
    title: String,
    onValueChange: (String) -> Unit,
    onContentValidity: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
) {

    val errorWarning =
        if (value.isEmpty()) stringResource(R.string.this_field_is_mandatory)
        else if (value.length < 3)
            stringResource(id = R.string.text_is_very_small)
        else null

    TextFieldBase(
        value = value,
        placeholder = placeholder,
        title = title,
        onValueChange = onValueChange,
        onContentValidity = onContentValidity,
        modifier = modifier,
        isRequired = isRequired,
        errorWarning = errorWarning,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.Text
        ),
    )
}


@Composable
fun EmailTextField(
    value: String,
    placeholder: String,
    title: String,
    onValueChange: (String) -> Unit,
    onContentValidity: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
) {

    val errorWarning =
        if (value.isEmpty()) stringResource(R.string.this_field_is_mandatory)
        else if (!Validations.isValidEmail(value))
            stringResource(R.string.please_enter_a_valid_email)
        else null

    TextFieldBase(
        value = value,
        placeholder = placeholder,
        title = title,
        onValueChange = onValueChange,
        onContentValidity = onContentValidity,
        modifier = modifier,
        isRequired = isRequired,
        errorWarning = errorWarning,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
    )
}


@Composable
fun PasswordTextField(
    value: String,
    placeholder: String,
    title: String,
    onValueChange: (String) -> Unit,
    onContentValidity: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    errorWarningMessage: String? = null,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
) {
    var isShowPassword by remember { mutableStateOf(false) }

    val errorWarning =
        errorWarningMessage ?: if (value.isEmpty()) stringResource(R.string.this_field_is_mandatory)
        else if (!Validations.isValidPassword(value))
            stringResource(R.string.please_enter_your_password)
        else null

    TextFieldBase(
        value = value,
        placeholder = placeholder,
        title = title,
        onValueChange = onValueChange,
        onContentValidity = onContentValidity,
        modifier = modifier,
        errorWarning = errorWarning,
        isRequired = isRequired,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = if (isShowPassword) KeyboardType.Text else KeyboardType.Password
        ),
        visualTransformation = if (!isShowPassword) PasswordVisualTransformation() else VisualTransformation.None,
        onEndButton = { color ->
            Button(
                onClick = { isShowPassword = !isShowPassword },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Icon(
                    painter = if (isShowPassword) painterResource(id = R.drawable.ic_eye_off)
                    else painterResource(id = R.drawable.ic_eye_on),
                    contentDescription = if (isShowPassword) stringResource(R.string.hide_password) else stringResource(
                        R.string.show_password
                    ),
                    modifier = Modifier.size(18.dp),
                    tint = color
                )
            }
        }
    )
}


@Composable
fun TextFieldBase(
    value: String,
    placeholder: String,
    title: String,
    onValueChange: (String) -> Unit,
    onContentValidity: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorWarning: String? = null,
    onEndButton: @Composable (Color) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    val focusRequester = remember { FocusRequester() }
    var hasFocus by remember { mutableStateOf(false) }
    var isTypingFinished by remember { mutableStateOf(false) }

    val textColor = if (errorWarning == null) {
        MaterialTheme.colorScheme.onBackground
    } else if (isTypingFinished) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    LaunchedEffect(key1 = errorWarning) {
        onContentValidity(title, errorWarning == null)
    }

    Column(modifier) {
        Text(
            text = title.plus(if (isRequired) " *" else ""),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                isTypingFinished = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.hasFocus) {
                        hasFocus = true
                    }
                    if (hasFocus && !focusState.hasFocus) {
                        isTypingFinished = true
                        hasFocus = false
                    }
                },
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    isTypingFinished = true
                    defaultKeyboardAction(ImeAction.Done)
                },
                onNext = {
                    isTypingFinished = true
                    defaultKeyboardAction(ImeAction.Next)
                }
            ),
            visualTransformation = visualTransformation,
            textStyle = TextStyle(color = textColor),
            decorationBox = {
                Column {
                    Row {
                        Box(
                            Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = textColor.copy(.4f)
                                )
                            }
                            it()
                        }
                        onEndButton(textColor)
                    }
                    Divider(color = textColor.copy(.2f))
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 34.dp)
        ) {
            AnimatedVisibility(visible = errorWarning != null && isTypingFinished) {
                Text(
                    text = errorWarning.orEmpty(),
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp
                )
            }
        }
    }
}