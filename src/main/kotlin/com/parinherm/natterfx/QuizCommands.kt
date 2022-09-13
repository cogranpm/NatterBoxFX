package com.parinherm.natterfx

import com.parinherm.natterfx.Constants.STOP_PHRASE
import com.parinherm.natterfx.Constants.TAG_ANSWER
import com.parinherm.natterfx.Constants.TAG_NAME
import com.parinherm.natterfx.Constants.TAG_QUESTION
import com.parinherm.natterfx.database.QuizEntity
import com.parinherm.natterfx.database.QuizRepository
import com.parinherm.natterfx.database.RecognitionRepository

object QuizCommands {

    var currentQuiz: QuizEntity? = null
    var nameFilled: Boolean = false
    var questionFilled: Boolean = false
    var answerFilled: Boolean = false
    val entityList = arrayListOf<QuizEntity>()

    fun processInput(cleanedText: String, audioData: ByteArray, audioLength: Int) {
        if (cleanedText.isEmpty()) {
            return
        }
        if (cleanedText.startsWith("create", true)) {
            currentQuiz = QuizRepository.create("untitled")
            nameFilled = false
            questionFilled = false

        } else {
            if (currentQuiz != null) {
                if(cleanedText.startsWith(STOP_PHRASE)){
                    println("we stopped")
                    entityList.add(currentQuiz!!)
                    currentQuiz = null
                    return
                }
                var tag = TAG_NAME
                if (!nameFilled) {
                    QuizRepository.setName(cleanedText, currentQuiz!!)
                    nameFilled = true
                } else if (!questionFilled) {
                    tag = TAG_QUESTION
                    questionFilled = true
                    answerFilled = false
                } else {
                    tag = TAG_ANSWER
                    answerFilled = true
                    questionFilled = false
                }
                RecognitionRepository.create(cleanedText, tag, audioData, audioLength, currentQuiz!!)
            }
        }
    }

}