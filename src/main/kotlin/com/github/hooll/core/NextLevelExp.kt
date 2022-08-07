package com.github.hooll.core

import taboolib.common5.Coerce

object NextLevelExp {
    var nextLevelExp = mutableMapOf<String,Double>()

    fun getExpToNextLevel(level:Int):Double{
        val lvl = level+1
        nextLevelExp.keys.forEach {
            if(it.split("-").size == 2 ){
                val min = Coerce.toInteger(it.split("-")[0])
                val max = Coerce.toInteger(it.split("-")[1])
                if(lvl in min..max){
                    return nextLevelExp[it]!!
                }
            }else{
                if(lvl == Coerce.toInteger(it)){
                    return nextLevelExp[it]!!
                }
            }
        }
        return com.github.hooll.config.Config.default_NextLevelExp
    }
}