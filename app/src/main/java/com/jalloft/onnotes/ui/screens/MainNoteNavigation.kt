package com.jalloft.onnotes.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jalloft.onnotes.common.SessionPrefs
import com.jalloft.onnotes.ui.screens.auth.signin.SignInScreen
import com.jalloft.onnotes.ui.screens.auth.siginup.SignUpScreen
import com.jalloft.onnotes.ui.screens.addnotes.AddNoteScreen
import com.jalloft.onnotes.ui.screens.home.HomeScreen
import org.koin.compose.koinInject


private const val RELOAD_NOTES_KEY = "reload_notes_key"

@Composable
fun MainNoteNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val sessionPrefs = koinInject<SessionPrefs>()

    val startDestination by remember {
        val route = if (sessionPrefs.getToken() != null) {
            OnNoteDestinations.NotesRoute.route
        } else OnNoteDestinations.WelcomeRoute.route
        mutableStateOf(route)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(OnNoteDestinations.WelcomeRoute.route) {
            WelcomeScreen(
                onNavigateToSignIn = {
                    navController.navigate(OnNoteDestinations.SignInRoute.route.plus("/null"))
                },
                onNavigateToSignUp = {
                    navController.navigate(OnNoteDestinations.SignUpRoute.route)
                }
            )
        }
        composable(
            OnNoteDestinations.SignInRoute.route.plus("/{email}"),
            arguments = listOf(navArgument("email") {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            SignInScreen(
                onBack = { navController.popBackStack() },
                onNavigateToNotes = {
                    navController.navigate(OnNoteDestinations.NotesRoute.route) {
                        popUpTo(OnNoteDestinations.SignUpRoute.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(OnNoteDestinations.SignUpRoute.route) {
                        popUpTo(OnNoteDestinations.SignUpRoute.route) {
                            inclusive = true
                        }
                    }
                },
                userEmail = backStackEntry.arguments?.getString("email")
            )
        }
        composable(OnNoteDestinations.SignUpRoute.route) {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onNavigateToNotes = {
                    navController.navigate(OnNoteDestinations.NotesRoute.route) {
                        popUpTo(OnNoteDestinations.SignUpRoute.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(OnNoteDestinations.SignInRoute.route.plus("/null")) {
                        popUpTo(OnNoteDestinations.SignInRoute.route.plus("/null")) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(OnNoteDestinations.NotesRoute.route) {
            val isReloadNotesList =
                navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
                    RELOAD_NOTES_KEY
                )?.value
                    ?: false
            HomeScreen(
                onNavigateToSignIn = { email ->
                    navController.navigate(OnNoteDestinations.SignInRoute.route.plus("/${email}")) {
                        popUpTo(OnNoteDestinations.SignInRoute.route.plus("/${email}")) {
                            inclusive = true
                        }
                    }
                },
                onAddNewNote = { navController.navigate(OnNoteDestinations.AddNoteRoute.route) },
                onNavigateToNote = {},
                isReloadNotesList = isReloadNotesList
            )
            navController.previousBackStackEntry?.savedStateHandle?.set(
                RELOAD_NOTES_KEY,
                false
            )
        }

        composable(OnNoteDestinations.AddNoteRoute.route) {
            AddNoteScreen(
                onBack = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        RELOAD_NOTES_KEY,
                        it
                    )
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class OnNoteDestinations(val route: String) {
    data object WelcomeRoute : OnNoteDestinations("welcome")
    data object SignInRoute : OnNoteDestinations("signin")
    data object SignUpRoute : OnNoteDestinations("signup")
    data object NotesRoute : OnNoteDestinations("notes")
    data object AddNoteRoute : OnNoteDestinations("addnote")
}
