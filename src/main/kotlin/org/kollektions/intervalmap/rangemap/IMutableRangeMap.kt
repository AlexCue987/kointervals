package org.kollektions.intervalmap.rangemap

import org.kollektions.intervalmap.range.Range

interface IMutableRangeMap<T : Comparable<T>, V> : IRangeMap<T, V> {
    operator fun set(range: Range<T>, value: V)

    operator fun set(point: T, value: V)

    fun remove(range: Range<T>)

    fun remove(point: T)
}