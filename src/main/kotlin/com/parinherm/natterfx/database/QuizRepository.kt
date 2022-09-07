package com.parinherm.natterfx.database

import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp
import java.time.Instant

object QuizRepository {

    fun test(){
        val testQuiz = QuizEntity.new {
            name = "Basic Test"
        }

        val all = QuizEntity.all()
        all.forEach{
            println(it.name)
            println(it.id.toString())
        }
    }

    fun create(name_: String): QuizEntity {
        return transaction {
            return@transaction QuizEntity.new {
                name = name_
            }
        }
    }

    fun setName(name_: String, entity: QuizEntity) {
        transaction {
           entity.name = name_
        }
    }
}