package com.example.loadingtrex.dinoGame

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class DinoGame(context: Context) : FrameLayout(context) {
    private val gameLayout = GameLayout(context, onLost = {
        replaceView(lostLayout)
    })
    private lateinit var lostLayout: LostLayout

    init {
        lostLayout = LostLayout(context, onRetry = {
            replaceView(GameLayout(context, onLost = {
                replaceView(lostLayout)
            }))
        })
        addView(gameLayout)
    }

    private fun replaceView(newView: View) {
        removeAllViews()
        addView(newView)
    }

    fun onDestroy(){
        gameLayout.onDestroy()
    }
}