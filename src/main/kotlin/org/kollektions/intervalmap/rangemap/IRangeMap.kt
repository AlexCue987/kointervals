package org.kollektions.intervalmap.rangemap

import org.kollektions.intervalmap.range.Range

interface IRangeMap<T : Comparable<T>, V> {
    operator fun get(point: T): V?

    fun getOrDefault(point: T, default: V): V

    operator fun get(range: Range<T>): List<RangeMapEntry<T, V>>

    operator fun get(range: ClosedRange<T>): List<RangeMapEntry<T, V>>

    fun getWithRanges(range: Range<T>): List<RangeMapEntry<T, V>>

    fun getWithRanges(range: ClosedRange<T>): List<RangeMapEntry<T, V>>

    fun getWithRange(point: T): RangeMapEntry<T, V>?

    fun toList(): List<RangeMapEntry<T, V>>

    val size: Int

    fun isEmpty(): Boolean

    fun gaps(): List<Range<T>>

    fun toMutableRangeMap(): IMutableRangeMap<T, V>
}