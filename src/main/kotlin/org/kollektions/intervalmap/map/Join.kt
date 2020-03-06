package org.kollektions.intervalmap.map

import java.util.*

fun<T : Comparable<T>, V, W> innerJoin(first: IRangeMap<T, V>, second: IRangeMap<T, W>): IRangeMap<T, Pair<V, W>> {
    val ret = mutableListOf<RangeMapEntry<T, Pair<V, W>>>()
    for(firstRange in first.toList()) {
        val intersection = second[firstRange.range]
        val withCombinedValues =
                intersection.map { RangeMapEntry(it.range, Pair(firstRange.value, it.value)) }
        ret.addAll(withCombinedValues)
    }
    return RangeMap(ret)
}

fun<T : Comparable<T>, V, W> outerJoin(first: IRangeMap<T, V>, second: IRangeMap<T, W>):
        IRangeMap<T, Pair<V, Optional<W>>> {
    val ret = mutableListOf<RangeMapEntry<T, Pair<V, Optional<W>>>>()
    for(firstRange in first.toList()) {
        val intersection = second[firstRange.range]
        val withCombinedValues =
                intersection.map { RangeMapEntry(it.range, Pair(firstRange.value, Optional.of(it.value))) }
        ret.addAll(withCombinedValues)
    }
    val notInSecond = first.toMutableRangeMap()
    second.toList().forEach { notInSecond.remove(it.range) }
    notInSecond.toList().forEach { ret.add(RangeMapEntry(it.range, Pair(it.value, Optional.empty()))) }
    ret.sortBy { it.range.startEdge }
    return RangeMap(ret)
}

fun<T : Comparable<T>, V, W> fullOuterJoin(first: IRangeMap<T, V>, second: IRangeMap<T, W>):
        IRangeMap<T, Pair<Optional<V>, Optional<W>>> {
    val ret = mutableListOf<RangeMapEntry<T, Pair<Optional<V>, Optional<W>>>>()
    val leftOuterJoin = outerJoin(first, second)
    val rightOuterJoin = outerJoin(second, first)
    leftOuterJoin.toList().forEach { ret.add(RangeMapEntry(it.range, Pair(Optional.of(it.value.first), it.value.second))) }
    rightOuterJoin.toList().filter { !it.value.second.isPresent }
            .forEach { ret.add(RangeMapEntry(it.range, Pair(it.value.second, Optional.of(it.value.first)))) }
    ret.sortBy { it.range.startEdge }
    return RangeMap(ret)
}