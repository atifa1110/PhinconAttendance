package com.example.phinconattendance.component

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import com.example.phinconattendance.R
import com.example.phinconattendance.ui.theme.*

@Composable
fun TextComponent(value: String) {
    Text(
        text = value,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = TextColor
    )
}

@Composable
fun TextWhiteComponent(value: String,textAlign: TextAlign, fontSize: TextUnit) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = textAlign
        ), color = Color.White
    )
}


@Composable
fun TextFieldComponent(
    text : String,
    textError : String? = null,
    @StringRes labelName : Int,
    onTextChanged : (String) -> Unit,
) {
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(id = labelName)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Gray,
            backgroundColor = Color.Transparent,
            errorBorderColor = Color.Red
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        isError = textError!= null
    )

    if(textError!=null){
        Text(
            text = "* $textError",
            color = MaterialTheme.colors.error,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp)
        )
    }
}


@Composable
fun PasswordTextFieldComponent(
    text : String,
    textError : String? = null,
    @StringRes labelName : Int,
    onTextChanged : (String) -> Unit,
    localFocusManager: FocusManager = LocalFocusManager.current
) {
    val passwordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .clip(Shapes.small),
        placeholder = { Text(text = stringResource(id = labelName)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                painterResource(id = R.drawable.ic_visibility)
            } else {
                painterResource(id = R.drawable.ic_visibility_off)
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = iconImage, contentDescription = description)
            }

        },

        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = textError!=null
    )

    if(textError!=null){
        Text(
            text = "* $textError",
            color = MaterialTheme.colors.error,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp)
        )
    }
}

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Already Have Account? Please " else "Don’t have Account? Please "
    val loginText = if (tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = LoginPrimary, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color = TextClickable,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        ),

        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }

        },
    )
}

@Composable
fun ButtonComponent(
    @StringRes value: Int,
    background: Color,
    content :Color,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false
) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(57.dp),
        onClick = onButtonClicked,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background,
            contentColor = content
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(id = value),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TextButtonComponent(
    @StringRes value: Int,
    onButtonClicked: () -> Unit
){
    TextButton(modifier = Modifier.fillMaxWidth(),
        onClick = onButtonClicked)
    {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ){
            Text(
                text = stringResource(id = value),
                fontSize = 14.sp ,
                color = LoginPrimary,
                textAlign = TextAlign.End
            )
        }
    }
}

