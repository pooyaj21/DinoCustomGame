package com.example.loadingtrex.dinoGame.score


internal object Score {
    private var currentInt: Int = 0
    private var highestInt: Int = 0

    val hasHighScore: Boolean
        get() = highestInt != 0

    val current: String
        get() = currentInt.formatToFourDigits()
    val highest: String
        get() = highestInt.formatToFourDigits()

    fun setNewScore() {
        if (highestInt < currentInt) {
            highestInt = currentInt
        }
    }

    fun resetCurrentScore() {
        currentInt = 0
    }

    fun addToCurrent() {
        currentInt++
    }

    private fun Int.formatToFourDigits(): String {
        val text = this.toString()
        return when {
            text.length < 4 -> text.padStart(4, '0') // Pad with leading zeros
            text.length > 4 -> text.substring(0, 4)   // Truncate to four digits
            else -> text // Return unchanged if already four digits
        }
    }
}