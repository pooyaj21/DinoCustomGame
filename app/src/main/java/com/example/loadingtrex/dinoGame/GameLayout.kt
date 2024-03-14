package com.example.loadingtrex.dinoGame

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import com.example.loadingtrex.R
import com.example.loadingtrex.dinoGame.score.ScoreView
import kotlinx.coroutines.*
import java.util.*

@SuppressLint("ViewConstructor")
internal class GameLayout(
    context: Context,
    private val onLost: () -> Unit
) : FrameLayout(context) {

    private val dino = Dino(context)
    private val middleLine = View(context).apply {
        setBackgroundColor(Color.BLACK)
    }
    private val scoreView = ScoreView.Game(context)
    private val obstacles = mutableListOf<Obstacle>()
    private var obstacleJob: Job? = null

    private val appLogos = listOf(R.drawable.pharma, R.drawable.care, R.drawable.mataq)

    init {
        minimumHeight = dino.measure + dino.jumpHeight + 100
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, minimumHeight)
        setBackgroundColor(Color.TRANSPARENT)
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dino.jump()
                    performClick()
                    true
                }
                else -> {
                    dino.jump()
                    performClick()
                    true
                }
            }
        }
        setOnLongClickListener {
            dino.jump()
            performClick()
        }

        addView(dino, LayoutParams(dino.measure, dino.measure))
        val scoreLayoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.END
            setMargins(0, 20, 10, 0)
        }
        addView(scoreView, scoreLayoutParams)
        addView(middleLine, LayoutParams(LayoutParams.MATCH_PARENT, 10))
        startObstacleGenerator()
        startScoreIncrementer()
    }

    private fun startScoreIncrementer() {
        CoroutineScope(Dispatchers.Main).launch {
            while (!dino.isDead) {
                delay(50)
                scoreView.addScore()
            }
        }
    }

    private fun startObstacleGenerator() {
        obstacleJob = CoroutineScope(Dispatchers.Main).launch {
            while (!dino.isDead) {
                delay((Random().nextInt(2000) + 1000).toLong()) // Random delay between 1 to 3 seconds
                addObstacle(appLogos[Random().nextInt(appLogos.size)])
            }
            obstacleJob?.cancel()
        }
    }

    private fun addObstacle(imageView: Int) {
        val obstacle = Obstacle(context, width, height)
        obstacle.setImageResource(imageView)
        obstacles.add(obstacle)
        addView(obstacle)
        val animation = ObjectAnimator.ofPropertyValuesHolder(
            obstacle, PropertyValuesHolder.ofFloat("x", obstacle.x, -500F)
        ).apply {
            duration = 3000L
        }
        animation.addUpdateListener {
            if (isCollision(obstacle)) {
                dino.isDead = true
                animation.cancel()
                obstacleJob?.cancel()
                onLost()
            }
        }
        animation.doOnEnd {
            if (!dino.isDead) {
                this@GameLayout.removeView(obstacle)
                obstacles.remove(obstacle)
            }
        }
        animation.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (dino.y == 0F) dino.changeHeight(height / 2F)
        if (middleLine.y == 0F) middleLine.y = (height / 2F) + dino.height
    }

    private fun isCollision(obstacle: Obstacle): Boolean {
        val rect1 = Rect()
        dino.getHitRect(rect1)

        val rect2 = Rect()
        obstacle.getHitRect(rect2)
        val smallerRectWidth = (rect2.width() * 0.5).toInt() //50% of obstacle width
        val smallerRectHeight = (rect2.height() * 0.5).toInt() //50% of obstacle height
        rect2.left = rect2.left + (rect2.width() - smallerRectWidth) / 2
        rect2.top = rect2.top + (rect2.height() - smallerRectHeight) / 2
        rect2.right = rect2.left + smallerRectWidth
        rect2.bottom = rect2.top + smallerRectHeight

        return rect1.intersect(rect2)
    }

    fun onTouch(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dino.jump()
                true
            }
            else -> {
                dino.jump()
                true
            }
        }
    }

    fun onDestroy() {
        obstacleJob?.cancel()
    }
}
