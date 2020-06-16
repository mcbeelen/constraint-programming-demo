package net.mcbeelen.demo.csp.sudoku

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

class AivdSudokuSolver {
    fun performMagic() {
        val model = Model("Solver model for a sudoku")

        val variables: Map<String, IntVar> = createSudokuGrid(model)

        registerDefaultSudokuRulesAsConstraints(model, variables)

        applyConstraints()
       // setInitialDigits(givenDigitsFromWikipediaSudoku(), model, variables)

        model.solver.solve()

        printSolution(variables)
    }

}

fun main() {
    fun main() {

        val sudokuSolver = AivdSudokuSolver()
        sudokuSolver.performMagic()

    }
}