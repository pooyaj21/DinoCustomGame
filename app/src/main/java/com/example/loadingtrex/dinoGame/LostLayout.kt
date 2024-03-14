package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import android.widget.LinearLayout.VERTICAL
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import com.example.loadingtrex.R
import com.example.loadingtrex.dinoGame.score.Score
import com.example.loadingtrex.dinoGame.score.ScoreView

@SuppressLint("ViewConstructor")
internal class LostLayout(
    context: Context, private val onRetry: () -> Unit
) : ConstraintLayout(context) {

    private val highestScoreView = ScoreView.Lost(context).apply {
        id = generateViewId()
        setTitle("Highest Score:")
        setScore(Score.highest)
    }

    private val lastScoreView = ScoreView.Lost(context).apply {
        id = generateViewId()
        setTitle("Last Score:")
        setScore(Score.current)
    }

    private val middleView = LinearLayout(context).apply {
        id = generateViewId()
        orientation = VERTICAL
        gravity = Gravity.CENTER

        val imageView = ImageView(context).apply {
            setImageResource(R.drawable.dino_dead)
        }
        val textView = TextView(context).apply {
            text = "G A M E  O V E R"
            textAlignment = TEXT_ALIGNMENT_CENTER
            setTypeface(null, Typeface.BOLD)
        }
        val retryButton = Button(context).apply {
            setBackgroundResource(R.drawable.retry)
            setOnClickListener {
                Score.resetCurrentScore()
                onRetry()
            }
        }

        addView(imageView, LayoutParams(180, 180))
        addView(textView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        addView(retryButton, LayoutParams(120, 150))
    }

    init {
        layoutParams = LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )


        addView(highestScoreView)
        addView(middleView)
        addView(lastScoreView)

        val constraintSet = ConstraintSet()
        constraintSet.apply {
            clone(this@LostLayout)
            //HighScore
            connect(highestScoreView.id, TOP, PARENT_ID, TOP)
            connect(highestScoreView.id, BOTTOM, PARENT_ID, BOTTOM)
            connect(highestScoreView.id, START, PARENT_ID, START)
            connect(highestScoreView.id, END, middleView.id, START)

            //Middle
            connect(middleView.id, TOP, PARENT_ID, TOP)
            connect(middleView.id, BOTTOM, PARENT_ID, BOTTOM)
            connect(middleView.id, START, PARENT_ID, START)
            connect(middleView.id, END, PARENT_ID, END)

            //LastScore
            connect(lastScoreView.id, TOP, PARENT_ID, TOP)
            connect(lastScoreView.id, BOTTOM, PARENT_ID, BOTTOM)
            connect(lastScoreView.id, START, middleView.id, END)
            connect(lastScoreView.id, END, PARENT_ID, END)

            applyTo(this@LostLayout)
        }
    }

    fun updateScore() {
        highestScoreView.setScore(Score.highest)
        lastScoreView.setScore(Score.current)
    }
}
