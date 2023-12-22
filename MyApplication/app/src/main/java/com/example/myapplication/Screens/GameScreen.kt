package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


enum class Player {
    X, O
}

@Composable
fun TicTacToeGrid(navController: NavController) {

    val buttons = remember { mutableStateListOf<Player>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(9) { it ->
                TicTacToeButton(buttons, it)
            }
        }

        
    }
}

@Composable
fun TicTacToeButton(buttons: MutableList<Player>, index: Int) {
    var player by remember { mutableStateOf<Player?>(null) }

    Button(
        onClick = {
            if (player == null) {
                player = if (buttons.size % 2 == 0) Player.X else Player.O
                buttons.add(player!!)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        when (player) {
            Player.X -> {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
            }
            Player.O -> {
                Icon(imageVector = Icons.Default.Done, contentDescription = null, tint = Color.White)
            }

            else -> {}
        }
    }
}

