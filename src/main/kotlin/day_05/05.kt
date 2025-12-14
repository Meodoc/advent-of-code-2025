package day_05

import java.io.File
import kotlin.math.max
import kotlin.math.min

val LongRange.size: Long
    get() = endInclusive - start + 1

fun LongRange.subtract(other: LongRange): List<LongRange> {
    if (first > other.last || last < other.first) return listOf(this)
    val overlap = max(first, other.first).rangeTo(min(last, other.last))
    return listOf(first.rangeUntil(overlap.first), (overlap.last + 1).rangeTo(last))
        .filter { it != LongRange.EMPTY }
}

fun load(input: String): Pair<List<LongRange>, List<Long>> {
    val (first, second) = File(input).readText().trim().split("\n\n")
    val fresh = first.split("\n")
        .map { it.split("-") }
        .map { it.first().toLong().rangeTo(it.last().toLong()) }
    val available = second.split("\n").map { it.toLong() }

    return fresh to available
}

fun a(fresh: List<LongRange>, available: List<Long>) {
    val answer = available.count { id ->
        fresh.any { id in it }
    }

    println("Answer a: $answer")
}

fun b(fresh: List<LongRange>) {
    val answer = fresh.withIndex().flatMap { range ->
        val seen = fresh.slice(0..<range.index)
        seen.fold(listOf(range.value)) { res, seen ->
            res.flatMap { it.subtract(seen) }
        }
    }.sumOf { it.size }

    println("Answer b: $answer")
}

fun main() {
    // val input = "05/example.in"
    val input = "05/5.in"

    val (fresh, available) = load(input)

    a(fresh, available)  // 529
    b(fresh)             // 344260049617193
}