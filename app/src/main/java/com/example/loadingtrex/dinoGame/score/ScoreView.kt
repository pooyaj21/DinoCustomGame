package com.example.loadingtrex.dinoGame.score

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

internal open class ScoreView(context: Context) : LinearLayout(context) {

    class Game(context: Context) : ScoreView(context) {

        private val highestScoreTextView = TextView(context).apply {
            text = "HI ${Score.highest}"
            setTypeface(null, Typeface.BOLD)
        }

        private val scoreTextView = TextView(context).apply {
            text = Score.highest
            setTypeface(null, Typeface.BOLD)
        }

        init {
            orientation = HORIZONTAL

            val layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(10, 0, 10, 0)
                }

            if (Score.hasHighScore) addView(highestScoreTextView, layoutParams)
            addView(scoreTextView, layoutParams)
        }

        fun addScore() {
            Score.addToCurrent()
            scoreTextView.text = Score.current
        }
    }

    class Lost(context: Context) : ScoreView(context) {

        private val titleTextView = TextView(context).apply {
            setTypeface(null, Typeface.BOLD)
        }
        private val scoreTextView = TextView(context).apply {
            setTypeface(null, Typeface.BOLD)
        }

        init {
            orientation = VERTICAL
            gravity = Gravity.CENTER

            val layoutParams = ConstraintLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
            )

            addView(titleTextView, layoutParams)
            addView(scoreTextView, layoutParams)
        }

        fun setTitle(title: String) {
            titleTextView.text = title
        }

        fun setScore(score: String) {
            scoreTextView.text = score
        }
    }

}