package com.example.myapplication.Screens
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope

import androidx.navigation.NavController
import com.example.myapplication.ViewModels.LobbyViewModel
import io.garrit.android.multiplayer.SupabaseService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import io.garrit.android.multiplayer.Player
import io.garrit.android.multiplayer.SupabaseService.joinLobby


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LobbyScreen(navController: NavController,playerName: String, lobbyViewModel: LobbyViewModel ) {

    val lobbyViewModel33: LobbyViewModel = LobbyViewModel()
    //val lobbyViewModel: LobbyViewModel = rememberSaveable { LobbyViewModel() }


    val scope = rememberCoroutineScope()

    val viewModelScope = lobbyViewModel.viewModelScope

    // Launch a coroutine in the Composable scope
    LaunchedEffect(key1 = true) {
        try {
            // Call the suspend function from the ViewModel
            lobbyViewModel33.joinLobby(playerName)
            Log.d("ViewModelScope", "Coroutine finished")
        } catch (e: Exception) {
            Log.e("ViewModelScope", "Coroutine failed: ${e.message}", e)
            // Handle any exceptions or errors
        }
    }




    /*
    viewModelScope.launch {
        try {
            // Call the suspend function from the ViewModel
            lobbyViewModel33.joinLobby(playerName)
            Log.d("ViewModelScope", "Coroutine finished")
        } catch (e: Exception) {
            Log.e("ViewModelScope", "Coroutine failed: ${e.message}", e)
            // Handle any exceptions or errors
        }
    }
*/
  //  Text(text = playerName)

    val userUserState by lobbyViewModel33.useruserstate.collectAsState()

    LazyColumn {
        items(userUserState) { player ->
            Text(text = player.name)
        }
    }
/*

        val list = lobbyViewModel33.useruserstate
        LazyColumn {
            items(list) { player ->
                Text(text = player.name)
            }
        }
*/


}













