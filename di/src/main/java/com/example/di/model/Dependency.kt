package com.example.di.model

abstract class Dependency<T : Any>(protected val creation: () -> Any) {
    abstract fun get(): Any
}

class Singleton<T : Any>(creation: () -> Any) : Dependency<T>(creation) {
    private val dependency: Any by lazy { creation.invoke() }

    override fun get(): Any {
        return dependency
    }

}

class Fabric<T : Any>(creation: () -> Any) : Dependency<T>(creation) {
    override fun get(): Any {
        return creation.invoke()
    }
}
