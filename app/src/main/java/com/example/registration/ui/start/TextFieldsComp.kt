package com.example.registration.ui.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedNameTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    shape: Shape,
    label: String = "Name",
    placeholder: String = "Enter your name"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        shape = shape,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) }
    )
}

@Composable
fun OutlinedSurnameTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    shape: Shape,
    label: String = "Surname",
    placeholder: String = "Enter your surname"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        shape = shape,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) }
    )
}

@Composable
fun OutlinedLoginTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    shape: Shape,
    label: String = "Email",
    placeholder: String = "Enter your email"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        shape = shape,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) }
    )
}

@Composable
fun OutlinedPasswordTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    shape: Shape,
    label: String = "Password",
    placeholder: String = "Enter your password",
    isError: Boolean
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val errorMessage = "Пароль невірний!"

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            modifier = modifier,
            shape = shape,
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                if (isError) {
                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                } else {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "visibleOn"
                            )
                        }
                    } else {
                        IconButton(onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "visibleOff"
                            )
                        }
                    }
                }
            }
        )
        if(isError) {
            Text(text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp))
        }
    }
}