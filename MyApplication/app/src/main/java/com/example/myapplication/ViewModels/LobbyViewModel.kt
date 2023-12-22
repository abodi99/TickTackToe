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

    init {
        viewModelScope.launch {
            SupabaseService.games
                .onEach { gamesState.value = listOf(it) }
        }

        // Observe changes to the list of users in the lobby
        currentUser = SupabaseService.player

        // Observe changes to the list of users using StateFlow
        SupabaseService.users
            .onEach {
                _useruserstate.value = listOf(it)
            }
    }







        suspend fun joinLobby(playerName: String) {
        val player = Player(name = playerName)
        Log.w("myApp", "it got the player name");
        SupabaseService.joinLobby(player)
    }
        /*
    suspend fun inviteOpponent(opponent: Player, gameViewModel: GameViewModel) {
        SupabaseService.invite(opponent)

    }

    suspend fun acceptInvitation(game: Game, gameViewModel: GameViewModel) {
        SupabaseService.acceptInvite(game)
    }
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

