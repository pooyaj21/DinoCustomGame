package com.example.loadingtrex.dinoGame

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class DinoGame(context: Context) : FrameLayout(context) {
    private var highestScore = 0

    private var gameLayout = GameLayout(context, highestScore = highestScore, onLost = {
        replaceView(lostLayout)
    })
    private lateinit var lostLayout: LostLayout

    init {
        lostLayout = LostLayout(context, onRetry = {
            gameLayout = GameLayout(context, highestScore = highestScore, onLost = {
                replaceView(lostLayout)
            })
            replaceView(gameLayout)
        })
        addView(gameLayout)
    }

    private fun replaceView(newView: View) {
        if (highestScore < gameLayout.getTheScore()) highestScore = gameLayout.getTheScore()
        removeAllViews()
        addView(newView)
    }

    fun onDestroy() {
        gameLayout.onDestroy()
    }
}