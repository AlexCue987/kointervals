package org.kollektions.intervalmap

data class Range<T: Comparable<T>>(val startEdge: IRangeEdge<T>,
                                   val endEdge: IRangeEdge<T>) : IRange<T>
{
    override fun contains(value: T): Boolean = startEdge <= value && endEdge >= value

    override fun isBefore(other: IRange<T>) = when(other) {
        is Range -> endEdge < other.startEdge()
        else -> false
    }

    override fun isNotEmpty() = true

    override fun and(other: IRange<T>) = when(other) {
        is Range -> when { this.intersect(other) -> Range(maxOf(startEdge, other.startEdge()), minOf(endEdge, other.endEdge()))
            else -> emptyRange()
        }
        else -> other.and(this)
    }

    fun isBeforeNotAdjacent(other: IRange<T>) = when(other) {
        is Range -> this.isBefore(other) && ( !this.endEdge.adjacentTo(other.startEdge()))
        else -> this.isBefore(other)
    }

    override fun intersect(other: IRange<T>) = !(this.isBefore(other) || other.isBefore(this))

    fun startEdge(): IRangeEdge<T> = startEdge

    fun endEdge(): IRangeEdge<T> = endEdge

    operator fun minus(other: IRange<T>): List<Range<T>> {
        return when {
            other is Range && this.intersect(other) -> {
                val ret = mutableListOf<Range<T>>()
                if (this.startEdge() < other.startEdge()) {
                    ret.add(Range(this.startEdge(), other.startEdge().inverse()))
                }
                if (this.endEdge() > other.endEdge()) {
                    ret.add(Range(other.endEdge().inverse(), this.endEdge()))
                }
                ret
            }
            else -> listOf(this)
        }
    }

    fun or(other: Range<T>): Range<T> {
        require(!this.separatedByGap(other)) {
            "Ranges $this and $other must be adjacent"
        }
        return Range(minOf(startEdge, other.startEdge()), maxOf(endEdge, other.endEdge()))
    }

    fun separatedByGap(other: Range<T>): Boolean = isBeforeNotAdjacent(other) || other.isBeforeNotAdjacent(this)

    override fun toString() = "$startEdge,$endEdge"

    init {
        require(startEdge <= endEdge) {
            "start must be before end: $this"
        }
    }
}

fun<T: Comparable<T>> closedClosed(range: ClosedRange<T>) = Range(closedStart(range.start), closedEnd(range.endInclusive))

infix fun <T: Comparable<T>> T.closedClosed(that: T) = closedClosed(this..that)

fun<T: Comparable<T>> openOpen(range: ClosedRange<T>) = Range(openStart(range.start), openEnd(range.endInclusive))

infix fun <T: Comparable<T>> T.openOpen(that: T) = openOpen(this..that)

fun<T: Comparable<T>> openClosed(range: ClosedRange<T>) = Range(openStart(range.start), closedEnd(range.endInclusive))

infix fun <T: Comparable<T>> T.openClosed(that: T) = openClosed(this..that)

fun<T: Comparable<T>> closedOpen(range: ClosedRange<T>) = Range(closedStart(range.start), openEnd(range.endInclusive))

infix fun <T: Comparable<T>> T.closedOpen(that: T) = closedOpen(this..that)

fun<T: Comparable<T>> greaterThan(value: T) = Range(openStart(value), PlusInfinity())

fun<T: Comparable<T>> noLessThan(value: T) = Range(closedStart(value), PlusInfinity())

fun<T: Comparable<T>> lessThan(value: T) = Range(MinusInfinity(), openEnd(value))

fun<T: Comparable<T>> noGreaterThan(value: T) = Range(MinusInfinity(), closedEnd(value))

fun<T: Comparable<T>> allValues() = Range(MinusInfinity<T>(), PlusInfinity<T>())
