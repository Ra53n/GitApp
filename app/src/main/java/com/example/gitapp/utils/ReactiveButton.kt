package com.example.gitapp.utils


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ReactiveButton(context: Context, attributeSet: AttributeSet) :
    AppCompatButton(context, attributeSet) {

    private val buttonObservable = BehaviorSubject.create<Any>()

    fun observeButton(): Observable<Any> {
        return buttonObservable
    }

    override fun performClick(): Boolean {
        buttonObservable.onNext(Any())
        return super.performClick()
    }
}