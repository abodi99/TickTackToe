package com.example.myapplication.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService


data class Cell(
    val x: Int,
    val y: Int,
    var symbol: Char = ' ',
    var isOccupied: Boolean = false

)

class Board(val rows: Int, val columns: Int) {
    var board = mutableStateListOf<Cell>()


    var cells: List<List<Cell>> by mutableStateOf(
        List(rows) { row ->
            List(columns) { col ->
                Cell(x = row, y = col,' ' , false)
            }
        }
    )



}
