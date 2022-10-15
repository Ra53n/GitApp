package com.example.di

import com.example.di.model.Fabric
import com.example.di.model.Singleton
import kotlin.reflect.KClass

class Module(private val block: () -> Unit) {
    fun install() {
        block.invoke()
    }
}

inline fun <reified T : Any> KClass<T>.bindToFabric(noinline creator: () -> T) {
    Di.add(Fabric<T>(creator))
}

inline fun <reified T : Any> KClass<T>.bindToSingleton(noinline creator: () -> T) {
    Di.add(Singleton<T>(creator))
}

inline fun <reified T : Any> KClass<T>.bindToInstance() {
    Di.add(Singleton<T> { T::class.java.newInstance() })
}