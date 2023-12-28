package com.example.myapplication.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import io.garrit.android.multiplayer.SupabaseService


data class Cell(
    val x: Int,
    val y: Int,
    var symbol: Char = ' ',
    var color: Color = Color.Gray,
    var cellOwnerName: String = "",
    var alpha: Float = 1.0f, // Default alpha value (fully opaque)
    var isOccupied: Boolean = false
)

class Board(val rows: Int, val columns: Int) {
    var cells: List<List<Cell>> by mutableStateOf(
        List(rows) { row ->
            List(columns) { col ->
                Cell(x = row, y = col)
            }
        }
    )

    var player1 = SupabaseService.currentGame?.player1


}
