package com.example.myapplication.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
<<<<<<< HEAD
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Board
import com.example.myapplication.data.Cell
import io.garrit.android.multiplayer.ActionResult
import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.GameResult
import io.garrit.android.multiplayer.SupabaseCallback
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel(), SupabaseCallback {
    private val _board = Board(rows = 6, columns = 7)
    var playerReady1 by mutableStateOf(false)
    var playerReady2 by mutableStateOf(false)
    var player1Turn by mutableStateOf(false)
    val gamesState = MutableStateFlow<List<Game>>(emptyList())
    val serverState = SupabaseService.serverState
    val currentPlayer
        get() = SupabaseService.player
=======
import io.garrit.android.multiplayer.SupabaseService

class ViewmodelTickTackTo: ViewModel() {
    // Access the singleton instance of SupabaseService
    private val supabaseService = SupabaseService
>>>>>>> 9cc1665782210836e0d05a47d3964cef6d1eddc4


    val board: Board
        get() = _board


    init {
        viewModelScope.launch {
            SupabaseService.games
                .onEach { gamesState.value = listOf(it) }
        }
        SupabaseService.callbackHandler = this
        startNewGame()
    }

    fun startNewGame() {
        _board.cells.forEach { row ->
            row.forEach { cell ->
                cell.symbol = ' '
                cell.isOccupied = false
                cell.color = Color.White
                cell.alpha = 0.3f
            }
        }

    }

    override suspend fun playerReadyHandler() {
        playerReady2 = true
        println(playerReady2)

    }

    override suspend fun releaseTurnHandler() {
        player1Turn = !player1Turn
    }

    override suspend fun actionHandler(x: Int, y: Int) {
        println("trying to go in dropdisc")
        //dropDisc(x, false, Color.Red)
        println("could go in!")
        println("actionHandler Not yet implemented")
    }

    override suspend fun answerHandler(status: ActionResult) {
        println("actionHandler Not yet implemented")
    }

    override suspend fun finishHandler(status: GameResult) {
        println("finishHandler Not yet implemented")
    }

}

