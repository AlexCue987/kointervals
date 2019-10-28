package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.Range
import com.tgt.trans.common.range.closedClosed
import java.util.*

class MutableRangeMap<T : Comparable<T>, V>(private var rangeMap: RangeMap<T, V>) : IMutableRangeMap<T, V> {

    override fun get(point: T) = rangeMap[point]

    override fun getOrDefault(point: T, default: V) = rangeMap.getOrDefault(point, default)

    constructor(pairs: List<RangeMapEntry<T, V>>) : this(RangeMap(pairs))

    override operator fun get(range: Range<T>): List<RangeMapEntry<T, V>> = rangeMap[range]

    override operator fun get(range: ClosedRange<T>): List<RangeMapEntry<T, V>> = rangeMap[range]

    override fun getWithRanges(range: Range<T>): List<RangeMapEntry<T, V>> = rangeMap.getWithRanges(range)

    override fun getWithRanges(range: ClosedRange<T>): List<RangeMapEntry<T, V>>
            = getWithRanges(range.start closedClosed range.endInclusive)

    override fun getWithRange(point: T): RangeMapEntry<T, V>? = rangeMap.getWithRange(point)

    override operator fun set(range: Range<T>, value: V) {
        changeWithRange(range) { getRangesAffectedByNewRange(rangeMap.toList(), range, value) }
    }

    operator fun set(range: ClosedRange<T>, value: V) = set(closedClosed(range), value)

    override fun remove(range: Range<T>) {
        changeWithRange(range) { rangesAffectedByRemove(rangeMap.toList(), range) }
    }

    fun remove(range: ClosedRange<T>) = remove(closedClosed(range))

    override fun remove(point: T) = remove(point closedClosed point)

    private fun changeWithRange(range: Range<T>, processRangesAroundNewRange: () -> List<RangeMapEntry<T, V>> ) {
        val pairs = rangeMap.toList()
        val newPairs = mutableListOf<RangeMapEntry<T, V>>()
        newPairs.addAll(pairs.filter { it.range.isBeforeNotAdjacent(range) })
        val rangesToAdd = processRangesAroundNewRange()
        newPairs.addAll(rangesToAdd)
        newPairs.addAll(pairs.filter { range.isBeforeNotAdjacent(it.range) })
        rangeMap = RangeMap(newPairs)
    }

    override operator fun set(point: T, value: V) = set(point closedClosed point, value)

    private fun getRangesAffectedByNewRange(pairs: List<RangeMapEntry<T, V>>,
                                            range: Range<T>, value: V): List<RangeMapEntry<T, V>> {
        val rangesAroundIntersection = rangesAffectedByRemove(pairs, range)
        rangesAroundIntersection.add(RangeMapEntry(range, value))
        val sortedRanges =
                rangesAroundIntersection.sortedWith(compareBy {it.range.startEdge()})
        return mergeAdjacentItemsWithSameValues(sortedRanges)
    }

    private fun rangesAffectedByRemove(pairs: List<RangeMapEntry<T, V>>, range: Range<T>): MutableList<RangeMapEntry<T, V>> {
        val rangesAroundIntersection = pairs
                .filter { !it.range.separatedByGap(range) }
                .map { (it.range - range).map { r -> RangeMapEntry(r, it.value) } }
                .flatten()
                .filter { it.range.isNotEmpty() }
                .toMutableList()
        return rangesAroundIntersection
    }

    override fun toList() = rangeMap.toList()

    override val size: Int
        get() = rangeMap.size

    override fun isEmpty() = rangeMap.isEmpty()

    override fun gaps() = rangeMap.gaps()

    fun toRangeMap() = rangeMap

    override fun toMutableRangeMap() = this
}

fun<T : Comparable<T>, V> mergeAdjacentItemsWithSameValues(pairs: List<RangeMapEntry<T, V>>): List<RangeMapEntry<T, V>> {
    require(pairs.isNotEmpty()) { "Pairs cannot be empty" }
    val newPairs = mutableListOf(pairs[0])
    for(index in 1 until pairs.size) {
        if (!newPairs.last().range.separatedByGap(pairs[index].range) && newPairs.last().value == pairs[index].value) {
            val mergedRange = newPairs.last().range.or(pairs[index].range)
            val sameValue = newPairs.last().value
            newPairs[newPairs.size - 1] = RangeMapEntry(mergedRange as Range<T>, sameValue)
        } else {
            newPairs.add(pairs[index])
        }
    }
    return newPairs
}

fun<T : Comparable<T>, V> mutableRangeMapOf(vararg pairs: Pair<Range<T>, V>): MutableRangeMap<T, V> {
    val ret = MutableRangeMap<T, V>(listOf())
    pairs.forEach { ret[it.first] = it.second }
    return ret
}

fun<T : Comparable<T>, V> mutableRangeMapFrom(vararg pairs: Pair<ClosedRange<T>, V>): MutableRangeMap<T, V> {
    var convertedPairs = pairs.map { Pair(closedClosed(it.first), it.second)}
    val mutableRangeMap = mutableRangeMapOf(*convertedPairs.toTypedArray())
    return mutableRangeMap
}

fun<T : Comparable<T>, V> rangeMapOf(vararg pairs: Pair<Range<T>, V>): RangeMap<T, V> {
    val mutableRangeMap = mutableRangeMapOf(*pairs)
    return mutableRangeMap.toRangeMap()
}

fun<T : Comparable<T>, V> rangeMapFrom(vararg pairs: Pair<ClosedRange<T>, V>): RangeMap<T, V> {
    val mutableRangeMap = mutableRangeMapFrom(*pairs)
    return mutableRangeMap.toRangeMap()
}

fun<T : Comparable<T>, V> List<Pair<Range<T>, V>>.toRangeMapEntryList() =
        this.map { RangeMapEntry(it.first, it.second) }
