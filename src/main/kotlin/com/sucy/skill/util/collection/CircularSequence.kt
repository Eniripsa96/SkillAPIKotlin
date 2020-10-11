package com.sucy.skill.util.collection

class CircularSequence<T>(private val sequence: Sequence<T>) : Iterator<T> {
    var iterator = sequence.iterator()
    override fun hasNext() = true

    override fun next(): T {
        if (!iterator.hasNext()) iterator = sequence.iterator()
        return iterator.next()
    }

    fun getValues(count: Int) = List(count) { next() }
}