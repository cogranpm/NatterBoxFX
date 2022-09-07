package com.parinherm.natterfx.database

import com.parinherm.natterfx.RecognitionResult
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction

object RecognitionRepository {

    fun create(recognitionResult: RecognitionResult, quizEntity_: QuizEntity) {
        transaction {
            val recognitionEntity = RecognitionEntity.new {
                text = recognitionResult.text
                audio = ExposedBlob(recognitionResult.audioData)
                length = recognitionResult.audioLength
                quiz = quizEntity_
            }
        }
    }

    fun getMostRecent(): RecognitionEntity? {
        return transaction {
            val results = RecognitionEntity.all().limit(1).sortedByDescending { it.ts }
            println(results.size)
            return@transaction results.firstOrNull()
        }
    }
}