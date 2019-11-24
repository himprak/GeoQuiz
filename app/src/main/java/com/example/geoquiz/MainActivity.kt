package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(Question(R.string.question_india, false),
                                      Question(R.string.question_microsoft, false),
                                      Question(R.string.question_zero, true))

    private var currentIndex = 0
    private val doneIndices = mutableSetOf<Int>()
    private var score = 0
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : ImageButton
    private lateinit var prevButton : ImageButton
    private lateinit var questionTextView : TextView
    private lateinit var scoreView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        scoreView = findViewById(R.id.score_view)
        prevButton = findViewById(R.id.prev_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view : View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        prevButton.setOnClickListener {
            if(currentIndex == 0) {
                currentIndex = questionBank.size - 1
            } else {
                currentIndex = currentIndex - 1
            }
            updateQuestion()
        }

        updateQuestion()
        showScore()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        if(doneIndices.contains(currentIndex)) {
            disableAnswers()
        }
        else {
            enableAnswers()
        }
    }

    private fun checkAnswer(userAnswer : Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        doneIndices.add(currentIndex)
        disableAnswers()
        val messageId:Int
        if(userAnswer == correctAnswer) {
            score += 1
            messageId = R.string.correct_toast
        }
        else {
            messageId = R.string.incorrect_toast
        }
        showScore()
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }

    private fun showScore() {
        var scoreStr = "Score "
        scoreStr = scoreStr.plus(score.toString()).plus("/").plus(doneIndices.size)
        scoreView.setText(scoreStr)
    }

    private fun enableAnswers() {
        trueButton.isEnabled = true
        trueButton.isClickable = true
        falseButton.isEnabled = true
        falseButton.isClickable = true
    }

    private fun disableAnswers() {
        trueButton.isEnabled = false
        trueButton.isClickable = false
        falseButton.isEnabled = false
        falseButton.isClickable = false
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}


