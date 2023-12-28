<<<<<<< HEAD
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.ViewModels.GameViewModel
import io.garrit.android.multiplayer.ActionResult
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn

import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.GameResult
import io.garrit.android.multiplayer.ServerState
import io.garrit.android.multiplayer.SupabaseService.serverState

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class LobbyViewModel : ViewModel() {
    val gamesState = MutableStateFlow<List<Game>>(emptyList())
    var useruserstate = mutableListOf<Player>()
    var currentUser: Player? = null


=======
package com.example.myapplication.ViewModels

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LobbyViewModel : ViewModel() {
    val gamesState = MutableStateFlow<List<Game>>(emptyList())
    private val _useruserstate = MutableStateFlow<List<Player>>(emptyList())
    val useruserstate: StateFlow<List<Player>> get() = _useruserstate.asStateFlow()
    var currentUser: Player? = null

>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
    init {
        viewModelScope.launch {
            SupabaseService.games
                .onEach { gamesState.value = listOf(it) }
        }
<<<<<<< HEAD
        // Observe changes to the list of users in the lobby
        currentUser = SupabaseService.player
        useruserstate = SupabaseService.users.toMutableStateList()
    }


    suspend fun joinLobby(playerName: String) {
=======

        // Observe changes to the list of users in the lobby
        currentUser = SupabaseService.player

        // Observe changes to the list of users using StateFlow
        SupabaseService.users
            .onEach {
                _useruserstate.value = listOf(it)
            }
    }







        suspend fun joinLobby(playerName: String) {
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
        val player = Player(name = playerName)
        Log.w("myApp", "it got the player name");
        SupabaseService.joinLobby(player)
    }
<<<<<<< HEAD

    suspend fun inviteOpponent(opponent: Player, gameViewModel: GameViewModel) {
        SupabaseService.invite(opponent)
=======
        /*
    suspend fun inviteOpponent(opponent: Player, gameViewModel: GameViewModel) {
        SupabaseService.invite(opponent)

>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
    }

    suspend fun acceptInvitation(game: Game, gameViewModel: GameViewModel) {
        SupabaseService.acceptInvite(game)
    }
<<<<<<< HEAD


    suspend fun declineInvitation(game: Game) {
        SupabaseService.declineInvite(game)
    }

    fun filterCurrentUser(users: List<Player>): List<Player> {
        return users.filter { it != currentUser }
    }

}
=======
*/

        suspend fun declineInvitation(game: Game) {
            SupabaseService.declineInvite(game)
        }

}
/*
        fun filterCurrentUser(users: List<Player>): List<Player> {
            return users.filter { it != currentUser }
        }
*/

>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4
