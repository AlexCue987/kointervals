package org.kollektions.intervalmap

interface IRange<T : Comparable<T>> {
    operator fun contains(value: T): Boolean
    fun isBefore(other: IRange<T>): Boolean
    fun isNotEmpty(): Boolean
    fun and(other: IRange<T>): IRange<T>
    fun intersect(other: IRange<T>): Boolean
}
