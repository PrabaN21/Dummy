package com.example.model1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class QuizActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var option1RadioButton: RadioButton
    private lateinit var option2RadioButton: RadioButton
    private lateinit var option3RadioButton: RadioButton
    private lateinit var option4RadioButton: RadioButton
    private lateinit var submitButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var answersTextView: TextView // Added TextView for answers

    private var currentQuestionIndex = 0
    private var score = 0
    private val questions = listOf(
        Question(
            "What is the capital of France?",
            listOf("Berlin", "Madrid", "Paris", "Rome"),
            2
        ),
        Question(
            "Which planet is known as the 'Red Planet'?",
            listOf("Earth", "Mars", "Jupiter", "Venus"),
            1
        ),
        Question(
            "What is the largest mammal?",
            listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
            1
        ),
        Question(
            "What is the chemical symbol for water?",
            listOf("O2", "CO2", "H2O", "NaCl"),
            2
        ),
        Question(
            "Who painted the Mona Lisa?",
            listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"),
            2
        )
    )
    private val userAnswers = mutableListOf<Int?>() // Store user's answers (index of selected option)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        questionTextView = findViewById(R.id.questionTextView)
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup)
        option1RadioButton = findViewById(R.id.option1RadioButton)
        option2RadioButton = findViewById(R.id.option2RadioButton)
        option3RadioButton = findViewById(R.id.option3RadioButton)
        option4RadioButton = findViewById(R.id.option4RadioButton)
        submitButton = findViewById(R.id.submitButton)
        feedbackTextView = findViewById(R.id.feedbackTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        restartButton = findViewById(R.id.restartButton)
        answersTextView = findViewById(R.id.answersTextView) // Initialize answers TextView
        restartButton.visibility = View.GONE
        answersTextView.visibility = View.GONE // Initially hide answers

        // Initialize userAnswers list with null values (no answer yet)
        repeat(questions.size) { userAnswers.add(null) }

        displayQuestion()

        submitButton.setOnClickListener {
            checkAnswer()
        }

        restartButton.setOnClickListener {
            restartQuiz()
        }
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            questionTextView.text = currentQuestion.text
            option1RadioButton.text = currentQuestion.options[0]
            option2RadioButton.text = currentQuestion.options[1]
            option3RadioButton.text = currentQuestion.options[2]
            option4RadioButton.text = currentQuestion.options[3]
            optionsRadioGroup.clearCheck()
            feedbackTextView.visibility = TextView.GONE

            // Restore previously selected answer, if any
            val userAnswer = userAnswers[currentQuestionIndex]
            when (userAnswer) {
                0 -> option1RadioButton.isChecked = true
                1 -> option2RadioButton.isChecked = true
                2 -> option3RadioButton.isChecked = true
                3 -> option4RadioButton.isChecked = true
            }
        } else {
            // Quiz finished
            questionTextView.text = "Quiz Completed!"
            optionsRadioGroup.visibility = RadioGroup.GONE
            submitButton.visibility = Button.GONE
            feedbackTextView.visibility = TextView.VISIBLE
            feedbackTextView.text = "Your final score is $score out of ${questions.size}."
            restartButton.visibility = View.VISIBLE
            displayAnswers() // Show answers
        }
    }

    private fun checkAnswer() {
        val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
        val selectedAnswerIndex = when (findViewById<RadioButton>(selectedOptionId)) {
            option1RadioButton -> 0
            option2RadioButton -> 1
            option3RadioButton -> 2
            option4RadioButton -> 3
            else -> -1
        }

        // Store the user's answer
        userAnswers[currentQuestionIndex] = if (selectedAnswerIndex != -1) selectedAnswerIndex else null

        if (selectedAnswerIndex != -1) {
            val currentQuestion = questions[currentQuestionIndex]
            if (selectedAnswerIndex == currentQuestion.correctAnswerIndex) {
                score++
                feedbackTextView.text = "Correct!"
            } else {
                feedbackTextView.text =
                    "Incorrect. The correct answer is: ${currentQuestion.options[currentQuestion.correctAnswerIndex]}"
                score--
            }
            scoreTextView.text = "Score: $score"
            feedbackTextView.visibility = TextView.VISIBLE
            currentQuestionIndex++
            displayQuestion()
        } else {
            feedbackTextView.text = "Please select an answer."
            feedbackTextView.visibility = TextView.VISIBLE
        }
    }

    private fun displayAnswers() {
        val answersText = StringBuilder()
        for (i in questions.indices) {
            val question = questions[i]
            val userAnswerIndex = userAnswers[i]
            val userAnswer = if (userAnswerIndex != null) question.options[userAnswerIndex] else "Not answered"
            val correctAnswer = question.options[question.correctAnswerIndex]
            answersText.append("Q${i + 1}: ${question.text}\n")
            answersText.append("  Your answer: $userAnswer\n")
            answersText.append("  Correct answer: $correctAnswer\n\n")
        }
        answersTextView.text = answersText.toString()
        answersTextView.visibility = View.VISIBLE
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        scoreTextView.text = "Score: 0"
        optionsRadioGroup.visibility = RadioGroup.VISIBLE
        submitButton.visibility = Button.VISIBLE
        restartButton.visibility = View.GONE
        feedbackTextView.visibility = View.GONE
        answersTextView.visibility = View.GONE // Hide answers on restart
        // Reset user answers
        for (i in userAnswers.indices) {
            userAnswers[i] = null
        }
        displayQuestion()
    }
}

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)