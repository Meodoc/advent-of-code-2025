package day_06

import java.io.File

data class Equation(val numbers: List<Long>, val operation: String) {
    fun solve(): Long {
        return when (operation) {
            "+" -> numbers.reduce { acc, next -> acc + next }
            "*" -> numbers.reduce { acc, next -> acc * next }
            else -> error("Unknown operation $operation")
        }
    }
}

fun a(numberLines: List<String>, operators: List<String>) {
    val numbersGrid = numberLines.map { line ->
        line.trim().split("\\s+".toRegex()).map { it.toLong() }
    }

    val answer = operators.indices.map { colIndex ->
        val numbers = numbersGrid.map { row -> row[colIndex] }
        Equation(numbers, operators[colIndex])
    }.sumOf { it.solve() }

    println("Answer: $answer")
}

fun b(numberLines: List<String>, operators: List<String>) {
    val charGrid = numberLines.map { it.toCharArray() }

    val curNumbers = mutableListOf<Long>()
    val equations = mutableListOf<Equation>()

    for (colIndex in charGrid[0].lastIndex downTo 0) {
        val col = charGrid.map { it[colIndex] }.joinToString("")
        if (col.isNotBlank()) {
            curNumbers += col.trim().toLong()
        } else {
            equations.add(Equation(curNumbers.toList(), operators[equations.lastIndex + 1]))
            curNumbers.clear()
        }
    }
    equations.add(Equation(curNumbers.toList(), operators[equations.lastIndex + 1]))

    val answer = equations.sumOf { it.solve() }

    println("Answer b: $answer")
}


fun main() {
    // val input = "06/example.in"
    val input = "06/6.in"
    val lines = File(input).readLines()

    val numbers = lines.dropLast(1)
    val operators = lines.last().trim().split("\\s+".toRegex())

    a(numbers, operators)             // 5060053676136
    b(numbers, operators.reversed())  // 9695042567249
}
