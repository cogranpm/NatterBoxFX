package com.parinherm.natterfx.database

import com.parinherm.natterfx.RecognitionResult
import org.jetbrains.exposed.sql.transactions.transaction

object RecognitionRepository {

    fun create(recognitionResult: RecognitionResult, quizEntity_: QuizEntity) {
        transaction {
            val recognitionEntity = RecognitionEntity.new {
                text = recognitionResult.text
                audio = recognitionResult.audioData
                length = recognitionResult.audioLength
                quiz = quizEntity_
            }
        }
    }
}