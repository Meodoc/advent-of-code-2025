package day_01

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.floor

const val wrap = 100

fun turnDial(pos: Int, dir: Char, steps: Int): Int = when (dir) {
    'L' -> (pos - steps)
    'R' -> (pos + steps)
    else -> throw IllegalArgumentException("Invalid direction: $dir")
}

fun a(instructions: List<Pair<Char, Int>>) {
    var pos = 50
    var numberOfZeros = 0

    instructions.forEach { (direction, steps) ->
        pos = turnDial(pos, direction, steps) % wrap

        if (pos == 0) {
            numberOfZeros++
        }
    }

    println(numberOfZeros)
}

fun b(instructions: List<Pair<Char, Int>>) {
    var pos = 50
    var numberOfZeros = 0

    instructions.forEach { (direction, steps) ->
        val newAbsolutePos = turnDial(pos, direction, steps)
        val newPos = Math.floorMod(newAbsolutePos, wrap)

        if (newPos == 0) {
            numberOfZeros++
        }

        val numberOfWraps = when {
            newAbsolutePos < 0 && pos == 0 -> abs(floor(newAbsolutePos / wrap.toDouble()).toInt()) - 1
            newAbsolutePos > 0 && newPos == 0 -> abs(floor(newAbsolutePos / wrap.toDouble()).toInt()) - 1
            else -> abs(floor(newAbsolutePos / wrap.toDouble()).toInt())
        }

        pos = newPos
        numberOfZeros += numberOfWraps
    }

    println(numberOfZeros)
}


fun main() {
    // val input = "01/example.in"
    val input = "01/1.in"

    val instructions = Files.readAllLines(Path.of(input)).map { line ->
        line[0] to line.slice(1..line.lastIndex).toInt()
    }

    a(instructions) // 1158
    b(instructions) // 6860
}
