package com.parinherm.natterfx

import com.parinherm.natterfx.database.QuizEntity
import com.parinherm.natterfx.database.QuizRepository
import com.parinherm.natterfx.database.RecognitionRepository

object QuizCommands {

    var currentQuiz: QuizEntity? = null
    var nameFilled: Boolean = false

    fun processInput(input: RecognitionResult) {
       if(input.text.startsWith("create", true)) {
           currentQuiz = QuizRepository.create("untitled")
           nameFilled = false
       } else {
           //next one is the name
           val cleanedText = input.getCleanedText()
           if(!nameFilled && cleanedText.isNotEmpty() && currentQuiz != null){
               currentQuiz?.let { QuizRepository.setName(input.text, it) }
               currentQuiz?.let { RecognitionRepository.create(input, it) }
               nameFilled = true
           } else {
               currentQuiz?.let { RecognitionRepository.create(input, it) }
           }
       }
    }

}