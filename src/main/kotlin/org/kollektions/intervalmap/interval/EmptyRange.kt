package org.kollektions.intervalmap

internal object EmptyRange : IRange<Nothing> {
    override fun and(other: IRange<Nothing>) = this

    override fun contains(value: Nothing) = false

    override fun isBefore(other: IRange<Nothing>) = false

    override fun isNotEmpty(): Boolean = false

    override fun intersect(other: IRange<Nothing>) = false
}

public fun<T: Comparable<T>> emptyRange(): IRange<T> = EmptyRange as IRange<T>