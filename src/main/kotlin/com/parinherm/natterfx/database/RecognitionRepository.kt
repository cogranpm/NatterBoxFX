package com.parinherm.natterfx.database

import com.parinherm.natterfx.RecognitionResult
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction

object RecognitionRepository {

    fun create(text_: String, tag_: String, audioData_: ByteArray, audioLength_: Int, quizEntity_: QuizEntity) {
        transaction {
            val recognitionEntity = RecognitionEntity.new {
                text = text_
                tag = tag_
                audio = ExposedBlob(audioData_)
                length = audioLength_
                quiz = quizEntity_
            }
        }
    }

    fun getMostRecent(): RecognitionEntity? {
        return transaction {
            val results = RecognitionEntity.all().limit(1).sortedByDescending { it.ts }
            return@transaction results.firstOrNull()
        }
    }

    fun getAll(): List<RecognitionEntity> {
        return transaction {
            return@transaction RecognitionEntity.all().toList()
        }
    }

    fun getByQuiz(quizEntity: QuizEntity) : List<RecognitionEntity> {
        return transaction {
            return@transaction RecognitionEntity.all().toList()
        }
    }
}