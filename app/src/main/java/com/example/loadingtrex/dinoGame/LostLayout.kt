package com.example.loadingtrex.dinoGame

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.*
import com.example.loadingtrex.R

@SuppressLint("ViewConstructor")
class LostLayout(context: Context, private val onRetry: () -> Unit) : LinearLayout(context) {
    private val imageView = ImageView(context).apply {
        setImageResource(R.drawable.dino_dead)
    }
    private val textView = TextView(context).apply {
        text = "You Lost!"
        textAlignment = TEXT_ALIGNMENT_CENTER
    }
    private val retryButton = Button(context).apply {
        text = "Retry"
        setOnClickListener {
            onRetry()
        }
    }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )

        val layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        addView(imageView, layoutParams)
        addView(textView, layoutParams)
        addView(
            retryButton, LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }
}
