package com.quiz.CountryFlagQuiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.quiz.CountryFlagQuiz.R

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var correctAnswers: Int = 0
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        userName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        val optionFour: TextView = findViewById(R.id.tv_option_four)
        val optionThree: TextView = findViewById(R.id.tv_option_three)
        val optionTwo: TextView = findViewById(R.id.tv_option_two)
        val optionOne: TextView = findViewById(R.id.tv_option_one)
        val buttonSubmit: Button = findViewById(R.id.btn_submit)

        setQuestion()

        optionOne.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionThree.setOnClickListener(this)
        optionFour.setOnClickListener(this)

        buttonSubmit.setOnClickListener(this)
    }

    fun setQuestion() {
        val optionFour: TextView = findViewById(R.id.tv_option_four)
        val optionThree: TextView = findViewById(R.id.tv_option_three)
        val optionTwo: TextView = findViewById(R.id.tv_option_two)
        val optionOne: TextView = findViewById(R.id.tv_option_one)

        val image: ImageView = findViewById(R.id.tv_image)
        val questionText: TextView = findViewById(R.id.tv_question)
        val progress: TextView = findViewById(R.id.tv_progress)
        val progressBar: ProgressBar = findViewById(R.id.tv_progress_bar)
        val buttonSubmit: Button = findViewById(R.id.btn_submit)

        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionsView(optionOne, optionTwo, optionThree, optionFour)

        if (mCurrentPosition == mQuestionsList!!.size){
            buttonSubmit.text = "Finish"
        } else {
            buttonSubmit.text = "Submit"
        }

        progressBar.progress = mCurrentPosition

        progress.text = "$mCurrentPosition/${progressBar.max}"

        questionText.text = question.question

        image.setImageResource(question.image)

        optionOne.text = question.optionOne

        optionTwo.text = question.optionTwo

        optionThree.text = question.optionThree

        optionFour.text = question.optionFour
    }

    private fun defaultOptionsView(
        optionOne: TextView,
        optionTwo: TextView,
        optionThree: TextView,
        optionFour: TextView
    ) {
        val options = ArrayList<TextView>()
        options.add(0, optionOne)
        options.add(1, optionTwo)
        options.add(2, optionThree)
        options.add(3, optionFour)

        options.forEach{
            it.setTextColor(Color.parseColor("#7A8089"))
            it.typeface = Typeface.DEFAULT
            it.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        val optionFour: TextView = findViewById(R.id.tv_option_four)
        val optionThree: TextView = findViewById(R.id.tv_option_three)
        val optionTwo: TextView = findViewById(R.id.tv_option_two)
        val optionOne: TextView = findViewById(R.id.tv_option_one)
        val buttonSubmit: Button = findViewById(R.id.btn_submit)
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(optionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(optionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(optionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(optionFour, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        } else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_border_bg)
                    } else {
                        correctAnswers ++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size){
                        buttonSubmit.text = "FINISH"
                    } else {
                        buttonSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        val optionFour: TextView = findViewById(R.id.tv_option_four)
        val optionThree: TextView = findViewById(R.id.tv_option_three)
        val optionTwo: TextView = findViewById(R.id.tv_option_two)
        val optionOne: TextView = findViewById(R.id.tv_option_one)
        when(answer){
            1 ->{
                optionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                optionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 ->{
                optionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 ->{
                optionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(textView: TextView, selected: Int){
        val optionFour: TextView = findViewById(R.id.tv_option_four)
        val optionThree: TextView = findViewById(R.id.tv_option_three)
        val optionTwo: TextView = findViewById(R.id.tv_option_two)
        val optionOne: TextView = findViewById(R.id.tv_option_one)
        defaultOptionsView(optionOne, optionTwo, optionThree, optionFour)
        mSelectedOptionPosition = selected

        textView.setTextColor(Color.parseColor("#363A43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}