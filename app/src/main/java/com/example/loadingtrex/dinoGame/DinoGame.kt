package com.example.loadingtrex.dinoGame

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.example.loadingtrex.dinoGame.score.Score

class DinoGame(context: Context) : FrameLayout(context) {

    private var gameLayout = GameLayout(context, onLost = {
        replaceView(lostLayout)
    })
    private lateinit var lostLayout: LostLayout

    init {
        lostLayout = LostLayout(context, onRetry = {
            gameLayout = GameLayout(context, onLost = {
                lostLayout.updateScore()
                replaceView(lostLayout)
            })
            replaceView(gameLayout)
        })
        addView(gameLayout)
    }

    private fun replaceView(newView: View) {
        Score.setNewScore()
        lostLayout.updateScore()
        removeAllViews()
        addView(newView)
    }

    fun onDestroy() {
        gameLayout.onDestroy()
    }
}