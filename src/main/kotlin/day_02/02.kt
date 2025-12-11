package day_02

import java.io.File

fun isRepeated(n: Long): Boolean {
    val s = n.toString()
    if (s.length < 2 || s.length % 2 != 0) return false

    val (first, second) = s.substring(0..<s.length / 2) to s.substring(s.length / 2..<s.length)

    return first.toLong() == second.toLong()
}

fun hasRepetitions(n: Long): Boolean {
    val s = n.toString()
    if (s.length < 2) return false

    val stepLens = 1.rangeTo(s.length / 2).filter { s.length % it == 0 }

    return stepLens.any { stepLen ->
        val startRange = 0..s.length - stepLen step stepLen
        val endRange = stepLen..s.length step stepLen
        val ranges = startRange.zip(endRange)

        val parts = ranges.map { (start, end) -> s.substring(start, end) }

        parts.all { it.toLong() == parts.first().toLong() }
    }
}

fun a(productIdRanges: List<Pair<Long, Long>>) {
    val answer = productIdRanges.flatMap { (start, end) ->
        start.rangeTo(end).map { n ->
            if (isRepeated(n)) n else 0
        }
    }.sum()

    println("Answer a: $answer")
}

fun b(productIdRanges: List<Pair<Long, Long>>) {
    val answer = productIdRanges.flatMap { (start, end) ->
        start.rangeTo(end).map { n ->
            if (hasRepetitions(n)) n else 0
        }
    }.sum()

    println("Answer b: $answer")
}

fun main() {
    // val input = "02/example.in"
    val input = "02/2.in"

    val productIdRanges = File(input).readText()
        .split(",")
        .map { pair ->
            val (start, end) = pair.trim().split("-")
            start.toLong() to end.toLong()
        }

    a(productIdRanges) // 12850231731
    b(productIdRanges) // 24774350322
}