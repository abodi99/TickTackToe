package com.example.myapplication

import LobbyViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.lifecycle.viewmodel.compose.viewModel
=======
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Screens.Content
import com.example.myapplication.Screens.LobbyScreen
import com.example.myapplication.Screens.Screen
import com.example.myapplication.Screens.TicTacToeGrid
<<<<<<< HEAD
import com.example.myapplication.ViewModels.GameViewModel
=======
import com.example.myapplication.ViewModels.LobbyViewModel
import com.example.myapplication.ViewModels.ViewmodelTickTackTo
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import com.example.myapplication.ui.theme.MyApplicationTheme
import io.garrit.android.multiplayer.SupabaseService

class MainActivity : ComponentActivity() {


    private val supabaseService = SupabaseService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val lobbyViewModel: LobbyViewModel = LobbyViewModel()
<<<<<<< HEAD

            val gameViewModel: GameViewModel = viewModel()
=======
            val lobbyViewModel2: LobbyViewModel by viewModels()
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4



            MyApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background

                ) {
                    //JoinLobby()
<<<<<<< HEAD
                    NavigationHost(navController,lobbyViewModel, gameViewModel)
=======
                    NavigationHost(navController,lobbyViewModel2)
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4

                }
            }
        }
    }
}

@Composable
<<<<<<< HEAD
fun NavigationHost(navController: NavHostController, lobbyViewModel: LobbyViewModel, gameViewModel: GameViewModel) {
=======
fun NavigationHost(navController: NavHostController, lobbyViewModel: LobbyViewModel) {
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
    NavHost(navController = navController, startDestination = "main") {
        composable(Screen.Main.route) {
            Content( onJoinLobby = {
                Log.d("Navigation", "Joining lobby...")
                navController.navigate("Lobby")}, lobbyViewModel,navController )
        }
        composable(Screen.Lobby.route + "/{playerName}") { backStackEntry ->
            //Lobby(navController)
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
<<<<<<< HEAD
          //  LobbyScreen(navController, playerName = playerName, gameViewModel, lobbyViewModel)
            LobbyScreen(navController, gameViewModel, lobbyViewModel, playerName = playerName)
=======
            LobbyScreen(navController, playerName = playerName, lobbyViewModel)
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4

        }
        composable(Screen.Game.route) {
            TicTacToeGrid(navController)
        }
    }
<<<<<<< HEAD


=======
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
}


