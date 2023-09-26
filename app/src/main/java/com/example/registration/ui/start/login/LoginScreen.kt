package com.example.registration.ui.start.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    MyAppTheme {
        var credentials by remember { mutableStateOf(Credentials()) }
        val dropList = listOf("a Customer", "an Employee", "a Manager")
        val dropListIsExpanded = remember { mutableStateOf(false) }
        val currentUserTypeIndex = rememberSaveable { mutableIntStateOf(0) }
        val viewModel: LoginViewModel = viewModel()
        val dataStoreViewModel: DataStoreViewModel = viewModel()
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
        ) {
            Text(
                text = "Warehouse",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )

            Column {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "I am",
                            style = MaterialTheme.typography.h5
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    UserTypeDropList(
                        curValueIndex = currentUserTypeIndex,
                        userList = dropList,
                        expanded = dropListIsExpanded
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedLoginTextField(
                    value = credentials.login,
                    onChange = { userInput ->
                        credentials = credentials.copy(login = userInput)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedPasswordTextField(
                    value = credentials.password,
                    onChange = { userInput ->
                        credentials = credentials.copy(password = userInput)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box {
                    Button(
                        onClick = {
                            if (!credentials.isEmpty()) {
                                coroutineScope.launch {
                                    LoginUtils.login(
                                        viewModel,
                                        dataStoreViewModel,
                                        credentials.login,
                                        credentials.password,
                                        currentUserTypeIndex.intValue,
                                        context
                                    )
                                }
                            } else {
                                ToastObj.shortToastMake("all fields must be completed!", context)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Log in",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (currentUserTypeIndex.intValue == 0) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(3.dp))

                        Text(
                            text = "Create a New Account",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserTypeDropList(
    curValueIndex: MutableIntState,
    userList: List<String>,
    expanded: MutableState<Boolean>
) {
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {

        TextField(
            readOnly = true,
            value = userList[curValueIndex.intValue],
            textStyle = MaterialTheme.typography.h5,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            modifier = Modifier
                .widthIn(1.dp, Dp.Infinity)
                .heightIn(1.dp, Dp.Infinity),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }
        ) {
            userList.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        curValueIndex.intValue = index
                        expanded.value = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun OutlinedLoginTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    shape: Shape,
    label: String = "Login",
    placeholder: String = "Enter your login"
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
    placeholder: String = "Enter your password"
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

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
        }, trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = null)
                }
            } else {
                IconButton(onClick = { showPassword = true }) {
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = null)
                }
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}