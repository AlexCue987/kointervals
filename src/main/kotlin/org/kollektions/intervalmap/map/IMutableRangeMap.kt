package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.Range

interface IMutableRangeMap<T : Comparable<T>, V> : IRangeMap<T, V> {
    operator fun set(range: Range<T>, value: V)

    operator fun set(point: T, value: V)

    fun remove(range: Range<T>)

    fun remove(point: T)
}