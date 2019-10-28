package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.Range

interface IMutableRangeMap<T : Comparable<T>, V> : IRangeMap<T, V> {
    operator fun set(range: Range<T>, value: V)

    operator fun set(point: T, value: V)

    fun remove(range: Range<T>)

    fun remove(point: T)
}