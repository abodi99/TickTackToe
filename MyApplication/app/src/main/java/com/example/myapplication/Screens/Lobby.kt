package com.example.myapplication.Screens
import LobbyViewModel
import android.util.Log

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear

import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ViewModels.GameViewModel
import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.ServerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavController, gameViewModel: GameViewModel, lobbyViewModel: LobbyViewModel, scope: CoroutineScope = rememberCoroutineScope(),playerName : String ) {

    val serverState = gameViewModel.serverState.collectAsState()


    // Launch a coroutine in the Composable scope
    LaunchedEffect(key1 = true) {
        try {
            // Call the suspend function from the ViewModel
            lobbyViewModel.joinLobby(playerName)
            Log.d("ViewModelScope", "Co routine finished")
        } catch (e: Exception) {
            Log.e("ViewModelScope", "Coroutine failed: ${e.message}", e)
            // Handle any exceptions or errors
        }
    }
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
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "Lobby",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive

                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        // Simulate some asynchronous task
                        lobbyViewModel.leavelobby()

                    }
                    navController.popBackStack()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.homescreen),
                    contentDescription = "go to home"
                )

            }
        }


        Spacer(modifier = Modifier.height(5.dp))

        if (lobbyViewModel.filterCurrentUser(lobbyViewModel.useruserstate).isEmpty()) {

            Text(
                text = "No available players",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

        }

        LazyColumn(
            modifier = Modifier
                .weight(0.60f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(lobbyViewModel.filterCurrentUser(lobbyViewModel.useruserstate)) { user ->
                UserItem(user, gameViewModel) {
                    scope.launch {
                        lobbyViewModel.inviteOpponent(it, gameViewModel)
                    }
                }
            }


        }

        LazyColumn(
            modifier = Modifier
                // .weight(0.40f)
                .padding(16.dp),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(lobbyViewModel.games) { game ->
                GameInvitationItem(game, gameViewModel) { accepted ->

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


//GameInvitations1(lobbyViewModel = LobbyViewModel(), navController = navController)


@Composable
fun UserItem(
    user: Player,
    gameViewModel: GameViewModel,
    onInviteClicked: (Player) -> Unit
) {
    var isInvited by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Sharp.Person,
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
                .size(70.dp)
                .shadow(
                    100.dp,
                    ambientColor = Color.White,
                    spotColor = Color.White,
                    shape = RoundedCornerShape(40.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                if (isInvited) Color.Gray else Color(0xFF673AB7),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(40.dp),
            enabled = !isInvited,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.invite),
                contentDescription = "invite",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


@Composable
fun GameInvitationItem(
    game: Game,
    gameViewModel: GameViewModel,
    onItemClicked: (Boolean) -> Unit,
) {
    Column {

        Row {
            Text(text = "Invitation from ")
            Text(text = game.player1.name)
        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {


            IconButton(
                onClick = {
                    onItemClicked(true)
                    gameViewModel.player1Turn = false
                },
                modifier = Modifier
                    .shadow(
                        100.dp,
                        ambientColor = Color.White,
                        spotColor = Color.White,
                        shape = RoundedCornerShape(3.dp)
                    ),


                ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Accept",
                    tint = Color.Green
                )
            }
            Spacer(modifier = Modifier.width(1.dp))
            IconButton(
                onClick = { onItemClicked(false) },
                modifier = Modifier
                    .shadow(
                        100.dp,
                        ambientColor = Color.White,
                        spotColor = Color.White,
                        shape = RoundedCornerShape(3.dp)
                    ),


                ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Deny",
                    tint = Color.Red
                )
            }

        }
    }

}