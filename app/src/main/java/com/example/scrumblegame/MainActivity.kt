package com.example.scrumblegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private val words = listOf("android", "activity", "scramble", "game", "developer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrumbleGame(words)
        }
    }
}

@Composable
fun ScrumbleGame(words: List<String>) {
    var score by remember { mutableStateOf(0) }
    var currentWord by remember { mutableStateOf(words.random()) }
    var scrambledWord by remember { mutableStateOf(scrambleWord(currentWord)) }
    var userGuess by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unscramble", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Score: $score", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = scrambledWord, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Unscramble the word using all the letters", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = userGuess,
            onValueChange = { userGuess = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(
                    Modifier.padding(16.dp)
                ) {
                    if (userGuess.isEmpty()) {
                        Text("Enter your word")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                if (userGuess.lowercase() == currentWord) {
                    score++
                    currentWord = words.random()
                    scrambledWord = scrambleWord(currentWord)
                    userGuess = ""
                } else {
                    userGuess = ""
                }
            }) {
                Text("Submit")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                currentWord = words.random()
                scrambledWord = scrambleWord(currentWord)
                userGuess = ""
            }) {
                Text("Skip")
            }
        }
    }
}

fun scrambleWord(word: String): String {
    val letters = word.toCharArray().toList().shuffled()
    return letters.joinToString("")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScrumbleGame(words = listOf("android", "compose", "scramble", "kotlin", "jetpack"))
}