package com.example.registration.ui.start.customerRegistration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.registration.R
import com.example.registration.global.ToastObj
import com.example.registration.ui.start.OutlinedLoginTextField
import com.example.registration.ui.start.OutlinedNameTextField
import com.example.registration.ui.start.OutlinedPasswordTextField
import com.example.registration.ui.start.OutlinedSurnameTextField
import com.example.registration.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

@Composable
fun CustomerRegistrationScreen(navigateBack: () -> Unit) {
    MyAppTheme {
        var regData by remember { mutableStateOf(RegData()) }
        val viewModel: CustomerRegistrationViewModel = hiltViewModel()
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        var isPasswordError by rememberSaveable { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp)
        ) {
            Text(
                text = "Registration",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Column {
                OutlinedNameTextField(
                    value = regData.name,
                    onChange = { userInput ->
                        regData = regData.copy(name = userInput)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedSurnameTextField(
                    value = regData.surname,
                    onChange = { userInput ->
                        regData = regData.copy(surname = userInput)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedLoginTextField(
                    value = regData.email,
                    onChange = { userInput ->
                        regData = regData.copy(email = userInput)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedPasswordTextField(
                    value = regData.password,
                    onChange = { userInput ->
                        regData = regData.copy(password = userInput)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    isError = false
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedPasswordTextField(
                    value = regData.confPassword,
                    onChange = { userInput ->
                        regData = regData.copy(confPassword = userInput)
                        isPasswordError = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    label = "Confirm password",
                    placeholder = "Repeat the password",
                    isError = isPasswordError
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                regData.apply {
                                    if (!regData.isEmpty()) {
                                        if (regData.isPasswordConfirmed()) {
                                            CustomerRegistrationUtils.customerRegistration(
                                                name,
                                                surname,
                                                email,
                                                password,
                                                viewModel,
                                                context,
                                                navigateBack
                                            )
                                        } else {
                                            isPasswordError = !isPasswordError
                                        }
                                    } else {
                                        ToastObj.shortToastMake(
                                            context.getString(R.string.null_check),
                                            context
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Register",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Sign in",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            navigateBack.invoke()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerRegistrationScreenPreview() {
    val navigateBack: () -> Unit = {}
    CustomerRegistrationScreen(navigateBack)
}