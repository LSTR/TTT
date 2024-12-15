package com.lstr.ttt.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TicTacToeViewModel : ViewModel() {
    private val boardSize = 3

    private val _currentPlayer = MutableStateFlow("X")
    val currentPlayer: StateFlow<String> = _currentPlayer

    private val _board = MutableStateFlow(arrayOf(
        arrayOf("", "", ""),
        arrayOf("", "", ""),
        arrayOf("", "", "")
    ))
    val board: StateFlow<Array<Array<String>>> = _board

    fun onCellClick(row: Int, col: Int) {
        if (board.value[row][col].isEmpty()) {
            board.value[row][col] = _currentPlayer.value
            if (!checkForWin()) {
                _currentPlayer.value = if (_currentPlayer.value == "X") "O" else "X"
            }
        }
    }

    fun checkForWin(): Boolean {
        val winConditions = listOf(
            // Rows
            listOf(0 to 0, 0 to 1, 0 to 2),
            listOf(1 to 0, 1 to 1, 1 to 2),
            listOf(2 to 0, 2 to 1, 2 to 2),
            // Columns
            listOf(0 to 0, 1 to 0, 2 to 0),
            listOf(0 to 1, 1 to 1, 2 to 1),
            listOf(0 to 2, 1 to 2, 2 to 2),
            // Diagonals
            listOf(0 to 0, 1 to 1, 2 to 2),
            listOf(0 to 2, 1 to 1, 2 to 0)
        )

        for (condition in winConditions) {
            val (a, b, c) = condition
            if (board.value[a.first][a.second] == board.value[b.first][b.second] &&
                board.value[b.first][b.second] == board.value[c.first][c.second] &&
                board.value[a.first][a.second].isNotEmpty()) {
                return true
            }
        }
        return false
    }

    fun resetGame() {
        _board.value = Array(boardSize) { Array(boardSize) { "" } }
        _currentPlayer.value = "X"
    }
}
