package games.rockpaperscissors.entity

import java.util.*

enum class Figure {
    ROCK, PAPER, SCISSORS;

    private val PRNG = Random()

    open fun figureRandom(): Figure {
        return Figure.values()[PRNG.nextInt(Figure.values().size)]
    }

    open fun wins(f1: Movement, f2: Movement): Player? {
        if (f1.figure === PAPER) {
            return if (f2.figure === SCISSORS) f2.player
            else if (f2.figure === ROCK) f1.player
            else null
        }
        if (f1.figure === ROCK) {
            return if (f2.figure === PAPER) f2.player
            else if (f2.figure === SCISSORS) f1.player
            else null
        }
        if (f1.figure === SCISSORS) {
            return if (f2.figure === ROCK) f2.player
            else if (f2.figure === PAPER) f1.player
            else null
        }
        return null
    }
}