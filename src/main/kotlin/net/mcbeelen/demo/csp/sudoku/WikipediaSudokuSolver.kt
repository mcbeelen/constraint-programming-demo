package net.mcbeelen.demo.csp.sudoku

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

class WikipediaSudokuSolver {

    fun performMagic() {
        val model = Model("Solver model for a sudoku")

        val variables: Map<String, IntVar> = createSudokuGrid(model)

//    registerDefaultSudokuRulesAsConstraints(model, variables)

//    setInitialDigits(givenDigitsFromWikipediaSudoku(), model, variables)

        model.solver.solve()

        printSolution(variables)
    }

}

fun main() {

    val sudokuSolver = WikipediaSudokuSolver()
    sudokuSolver.performMagic()

}




private fun givenDigitsFromWikipediaSudoku() =
        mapOf(
                "A1" to 5,
                "B1" to 3,
                "E1" to 7,

                "A2" to 6,
                "D2" to 1,
                "E2" to 9,
                "F2" to 5,

                "B3" to 9,
                "C3" to 8,
                "H3" to 6,

                "A4" to 8,
                "E4" to 6,
                "I4" to 3,

                "A5" to 4,
                "D5" to 8,
                "F5" to 3,
                "I5" to 1,

                "A6" to 7,
                "E6" to 2,
                "I6" to 6,

                "B7" to 6,
                "G7" to 2,
                "H7" to 8,

                "D8" to 4,
                "E8" to 1,
                "F8" to 9,
                "I8" to 5,

                "E9" to 8,
                "H9" to 7,
                "I9" to 9

        )


