package com.jalloft.onnotes.ui.screens.addnotes

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalloft.onnotes.R
import com.jalloft.onnotes.data.dto.note.NoteRequest
import com.jalloft.onnotes.ui.screens.home.HomeViewModel
import com.jalloft.onnotes.ui.theme.OnNotesTheme
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = koinViewModel(),
    onBack: (Boolean) -> Unit
) {

    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(key1 = viewModel.errorAddNewNoteRequest, viewModel.isLoadingAddNewNote) {
        if (viewModel.errorAddNewNoteRequest != null && viewModel.errorAddNewNoteRequest != 0) {
            Toast.makeText(context, "Erro ao adicionar nova anotação", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = viewModel.isSuccessAddNewNote) {
        if (viewModel.isSuccessAddNewNote != null && viewModel.isSuccessAddNewNote == true) {
            onBack(true)
            return@LaunchedEffect
        }
    }



    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                actions = {
                    Button(
                        onClick = {
                            viewModel.addNote(NoteRequest(title, content))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(.1f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        enabled = !viewModel.isLoadingAddNewNote && title.isNotBlank() && content.isNotBlank()
                    ) {
                        if (!viewModel.isLoadingAddNewNote) {
                            Text(text = stringResource(R.string.save).uppercase())
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeCap = StrokeCap.Round,
                                strokeWidth = 3.dp
                            )
                        }
                    }
                },
                navigationIcon = {
                    Button(
                        onClick = { onBack(false) },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground.copy(.1f),
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        enabled = !viewModel.isLoadingAddNewNote
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            TextFieldTitle(
                value = title,
                onValueChange = { title = it },
                enabled = !viewModel.isLoadingAddNewNote,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            )
            TextFieldContent(
                value = content,
                onValueChange = { content = it },
                enabled = !viewModel.isLoadingAddNewNote,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
            )

        }
    }
}

@Composable
fun TextFieldContent(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    enabled: Boolean = true,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.content_placeholder),
                        color = MaterialTheme.colorScheme.onBackground.copy(.2f),
                        fontSize = 18.sp
                    )
                }
                it()
            }
        }
    )
}


@Composable
fun TextFieldTitle(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    enabled: Boolean = true,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        maxLines = 2,
        enabled = enabled,
        textStyle = TextStyle(fontWeight = FontWeight.Black, fontSize = 24.sp),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        ),
        decorationBox = {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.title_placeholder),
                        color = MaterialTheme.colorScheme.onBackground.copy(.2f),
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                }
                it()
            }
        }
    )
}
