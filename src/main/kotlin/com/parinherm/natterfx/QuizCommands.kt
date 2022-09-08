package com.parinherm.natterfx

import com.parinherm.natterfx.database.QuizEntity
import com.parinherm.natterfx.database.QuizRepository
import com.parinherm.natterfx.database.RecognitionRepository

object QuizCommands {

    var currentQuiz: QuizEntity? = null
    var nameFilled: Boolean = false

    fun processInput(input: RecognitionResult, audioData: ByteArray, audioLength: Int) {
        val cleanedText = input.getCleanedText()
        if (cleanedText.isEmpty()) {
            return
        }
        if (cleanedText.startsWith("create", true)) {
            currentQuiz = QuizRepository.create("untitled")
            nameFilled = false
        } else {
            if (currentQuiz != null) {
                if (!nameFilled) {
                    QuizRepository.setName(input.text, currentQuiz!!)
                    RecognitionRepository.create(input.text, audioData, audioLength, currentQuiz!!)
                    nameFilled = true
                } else {
                    RecognitionRepository.create(input.text, audioData, audioLength, currentQuiz!!)
                }
            }
        }
    }

}