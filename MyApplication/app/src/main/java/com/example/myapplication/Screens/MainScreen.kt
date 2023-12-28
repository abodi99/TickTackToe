package com.example.myapplication.Screens

<<<<<<< HEAD
import LobbyViewModel
=======
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
<<<<<<< HEAD
=======
import com.example.myapplication.ViewModels.LobbyViewModel
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.ServerState
import io.garrit.android.multiplayer.SupabaseService
import io.garrit.android.multiplayer.SupabaseService.joinLobby
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
<<<<<<< HEAD
fun Content(onJoinLobby: () -> Unit, lobbyViewModel: LobbyViewModel, navController: NavController ) {
=======
fun Content(onJoinLobby: () -> Unit, lobbyViewModel: LobbyViewModel,navController: NavController ) {
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
    var playerName by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(false) }
    val viewModelScope = lobbyViewModel.viewModelScope



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Type your player name",
            style = MaterialTheme.typography.headlineMedium ,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = playerName,
            onValueChange = {
                playerName = it
                isNameValid = playerName.isNotEmpty() && playerName.length > 1 && playerName.length < 10 && playerName.all { it.isLetter() }
            },
            label = { Text("Player Name") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            isError = !isNameValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Button(
            onClick = {
                if (playerName.isNotEmpty()) {


                    navController.navigate("Lobby/$playerName")


                }




            },
            modifier = Modifier
                .shadow(100.dp, ambientColor = Color.White, spotColor = Color.White, shape = RoundedCornerShape(7.dp))
                .height(55.dp)
                .width(280.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray),
            shape = RectangleShape,        ) {
            Text("Join Lobby")
        }
    }
}

