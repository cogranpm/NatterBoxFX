package com.parinherm.natterfx.database

object QuizRepository {

    fun test(){
        val testQuiz = QuizEntity.new {
            name = "Basic Test"
        }

        val all = QuizEntity.all()
        all.forEach{
            println(it.name)
        }
    }
}