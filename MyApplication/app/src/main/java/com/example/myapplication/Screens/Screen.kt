package com.example.myapplication.Screens

sealed class Screen(val route: String){
    object Lobby : Screen( route = "Lobby")
    object Main : Screen( route = "main")
    object Game : Screen(route = "game")


}
