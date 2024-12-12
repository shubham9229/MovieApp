package com.example.testapp.util

class Consumer<out T>(private val content: T) {

    var hasBeenConsumed = false

    fun getContentIfNotConsumed(): T? {
        return if (hasBeenConsumed) {
            null
        } else {
            hasBeenConsumed = true
            content
        }
    }

}