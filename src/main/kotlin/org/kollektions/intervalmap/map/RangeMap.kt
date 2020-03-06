package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.*
import kotlin.streams.toList

class RangeMap<T : Comparable<T>, V>(private val pairs: List<RangeMapEntry<T, V>>) : IRangeMap<T, V> {

    override fun get(point: T): V? {
        val match = get(point closedClosed point)
        return if(match.isNotEmpty()) match[0].value else null
    }

    override fun getOrDefault(point: T, default: V): V {
        val match = get(point closedClosed point)
        return if(match.isNotEmpty()) match[0].value else default
    }

    override val size: Int
        get() = pairs.size

    init {
        validate(pairs)
    }

    private fun validate(pairs: List<RangeMapEntry<T, V>>) {
        for (i: Int in 1 until pairs.size) {
            require(pairs[i - 1].range.isBefore(pairs[i].range)) {
                 "${pairs[i - 1].range} does not precede ${pairs[i].range}"
            }
        }
    }

    override operator fun get(range: Range<T>): List<RangeMapEntry<T, V>> =
            pairs.stream().filter { range.intersect(it.range) }
                    .map { RangeMapEntry((range.and(it.range)) as Range<T>, it.value)}
                    .toList()

    override operator fun get(range: ClosedRange<T>): List<RangeMapEntry<T, V>> =
            get(Range(closedStart(range.start), closedEnd(range.endInclusive)))

    override fun getWithRanges(range: Range<T>): List<RangeMapEntry<T, V>> =
            pairs.stream().filter { range.intersect(it.range) }
                    .toList()

    override fun getWithRanges(range: ClosedRange<T>): List<RangeMapEntry<T, V>> =
            getWithRanges(range.start closedClosed range.endInclusive)

    override fun getWithRange(point: T): RangeMapEntry<T, V>? {
        val match = getWithRanges(point closedClosed point)
        return if(match.isNotEmpty()) match[0] else null
    }

    override fun toList() = pairs.toList()

    override fun isEmpty() = pairs.isEmpty()

    override fun gaps(): List<Range<T>> {
        return (1 until pairs.size)
                .filter { pairs[it-1].range.separatedByGap(pairs[it].range) }
                .map { Range(pairs[it-1].range.endEdge().inverse() as RangeEdge<T>, pairs[it].range.startEdge().inverse() as RangeEdge<T>) }
    }

    override fun toMutableRangeMap() = MutableRangeMap(this)
}

data class RangeMapEntry<T : Comparable<T>, V>(val range: Range<T>, val value: V) {

    override fun toString() = "$range to ($value)"

}