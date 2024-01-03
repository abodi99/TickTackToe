package com.example.myapplication.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Board
import com.example.myapplication.data.Cell
import io.garrit.android.multiplayer.ActionResult
import io.garrit.android.multiplayer.Game
import io.garrit.android.multiplayer.GameResult
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseCallback
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log
import androidx.compose.ui.platform.LocalContext
import io.garrit.android.multiplayer.SupabaseService.gameFinish
import io.ktor.client.utils.EmptyContent.status


class GameViewModel : ViewModel(), SupabaseCallback {
    var _board = Board(rows = 3, columns = 3)
    var playerReady1 by mutableStateOf(false)
    var playerReady2 by mutableStateOf(false)
    var player1Turn by mutableStateOf(false)
    val gamesState = MutableStateFlow<List<Game>>(emptyList())
    val serverState = SupabaseService.serverState
    val columns = 3
    val rows = 3
    val currentPlayer
        get() = SupabaseService.player
    val game = SupabaseService.currentGame

    val board: Board
        get() = _board


    var winner = ""
    var winningSymbol = ' '


    init {
        viewModelScope.launch {
            SupabaseService.games
                .onEach { gamesState.value = listOf(it) }
        }
        SupabaseService.callbackHandler = this
    }

        fun reset(){
            _board = Board(rows = 3, columns = 3)

        }


    fun updateCell(row: Int, column: Int) {
        var symbol : Char
        if (!board.cells[row][column].isOccupied) {
          if (  player1Turn) {
                symbol = 'X'

            } else {
                symbol = 'O'
            }
            board.cells = board.cells.mapIndexed { rowIndex, rowList ->
                rowList.mapIndexed { colIndex, cell ->
                    if (rowIndex == row && colIndex == column) {
                        Cell(row, column, symbol, true)
                        //checkWin( )
                    } else {
                        cell
                    }
                }
            }


                viewModelScope.launch {
                    SupabaseService.sendTurn(row, column)
                    SupabaseService.releaseTurn()

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
        updateCell(x, y)
    }

    override suspend fun answerHandler(status: ActionResult) {
        println("actionHandler Not yet implemented")
    }

    override suspend fun finishHandler(status: GameResult) {
        checkWin()
        checkDraw()
    }




    fun leave(){
        viewModelScope.launch {
            SupabaseService.leaveGame()
        }
    }




    fun checkWin(): Boolean {
        var hasWinner = false


        // Check rows for a win
        for (i in 0 until rows) {
            if (board.cells[i][0].symbol == board.cells[i][1].symbol &&
                board.cells[i][1].symbol == board.cells[i][2].symbol &&
                board.cells[i][0].symbol != ' ') {
                hasWinner = true
                winningSymbol = board.cells[i][0].symbol
                break
            }
        }

        // Check columns for a win
        for (i in 0 until columns) {
            if (board.cells[0][i].symbol == board.cells[1][i].symbol &&
                board.cells[1][i].symbol == board.cells[2][i].symbol &&
                board.cells[0][i].symbol != ' ') {
                hasWinner = true
                winningSymbol = board.cells[0][i].symbol
                break
            }
        }

        // Check diagonals for a win
        if (board.cells[0][0].symbol == board.cells[1][1].symbol &&
            board.cells[1][1].symbol == board.cells[2][2].symbol &&
            board.cells[0][0].symbol != ' ') {
            hasWinner = true
            winningSymbol = board.cells[0][0].symbol
        }

        if (board.cells[0][2].symbol == board.cells[1][1].symbol &&
            board.cells[1][1].symbol == board.cells[2][0].symbol &&
            board.cells[0][2].symbol != ' ') {
            hasWinner = true
            winningSymbol = board.cells[0][2].symbol
        }

        // If there is a winner, update winner information and return true
        if (hasWinner) {
            winner = "winner is player " + winningSymbol
            viewModelScope.launch {
                gameFinish(GameResult.WIN)
            }
            return true
        }

        return false
    }


    fun checkDraw(): Boolean {
        var isTie = true
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                if (board.cells[i][j].symbol == ' ') {
                    isTie = false
                    break
                }
            }
        }

        if (isTie) {
            winner = "It's a Draw"
            viewModelScope.launch {
                gameFinish(GameResult.DRAW)

            }
            return true
        }

        return false
    }



}





