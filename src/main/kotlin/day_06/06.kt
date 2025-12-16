package day_06

import java.io.File

data class Equation(val numbers: List<Long>, val operation: String) {
    fun solve() = when (operation) {
        "+" -> numbers.reduce { acc, next -> acc + next }
        "*" -> numbers.reduce { acc, next -> acc * next }
        else -> error("Unknown operation $operation")
    }
}

fun String.splitByBlank(): List<String> = split("\\s+".toRegex())

fun List<String>.splitByEmpty(): List<List<String>> =
    this.fold(mutableListOf(mutableListOf<String>())) { acc, element ->
        if (element.isBlank()) {
            acc.apply { this += mutableListOf<String>() }
        } else {
            acc.apply { last() += element }
        }
    }

fun List<List<String>>.pivot(): List<List<String>> =
    this.first().indices.fold(mutableListOf()) { acc, colIndex ->
        val col = this.map { it[colIndex] }
        acc.apply { this += col }
    }

fun List<String>.pivot(): List<String> =
    this.first().indices.fold(mutableListOf()) { acc, colIndex ->
        val col = this.map { it[colIndex] }.joinToString("")
        acc.apply { this += col }
    }

fun a(numberLines: List<String>, operators: List<String>) {
    val problemNumbers = numberLines.map { line -> line.trim().splitByBlank() }.pivot()
        .map { problem -> problem.map { it.trim().toLong() } }

    val equations = problemNumbers.mapIndexed { index, number ->
        Equation(number, operators[index])
    }

    val answer = equations.sumOf { it.solve() }

    println("Answer a: $answer")
}

fun b(numberLines: List<String>, operators: List<String>) {
    val problemNumbers = numberLines.pivot().splitByEmpty().reversed()
        .map { problem -> problem.map { it.trim().toLong() } }

    val equations = problemNumbers.mapIndexed { index, numbers ->
        Equation(numbers, operators[index])
    }

    val answer = equations.sumOf { it.solve() }

    println("Answer b: $answer")
}


fun main() {
    // val input = "06/example.in"
    val input = "06/6.in"
    val lines = File(input).readLines()

    val numberLines = lines.dropLast(1)
    val operators = lines.last().trim().splitByBlank()

    a(numberLines, operators)             // 5060053676136
    b(numberLines, operators.reversed())  // 9695042567249
}
