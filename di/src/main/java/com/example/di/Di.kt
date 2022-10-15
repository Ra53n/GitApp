package com.example.di

import com.example.di.model.Dependency
import com.example.di.model.Qualifier

object Di {
    private val dependencyHolder = HashMap<Qualifier, Dependency<*>>()

    fun <T : Any> get(qualifier: Qualifier): T {
        val dep = dependencyHolder[qualifier]
        if (dep != null) {
            return dep.get() as T
        } else {
            throw IllegalArgumentException("No dependency in modules")
        }
    }

    fun <T : Any> add(qualifier: Qualifier, dependency: Dependency<T>) {
        dependencyHolder[qualifier] = dependency
    }

    inline fun <reified T : Any> add(dependency: Dependency<T>) {
        add(Qualifier(T::class), dependency)
    }

}

inline fun <reified T : Any> get(): T {
    return Di.get(Qualifier(T::class))
}

