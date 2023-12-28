package com.example.myapplication.Screens
<<<<<<< HEAD
import LobbyViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
=======
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
<<<<<<< HEAD
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
=======
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
<<<<<<< HEAD
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
=======

import androidx.navigation.NavController
import com.example.myapplication.ViewModels.LobbyViewModel
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
<<<<<<< HEAD
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ViewModels.GameViewModel
import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.ServerState
import io.garrit.android.multiplayer.SupabaseService.joinLobby
import kotlinx.coroutines.CoroutineScope





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavController, gameViewModel: GameViewModel, lobbyViewModel: LobbyViewModel, scope: CoroutineScope = rememberCoroutineScope(),playerName : String ) {

    val serverState = gameViewModel.serverState.collectAsState()
=======
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService.joinLobby


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LobbyScreen(navController: NavController,playerName: String, lobbyViewModel: LobbyViewModel ) {

    val lobbyViewModel33: LobbyViewModel = LobbyViewModel()
    //val lobbyViewModel: LobbyViewModel = rememberSaveable { LobbyViewModel() }


    val scope = rememberCoroutineScope()

    val viewModelScope = lobbyViewModel.viewModelScope
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4

    // Launch a coroutine in the Composable scope
    LaunchedEffect(key1 = true) {
        try {
            // Call the suspend function from the ViewModel
<<<<<<< HEAD
            lobbyViewModel.joinLobby(playerName)
            Log.d("ViewModelScope", "Co routine finished")
=======
            lobbyViewModel33.joinLobby(playerName)
            Log.d("ViewModelScope", "Coroutine finished")
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
        } catch (e: Exception) {
            Log.e("ViewModelScope", "Coroutine failed: ${e.message}", e)
            // Handle any exceptions or errors
        }
    }
<<<<<<< HEAD
    LaunchedEffect(serverState.value) {
        when (serverState.value) {
            ServerState.LOADING_GAME, ServerState.GAME -> {

                navController.navigate("Game")
            }
            else -> {}
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn (
            modifier = Modifier
                .weight(0.75f)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            items(lobbyViewModel.filterCurrentUser(lobbyViewModel.useruserstate)) { user ->
                UserItem(user , gameViewModel) {
                    scope.launch {
                        lobbyViewModel.inviteOpponent(it, gameViewModel )

                    }

                }
            }

        }

        LazyColumn (
            modifier = Modifier
                .weight(0.25f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            items(lobbyViewModel.gamesState.value) { game ->
                GameInvitationItem(game,gameViewModel) { accepted ->
                    scope.launch {
                        if (accepted) {
                            // Accept invitation and navigate to GameScreen
                            lobbyViewModel.acceptInvitation(game, gameViewModel)
                        } else {
                            // Decline invitation
                            lobbyViewModel.declineInvitation(game)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: Player, gameViewModel: GameViewModel, onInviteClicked: (Player) -> Unit) {
    var isInvited by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Sharp.Person ,
            contentDescription = null,
            modifier = Modifier.size(60.dp)

        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = user.name,
            color = Color.Black,
            style = TextStyle(
                //   fontFamily = wildWestFontFamily3,
                fontSize = 25.sp,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 2f
                )
            ),

            )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onInviteClicked(user)
                isInvited = true
                gameViewModel.player1Turn = true
            },
            modifier = Modifier
                .size(60.dp, 30.dp)
                .shadow(100.dp, ambientColor = Color.White, spotColor = Color.White, shape = RoundedCornerShape(30.dp)),
            colors = ButtonDefaults.buttonColors(  if (isInvited) Color.Gray else Color(0xFF673AB7),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(30.dp),
            enabled = !isInvited,


        ) {
            Text(
                text = "Invite",
                style = TextStyle(
                    fontSize = 13.sp
                )
            )
        }
    }
}


@Composable
fun GameInvitationItem( game: Game,  gameViewModel: GameViewModel, onItemClicked: (Boolean) -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        //       fontFamily = wildWestFontFamily2,
                        fontSize = 16.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(3f, 3f),
                            blurRadius = 2f
                        )
                    )
                ) {
                    append("Game Invitation from ")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        //      fontFamily = wildWestFontFamily2,
                        fontSize = 16.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 2f
                        )
                    )
                ) {
                    append("${game.player1.name}")
                }
            },
            lineHeight = 30.sp
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Button(
                onClick = {
                    onItemClicked(true)
                    gameViewModel.player1Turn = false
                },
                modifier = Modifier
                    .shadow(100.dp, ambientColor = Color.White, spotColor = Color.White, shape = RoundedCornerShape(3.dp)),
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                shape = RectangleShape

            ) {
                Text(
                    "Accept",
                    style = TextStyle(
                        color = Color.White,
                        //      fontFamily = wildWestFontFamily2,
                        fontSize = 16.sp
                    )
                )
            }
            Spacer(modifier = Modifier.width(1.dp))
            Button(
                onClick = { onItemClicked(false) },
                modifier = Modifier
                    .shadow(100.dp, ambientColor = Color.White, spotColor = Color.White, shape = RoundedCornerShape(3.dp)),
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                shape = RectangleShape

            ) {
                Text(
                    "Decline",
                    style = TextStyle(
                        color = Color.White,
                        //  fontFamily = wildWestFontFamily2,
                        fontSize = 16.sp
                    )
                )
            }

        }
    }
=======




    /*
    viewModelScope.launch {
        try {
            // Call the suspend function from the ViewModel
            lobbyViewModel33.joinLobby(playerName)
            Log.d("ViewModelScope", "Coroutine finished")
        } catch (e: Exception) {
            Log.e("ViewModelScope", "Coroutine failed: ${e.message}", e)
            // Handle any exceptions or errors
        }
    }
*/
  //  Text(text = playerName)

    val userUserState by lobbyViewModel33.useruserstate.collectAsState()

    LazyColumn {
        items(userUserState) { player ->
            Text(text = player.name)
        }
    }
/*

        val list = lobbyViewModel33.useruserstate
        LazyColumn {
            items(list) { player ->
                Text(text = player.name)
            }
        }
*/


>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
}



<<<<<<< HEAD
=======









>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4

