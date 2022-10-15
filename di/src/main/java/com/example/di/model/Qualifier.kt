package com.example.di.model

import kotlin.reflect.KClass

data class Qualifier(
    val clazz: KClass<*>,
    val name: String = ""
)