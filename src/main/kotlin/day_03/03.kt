package day_03

import java.io.File
import kotlin.math.pow

fun findBiggestJoltageWithIndex(battery: List<Int>): Pair<Int, Int> {
    val biggest = battery.withIndex().maxBy { it.value }
    return biggest.value to biggest.index
}

fun a(banks: List<List<Int>>) {
    val answer = banks.sumOf { bank ->
        val (firstValue, firstIndex) = findBiggestJoltageWithIndex(
            bank.slice(0..<bank.lastIndex)
        )
        val (secondValue, _) = findBiggestJoltageWithIndex(
            bank.slice(firstIndex + 1..bank.lastIndex)
        )

        firstValue * 10 + secondValue
    }

    println("Answer a: $answer")
}

fun b(banks: List<List<Int>>) {
    val answer = banks.sumOf { bank ->
        var result = 0L
        var leftOffset = 0

        for (rightOffset in 11.downTo(0)) {
            val (value, index) = findBiggestJoltageWithIndex(
                bank.slice(leftOffset..bank.lastIndex - rightOffset)
            )
            leftOffset += index + 1

            result += value * 10.0.pow(rightOffset).toLong()
        }

        result
    }

    println("Answer b: $answer")
}

fun bFunctional(banks: List<List<Int>>) {
    val answer = banks.sumOf { bank ->
        11.downTo(0).fold(Pair(0L, 0)) { (result, leftOffset), rightOffset ->
            val (value, index) = findBiggestJoltageWithIndex(
                bank.slice(leftOffset..bank.lastIndex - rightOffset)
            )

            Pair(
                result + value * 10.0.pow(rightOffset).toLong(),
                leftOffset + index + 1
            )
        }.first
    }

    println("Answer b: $answer")
}

fun main() {
    // val input = "03/example.in"
    val input = "03/3.in"

    val banks = File(input).readLines().map { line ->
        line.toList().map { it.digitToInt() }
    }

    a(banks) // 16993
    b(banks) // 168617068915447
    bFunctional(banks)
}
