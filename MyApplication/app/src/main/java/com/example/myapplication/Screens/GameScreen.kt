package com.example.myapplication.Screens

import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ViewModels.GameViewModel
import com.example.myapplication.data.Cell
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content
import java.nio.file.Files.lines



@Composable
fun TicTacToeGrid(navController: NavController, gameViewModel: GameViewModel) {




    LaunchedEffect(key1 = true) {
        try {
            // Call the suspend function from the ViewModel
            SupabaseService.playerReady()
            Log.d("player  ", "is ready")
        } catch (e: Exception) {
            Log.e("error ", "player not ready ${e.message}", e)
            // Handle any exceptions or errors
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Tick Tack Toe",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(12.dp),
                fontFamily = FontFamily.Cursive

            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    gameViewModel.leave()
                    gameViewModel.reset()
                    navController.popBackStack()

                }
            ) {
                Text(text = "Leave")
            }
        }
        if (gameViewModel.checkWin() || gameViewModel.checkDraw()){
            Text(
                text = gameViewModel.winner,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = if (gameViewModel.player1Turn) "Your turn" else "Oponents Turn",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )





        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)


        ) {
            BoardGrid(gameViewModel)

        }
    }
}


@Composable
fun BoardGrid(gameViewModel: GameViewModel) {

    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(20.dp))
            .aspectRatio(1f)
            .fillMaxWidth()
    ) {

        lines()


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (row in 0..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(), // Ensure each row takes full width
                    horizontalArrangement = Arrangement.SpaceEvenly // Evenly distribute cells
                ) {
                    for (column in 0..2) {
                        // Content for each cell here
                        Box(
                            //modifier = Modifier
                              //  .size(100.dp) // Set fixed cell size
                        ) {
                            val cell = gameViewModel.board.cells[row][column]
                            Cell(
                                cell = cell,
                                onCellClick = {
                                    if (gameViewModel.player1Turn && !gameViewModel.checkWin() && !gameViewModel.checkDraw() ) {
                                            gameViewModel.updateCell(cell.x, cell.y )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun lines(){
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            start = Offset(x = size.width * 1/3  , 0f),
            end = Offset(x = size.width * 1/3, size.height),
            color = Color.Black,
            strokeWidth = 2f
        )
        drawLine(
            start = Offset(x = size.width * 2/3  , 0f),
            end = Offset(x = size.width * 2/3, size.height),
            color = Color.Black,
            strokeWidth = 2f
        )
        drawLine(
            start = Offset(x = 0f  , size.height* 1/3),
            end = Offset(x = size.width , size.height* 1/3),
            color = Color.Black,
            strokeWidth = 2f
        )
        drawLine(
            start = Offset(x = 0f  , size.height* 2/3),
            end = Offset(x = size.width , size.height* 2/3),
            color = Color.Black,
            strokeWidth = 2f
        )
    }
}

@Composable
fun Cell(cell: Cell, onCellClick: () -> Unit) {
Box(
 modifier = Modifier
     .size(100.dp)
     .clickable {
         onCellClick()
     }


) {
     Text(
         text = cell.symbol.toString(),
         fontSize = 80.sp,
         color = Color.Black,
         modifier = Modifier.align(Alignment.Center)

     )
 }
}
