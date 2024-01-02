package com.example.myapplication.ViewModels

import androidx.compose.runtime.getValue
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
import io.garrit.android.multiplayer.SupabaseCallback
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class GameViewModel : ViewModel(), SupabaseCallback {
    private val _board = Board(rows = 3, columns = 3)
    var playerReady1 by mutableStateOf(false)
    var playerReady2 by mutableStateOf(false)
    var player1Turn by mutableStateOf(false)
    val gamesState = MutableStateFlow<List<Game>>(emptyList())
    val serverState = SupabaseService.serverState
    val columns = 3
    val rows = 3
    val currentPlayer
        get() = SupabaseService.player


    fun checkWin(board: Array<Array<Char>>): Int {
        // Check rows
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return if (board[i][0] == 'X') 1 else 2
            }
        }

        // Check columns
        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return if (board[0][i] == 'X') 1 else 2
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return if (board[0][0] == 'X') 1 else 2
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return if (board[0][2] == 'X') 1 else 2
        }

        // Check for draw
        if (board.all { row -> row.all { it != ' ' } }) {
            return 0 // Draw
        }

        // No winner yet
        return -1
    }

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
            }
        }

    }

    fun switchTurn() {
        player1Turn = !player1Turn
    }

    fun updateCell(row: Int, column: Int) {
        var color: Color? = null;
        var symbol: Char? = null;



        if(!board.cells[row][column].isOccupied){
            if(player1Turn){
                color = Color.Cyan
                symbol = 'X'

            }
            else {
                color = Color.Red
                symbol = 'O'
            }
            board.cells = board.cells.mapIndexed { rowIndex, rowList ->
                rowList.mapIndexed { colIndex, cell ->
                    if (rowIndex == row && colIndex == column) {
                        Cell(row, column, symbol, color,currentPlayer!!.name,  true)
                        //checkWin( )
                    } else {
                        cell
                    }
                }
            }
            viewModelScope.launch {
                SupabaseService.sendTurn(row,column)
                SupabaseService.releaseTurn()

            }

        }



    }


    fun checkWin(board: List<List<Cell>>): Int {
        // Check rows
        for (row in board) {
            val symbols = row.map { it.symbol }
            if (symbols.all { it == 'X' } || symbols.all { it == 'O' }) {
                return if (symbols[0] == 'X') 1 else 2
            }
        }

        // Check columns
        for (col in 0 until board[0].size) {
            val symbols = board.map { it[col] }.map { it.symbol }
            if (symbols.all { it == 'X' } || symbols.all { it == 'O' }) {
                return if (symbols[0] == 'X') 1 else 2
            }
        }

        // Check diagonals
        val mainDiagonalSymbols = board.indices.map { board[it][it] }.map { it.symbol }
        val secondaryDiagonalSymbols = board.indices.map { board[it][board.lastIndex - it] }.map { it.symbol }
        if (mainDiagonalSymbols.all { it == 'X' } || mainDiagonalSymbols.all { it == 'O' }) {
            return if (mainDiagonalSymbols[0] == 'X') 1 else 2
        }
        if (secondaryDiagonalSymbols.all { it == 'X' } || secondaryDiagonalSymbols.all { it == 'O' }) {
            return if (secondaryDiagonalSymbols[0] == 'X') 1 else 2
        }

        // Check for draw
        if (board.all { row -> row.all { it.symbol != ' ' } }) {
            return 0 // Draw
        }

        // No winner yet
        return -1
    }
    fun makeMove(){

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
        updateCell(x, y)
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

