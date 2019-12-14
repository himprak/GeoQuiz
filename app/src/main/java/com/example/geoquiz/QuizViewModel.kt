package com.example.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_india, false),
        Question(R.string.question_microsoft, false),
        Question(R.string.question_zero, true))

    var currentIndex = 0
    val doneIndices = mutableSetOf<Int>()
    var score = 0
    var isCheater = false

    val currentQuestionText: Int
      get() = questionBank[currentIndex].textResId

    val currentQuestionAnswer: Boolean
      get() = questionBank[currentIndex].answer

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        if(currentIndex == 0) {
            currentIndex = questionBank.size - 1
        } else {
            currentIndex = currentIndex - 1
        }
    }

    fun questionAttempted() : Boolean {
        return doneIndices.contains(currentIndex)
    }

    fun markQuestionAttempted() {
        doneIndices.add(currentIndex)
    }

    fun numAttempts() : Int {
        return doneIndices.size
    }

    fun incrementScore() {
        score += 1
    }

    fun currentScore() : Int {
        return score
    }
}