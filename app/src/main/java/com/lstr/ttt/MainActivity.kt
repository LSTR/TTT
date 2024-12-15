package com.lstr.ttt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lstr.ttt.ui.theme.TTTTheme
import com.lstr.ttt.viewmodel.TicTacToeViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = TicTacToeViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val winner = remember { mutableStateOf("") }
            TTTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeApp(viewModel, winner)
                }
            }
        }
    }
}

@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel, winner: MutableState<String>) {
    val currentPlayer = viewModel.currentPlayer.collectAsState().value
    val board = viewModel.board.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Current Player: $currentPlayer")

        if (winner.value.isNotEmpty()) {
            Text(text = "Player ${winner.value} Wins!", color = Color.Red, style = MaterialTheme.typography.headlineMedium)
        }

        Button(onClick = {
            viewModel.resetGame()
            winner.value = ""
        }) {
            Text("Restart")
        }

        Spacer(modifier = Modifier.height(16.dp))

        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    Cell(
                        value = board[row][col],
                        onClick = {
                            viewModel.onCellClick(row, col)
                            winner.value = if (viewModel.checkForWin()) currentPlayer else ""
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Cell(value: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .background(Color.White)
            .border(1.dp, Color.Black)
            .clickable(onClick = onClick)
    ) {
        Text(text = value,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 55.sp
            )
        )
    }
}
