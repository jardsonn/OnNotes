package com.jalloft.onnotes.ui.screens.auth.signin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalloft.onnotes.R
import com.jalloft.onnotes.common.Validations
import com.jalloft.onnotes.data.dto.auth.SignInRequest
import com.jalloft.onnotes.ui.components.EmailTextField
import com.jalloft.onnotes.ui.components.PasswordTextField
import com.jalloft.onnotes.ui.components.SubmitButton
import com.jalloft.onnotes.ui.components.TextFooter
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignInScreen(
    onBack: () -> Unit,
    viewModel: SiginInViewModel = koinViewModel(),
    onNavigateToNotes: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    userEmail: String?
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back)
                )
            }

            val context = LocalContext.current

            LaunchedEffect(key1 = viewModel.signInAuthSuccess) {
                if (viewModel.signInAuthSuccess != null) {
                    onNavigateToNotes()
                }
            }


            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                var email by remember { mutableStateOf(userEmail ?: "") }
                var password by remember { mutableStateOf("") }


                val fieldValidity = remember { mutableStateMapOf<String, Boolean>() }

                val isFormValid by remember {
                    derivedStateOf(calculation = { fieldValidity.values.all { it } })
                }

                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 28.sp
                )

                Text(
                    text = stringResource(R.string.signin_desc),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp),
                )

                EmailTextField(
                    value = email,
                    title = stringResource(R.string.email),
                    placeholder = stringResource(R.string.email_placeholder),
                    onValueChange = { email = it },
                    onContentValidity = { key, value -> fieldValidity[key] = value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    isRequired = true
                )

                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    onContentValidity = { key, value -> fieldValidity[key] = value },
                    title = stringResource(R.string.password),
                    placeholder = stringResource(R.string.type_your_password),
                    modifier = Modifier.fillMaxWidth(),
                    isRequired = true
                )

                AnimatedVisibility(
                    visible = viewModel.signInErrorCode != null,
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = Validations.getAuthError(context, viewModel.signInErrorCode),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                }

                SubmitButton(
                    text = stringResource(R.string.sign_in).uppercase(),
                    onClick = {
                        val signInRequest = SignInRequest(email, password)
                        viewModel.signIn(signInRequest)
                    },
                    enabled = isFormValid,
                    isLoading = viewModel.signInLoading
                )

                Spacer(modifier = Modifier.weight(1f))
                TextFooter(
                    textLabel = stringResource(R.string.new_to_one_notes),
                    textAction = stringResource(R.string.create_an_account),
                    actionClick = onNavigateToSignUp
                )

            }
        }
    }
}

