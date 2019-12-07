package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

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

        quizViewModel.currentIndex = savedInstanceState?.getInt("CurrentIndex", 0)?:0
        quizViewModel.score = savedInstanceState?.getInt("Score", 0)?:0
        quizViewModel.doneIndices.addAll(savedInstanceState?.getIntegerArrayList("DoneIndices")?:ArrayList<Int>())

        trueButton = findViewById<Button>(R.id.true_button)
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
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
        showScore()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        if(quizViewModel.questionAttempted()) {
            disableAnswers()
        }
        else {
            enableAnswers()
        }
    }

    private fun checkAnswer(userAnswer : Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        quizViewModel.markQuestionAttempted()
        disableAnswers()
        val messageId:Int
        if(userAnswer == correctAnswer) {
            quizViewModel.incrementScore()
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
        scoreStr = scoreStr.plus(quizViewModel.currentScore().toString()).plus("/").plus(quizViewModel.numAttempts())
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putInt("Score", quizViewModel.score)
        outState.putInt("CurrentIndex", quizViewModel.currentIndex)
        val doneIndices = ArrayList<Int>()
        doneIndices.addAll(quizViewModel.doneIndices)
        outState.putIntegerArrayList("DoneIndices", doneIndices)
    }
}


