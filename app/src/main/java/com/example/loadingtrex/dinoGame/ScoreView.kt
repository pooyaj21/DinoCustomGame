package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView

@SuppressLint("ViewConstructor")
internal class ScoreView(context: Context, private val highestScore: Int) : LinearLayout(context) {
    var score = 0
        private set
    private val highestScoreTextView = TextView(context).apply {
        text = "HI ${formatToFourDigits(highestScore.toString())}"
        setTypeface(null, Typeface.BOLD)
    }

    private val scoreTextView = TextView(context).apply {
        text = "${formatToFourDigits(score.toString())}"
        setTypeface(null, Typeface.BOLD)
    }

    init {
        orientation = HORIZONTAL

        val layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(10, 0, 10, 0)
            }

        if (highestScore != 0) addView(highestScoreTextView, layoutParams)
        addView(scoreTextView, layoutParams)
    }

    fun addScore() {
        score++
        scoreTextView.text = "${formatToFourDigits(score.toString())}"
    }

    private fun formatToFourDigits(text: String): String {
        return when {
            text.length < 4 -> text.padStart(4, '0') // Pad with leading zeros
            text.length > 4 -> text.substring(0, 4)   // Truncate to four digits
            else -> text // Return unchanged if already four digits
        }
    }
}