package com.example.phinconattendance.component

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.phinconattendance.R
import com.example.phinconattendance.ui.theme.*


@Composable
fun TextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = TextColor
    )
}

@Composable
fun TextBigComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = TitleColor
    )
}

@Composable
fun TextComponentShort(value: String) {
    Text(
        text = value,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = Color.Gray
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
fun TextWhiteBigComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W800,
            fontStyle = FontStyle.Normal
        ), color = Color.White
    )
}


@Composable
fun TextFieldComponent(input : MutableState<String>,
                       labelValue: String,
                       onTextChanged: (String) -> Unit,
                       errorStatus: Boolean = false,
                       errorMessage : MutableState<String>
) {

    //val textValue = remember { mutableStateOf("") }
    //val localFocusManager = LocalFocusManager.current
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth(),
        placeholder = { Text(text = labelValue) },
        colors =
        if (!errorStatus) TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Blue,
            focusedBorderColor = Blue,
            focusedLabelColor = Blue
        )else TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            //cursorColor = Color.Red,
            focusedBorderColor = Color.Red,
            focusedLabelColor = Color.Red,
            //unfocusedBorderColor = Color.Red
        ),

        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = input.value,
        onValueChange = {
            input.value = it
            onTextChanged(it)
        },
        isError = errorStatus
    )

    if(errorStatus){
        Text( modifier = Modifier.padding(top = 2.dp, start = 2.dp).fillMaxWidth(),
            text = errorMessage.value,
            color = MaterialTheme.colors.error)
    }
}

@Composable
fun PasswordTextFieldComponent(password : MutableState<String>,
                               labelValue: String,
                               onTextSelected: (String) -> Unit,
                               errorStatus: Boolean,
                               errorMessage : MutableState<String>) {

    val localFocusManager = LocalFocusManager.current
    //val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .clip(Shapes.small),
        placeholder = { Text(text = labelValue) },
        colors =
        if (!errorStatus) TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            focusedBorderColor = Blue,
            focusedLabelColor = Blue,
            cursorColor = Blue
        )else TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            cursorColor = Color.Red,
            focusedBorderColor = Color.Red,
            focusedLabelColor = Color.Red,
            unfocusedBorderColor = Color.Red
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
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
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
        isError = errorStatus
    )

    if(errorStatus){
        Text( modifier = Modifier.padding(top = 2.dp, start = 2.dp).fillMaxWidth(),
            text = errorMessage.value,
            color = MaterialTheme.colors.error)
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
fun ButtonComponent(value: String, background: Color, text:Color,onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .heightIn(57.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(57.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(5.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                color = text,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

@Composable
fun TextButtonComponent(value : String , onButtonClicked: () -> Unit){
    TextButton(modifier = Modifier
        .fillMaxWidth(),
        onClick = onButtonClicked)
    {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ){
            Text(
                text = value,
                fontSize = 14.sp ,
                color = LoginPrimary,
                textAlign = TextAlign.End
            )
        }
    }
}

