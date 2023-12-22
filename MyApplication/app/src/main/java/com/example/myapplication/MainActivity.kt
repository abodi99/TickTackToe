package com.example.myapplication

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Screens.Content
import com.example.myapplication.Screens.LobbyScreen
import com.example.myapplication.Screens.Screen
import com.example.myapplication.Screens.TicTacToeGrid
import com.example.myapplication.ViewModels.LobbyViewModel
import com.example.myapplication.ViewModels.ViewmodelTickTackTo
import com.example.myapplication.ui.theme.MyApplicationTheme
import io.garrit.android.multiplayer.SupabaseService

class MainActivity : ComponentActivity() {


    private val supabaseService = SupabaseService
    val viewModel = viewModels<ViewmodelTickTackTo>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val lobbyViewModel: LobbyViewModel = LobbyViewModel()
            val lobbyViewModel2: LobbyViewModel by viewModels()



            MyApplicationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background

                ) {
                    //JoinLobby()
                    NavigationHost(navController,lobbyViewModel2)

                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, lobbyViewModel: LobbyViewModel) {
    NavHost(navController = navController, startDestination = "main") {
        composable(Screen.Main.route) {
            Content( onJoinLobby = {
                Log.d("Navigation", "Joining lobby...")
                navController.navigate("Lobby")}, lobbyViewModel,navController )
        }
        composable(Screen.Lobby.route + "/{playerName}") { backStackEntry ->
            //Lobby(navController)
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            LobbyScreen(navController, playerName = playerName, lobbyViewModel)

        }
        composable(Screen.Game.route) {
            TicTacToeGrid(navController)
        }
    }
}


