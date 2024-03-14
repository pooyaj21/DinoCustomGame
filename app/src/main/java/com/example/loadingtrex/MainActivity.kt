package com.example.loadingtrex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loadingtrex.dinoGame.DinoGame

class MainActivity : AppCompatActivity() {
    private lateinit var dinoGame: DinoGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dinoGame = DinoGame(this, 0.5)
        setContentView(dinoGame)
    }

    override fun onDestroy() {
        super.onDestroy()
        dinoGame.onDestroy()
    }
}
