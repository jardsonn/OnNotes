package com.jalloft.onnotes.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jalloft.onnotes.R
import com.jalloft.onnotes.data.Note
import com.jalloft.onnotes.ui.components.AppNameText
import com.jalloft.onnotes.common.NoteUiCommon
import com.jalloft.onnotes.common.SessionPrefs
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSignIn: (String?) -> Unit,
    onAddNewNote: () -> Unit,
    onNavigateToNote: (UUID) -> Unit,
    isReloadNotesList: Boolean,
) {
//    val detailViewModel: HomeViewModel by viewModel()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val listState = rememberLazyStaggeredGridState()
    val showAddNoteButton by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    val notes by viewModel.notes.collectAsState()

    val sessionPrefs = koinInject<SessionPrefs>()

    LaunchedEffect(key1 = viewModel.errorNotesRequest) {
        if (viewModel.errorNotesRequest == null) {
//            onNavigateToSignIn(sessionPrefs.getUser()?.email)
//            return@LaunchedEffect
        }
    }

    LaunchedEffect(key1 = isReloadNotesList) {
        if (isReloadNotesList) {
            viewModel.getNote(false)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { AppNameText(fontSize = 32.sp) },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                ),
                actions = {
                    if (notes.isNotEmpty()) {
                        ActionMenuItem(
                            onClick = { /*TODO*/ },
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Buscar anotação"
                        )
                    }
                    ActionMenuItem(
                        onClick = { /*TODO*/ },
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Meu perfil"
                    )
                }
            )
        },
        floatingActionButton = {
            if (notes.isNotEmpty()) {
                AddNoteFab(
                    isVisibleBecauseOfScrolling = showAddNoteButton,
                    onClick = onAddNewNote
                )
            }
        }
    ) { innerPadding ->
        if (viewModel.loadingNotesRequest) {
            ContentLoading(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        } else if (notes.isNotEmpty()) {
            ContentNoteList(
                notes,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onClickNote = onNavigateToNote,
                state = listState
            )
        } else {
            ContentEmptyNote(
                onAddNote = onAddNewNote,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }


}

@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp),
        content = {
            repeat(20) {
                item {
                    ShimmerItem(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
                item {
                    ShimmerItem(
                        Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    )
                }
                item {
                    ShimmerItem(
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
                item {
                    ShimmerItem(
                        Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    )
                }
            }
        },
    )
}

@Composable
fun ShimmerItem(modifier: Modifier) {
    Surface(
        modifier = modifier.shimmer(),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(.1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .width(110.dp)
                    .height(16.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(.1f), CircleShape)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.onSurface.copy(.1f),
                        RoundedCornerShape(10.dp)
                    )
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Spacer(
                modifier = Modifier
                    .width(70.dp)
                    .height(16.dp)
                    .align(Alignment.End)
                    .background(MaterialTheme.colorScheme.onSurface.copy(.1f), CircleShape)
            )
        }
    }
}

@Composable
fun ContentEmptyNote(onAddNote: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .offset(y = -(50.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_notes),
                contentDescription = null,
                Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.no_notes).uppercase(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 24.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.no_notes_message),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp),
            )
        }

        Button(
            onClick = onAddNote,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 16.dp)
        ) {
            Text(text = stringResource(R.string.create_note).uppercase())
        }
    }
}

@Composable
fun ContentNoteList(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    onClickNote: (UUID) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        state = state,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp),
        content = {
            items(notes) { note -> NoteItem(note, onClick = { note.id?.let { onClickNote(it) } }) }
        },
    )
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(10.dp),
        color = NoteUiCommon.generateColorFromText(note.content.orEmpty()),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        ) {
            Text(
                text = note.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = note.content.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(.5f),
                maxLines = NoteUiCommon.getMaxLinesForContent(note.content.orEmpty()),
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = NoteUiCommon.formatRelativeTime(note.createdAt),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(.5f),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.End
            )

        }
    }

}


@Composable
fun ActionMenuItem(onClick: () -> Unit, imageVector: ImageVector, contentDescription: String?) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}


@Composable
private fun AddNoteFab(
    modifier: Modifier = Modifier,
    isVisibleBecauseOfScrolling: Boolean,
    onClick: () -> Unit,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = scaleIn(
            animationSpec = keyframes {
                durationMillis = 250
            }
        ),
        exit = scaleOut(
            animationSpec = keyframes {
                durationMillis = 300
            }
        )
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Adicionar nova anotação"
            )
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun PreviewHome() {
//    OnNotesTheme {
//        HomeScreen()
//    }
//}


