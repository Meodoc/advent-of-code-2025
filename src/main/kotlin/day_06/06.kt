package day_06

import java.io.File
import java.util.regex.Pattern

fun a(input: List<List<String>>) {
    val answer = (0..input[0].lastIndex).sumOf { problemIndex ->
        val values = input.take(input.size - 1).map { it[problemIndex].toLong() }
        val operation = input.last()[problemIndex]

        println("$values - $operation")

        when (operation) {
            "+" -> values.slice(1..values.lastIndex).fold(values.first()) { acc, value -> acc + value }
            "*" -> values.slice(1..values.lastIndex).fold(values.first()) { acc, value -> acc * value }
            else -> error("Illegal operation")
        }
    }

    println("Answer: $answer")
}

fun b(input: List<List<String>>) {
    val answer = input.sumOf { problem ->
        val operation = problem.last().last()
        val values = problem.dropLast(1).map { it.toLong() }

        when (operation) {
            '+' -> values.drop(1).fold(values.first()) { acc, value -> acc + value }
            '*' -> values.drop(1).fold(values.first()) { acc, value -> acc * value }

            else -> error("Illegal operation")
        }
    }

    println("Answer b: $answer")
}

fun Array<CharArray>.pivot(): Array<CharArray> {
    return (this[0].lastIndex downTo 0).map { col ->
        (0..this.lastIndex).map { row ->
            this[row][col]
        }.toCharArray()
    }.toTypedArray()
}

fun Array<CharArray>.prettyPrint() = joinToString("\n") { it.joinToString("") }

fun main() {
    // val input = "06/example.in"
    val input = "06/6.in"

    val linesA = File(input).readLines().map { line ->
        line.trim().split(Pattern.compile("\\s+"))
    }

    println(linesA)
    a(linesA) // 5060053676136

    val lines = File(input).readLines()

    val pivotedNumbers = lines
        .map { line -> line.toCharArray() }
        .dropLast(1)
        .toTypedArray()
        .pivot()

    val operations = lines.last().trim().split(Pattern.compile("\\s+")).map { it.trim() }.reversed()

    val linesB = pivotedNumbers
        .prettyPrint()
        .split(" ".repeat(pivotedNumbers.first().size))
        .map { l -> l.trim().split("\n").map { it.trim() } }
        .map { it.toMutableList() }

    val gangster = linesB.withIndex().map { indexedProblem ->
        indexedProblem.value.apply { add(operations[indexedProblem.index]) }
    }

    b(gangster) // 9695042567249
}