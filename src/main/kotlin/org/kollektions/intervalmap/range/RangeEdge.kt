package org.kollektions.intervalmap.range

interface IRangeEdge<T : Comparable<T>> : Comparable<IRangeEdge<T>> {
    val side: EdgeSide
    fun inverse(): IRangeEdge<T>
    fun adjacentTo(other: IRangeEdge<T>): Boolean
    operator fun compareTo(other: T): Int
}

enum class EdgeSide { END, START;

    fun other() = if (this == START) END else START
}

data class RangeEdge<T: Comparable<T>>(val value: T, val inclusive: Boolean, override val side: EdgeSide) : IRangeEdge<T> {

    override fun compareTo(other: IRangeEdge<T>): Int = when (other) {
        is RangeEdge -> when {
            value < other.value -> -1
            value > other.value -> 1
            inclusive && other.inclusive -> 0
            side == other.side -> inclusive.compareTo(other.inclusive) *
                    (if (side == EdgeSide.END) 1 else -1)
            else -> side.compareTo(other.side)
        }
        else -> -(other.compareTo(this))
    }

    override fun compareTo(other: T) = when {
        value != other -> value.compareTo(other)
        else -> when {
            inclusive -> 0
            else -> if (side == EdgeSide.END) -1 else 1
        }
    }

    override fun inverse() = RangeEdge(value, !inclusive, side.other())

    override fun adjacentTo(other: IRangeEdge<T>): Boolean {
        require(side != other.side) {
            "Must compare different sides"
        }
        return when (other) {
            is RangeEdge -> value == other.value && (inclusive || other.inclusive)
            else -> false
        }
    }

    override fun toString(): String {
        return when (side) {
            EdgeSide.START -> "${if (inclusive) '[' else '('}$value"
            else -> "$value${if (inclusive) ']' else ')'}"
        }
    }
}

fun<T: Comparable<T>> openStart(value: T) = RangeEdge(value, false, EdgeSide.START)

fun<T: Comparable<T>> openEnd(value: T) = RangeEdge(value, false, EdgeSide.END)

fun<T: Comparable<T>> closedStart(value: T) = RangeEdge(value, true, EdgeSide.START)

fun<T: Comparable<T>> closedEnd(value: T) = RangeEdge(value, true, EdgeSide.END)

class PlusInfinity<T: Comparable<T>> : IRangeEdge<T> {

    override fun inverse(): IRangeEdge<T> = throw IllegalStateException("Cannot inverse infinite range edge")

    override fun compareTo(other: IRangeEdge<T>): Int = if(other is PlusInfinity) 0 else 1

    override fun compareTo(other: T) = 1

    override val side: EdgeSide = EdgeSide.END

    override fun adjacentTo(other: IRangeEdge<T>) = other is PlusInfinity<T>

    override fun equals(other: Any?) = other is PlusInfinity<*>

    override fun toString() = "+Infinity)"
}

class MinusInfinity<T: Comparable<T>> : IRangeEdge<T> {

    override fun inverse(): IRangeEdge<T> = throw IllegalStateException("Cannot inverse infinite range edge")

    override fun compareTo(other: IRangeEdge<T>): Int = if (other is MinusInfinity) 0 else -1

    override fun compareTo(other: T) = -1

    override val side: EdgeSide = EdgeSide.END

    override fun adjacentTo(other: IRangeEdge<T>) = other is MinusInfinity<T>

    override fun equals(other: Any?) = other is MinusInfinity<*>

    override fun toString() = "(-Infinity"
}