package com.example.loadingtrex.dinoGame

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class DinoGame(context: Context) : FrameLayout(context) {
    private var highestScore = 0

    private val gameLayout = GameLayout(context, highestScore = highestScore, onLost = {
        replaceView(lostLayout)
    })
    private lateinit var lostLayout: LostLayout

    init {
        lostLayout = LostLayout(context, onRetry = {
            replaceView(GameLayout(context, highestScore = highestScore, onLost = {
                replaceView(lostLayout)
            }))
        })
        addView(gameLayout)
    }

    private fun replaceView(newView: View) {
        highestScore = gameLayout.getTheScore()
        removeAllViews()
        addView(newView)
    }

    fun onDestroy() {
        gameLayout.onDestroy()
    }
}