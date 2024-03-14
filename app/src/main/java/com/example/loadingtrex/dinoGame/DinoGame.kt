package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.example.loadingtrex.dinoGame.score.Score

@SuppressLint("ViewConstructor")
class DinoGame(
    context: Context,
    private val screenPositionPercentage: Double
) : FrameLayout(context) {

    private var gameLayout = GameLayout(context, onLost = {
        replaceView(lostLayout)
    })
    private lateinit var lostLayout: LostLayout

    private val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, gameLayout.minimumHeight)

    init {
        lostLayout = LostLayout(context, onRetry = {
            gameLayout = GameLayout(context, onLost = {
                lostLayout.updateScore()
                replaceView(lostLayout)
            })
            replaceView(gameLayout)
        })
        addView(gameLayout, layoutParams)
        setOnTouchListener { _, event ->
            gameLayout.onTouch(event)
            performClick()
        }
        positionWithPercentage(screenPositionPercentage)
    }

    private fun replaceView(newView: View) {
        Score.setNewScore()
        lostLayout.updateScore()
        removeAllViews()
        addView(newView, layoutParams)
        positionWithPercentage(screenPositionPercentage)
    }

    private fun positionWithPercentage(screenPercentage: Double) {
        val screenHeight = resources.displayMetrics.heightPixels
        val positionInScreen =
            (screenHeight - gameLayout.minimumHeight) * screenPercentage.toFloat()
        gameLayout.y = positionInScreen
        lostLayout.y = positionInScreen
    }

    fun onDestroy() {
        gameLayout.onDestroy()
    }
}