package net.mcbeelen.demo.csp.sudoku

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

typealias SquareIdentifier = String

class SudokuSolver {

    val model = Model("Some Sudoku")
    val COLUMNS = 'A' .. 'I'
    val ROWS = 1 .. 9

    val grid : Map<SquareIdentifier, IntVar> = buildGrid()

    fun buildGrid(): Map<SquareIdentifier, IntVar> {
        val grid = HashMap<SquareIdentifier, IntVar>().toMutableMap()
        ROWS.forEach { row ->
            COLUMNS.forEach { column ->
                val squareIdentifier = buildSquareIdentifier(column, row)
                val square = model.intVar(squareIdentifier, 1, 9, false)
                grid.put(squareIdentifier, square)
            }
        }
        return grid
    }

    private fun buildSquareIdentifier(column: Char, row: Int) = "$column$row"
    fun solve() {
        model.solver.solve()
    }

    fun printSolution() {

        print("    ")
        COLUMNS.forEach { column ->
            print(column)
        }
        println()

        ROWS.forEach { row ->
            print(" $row: ")
            COLUMNS.forEach { column ->
                val squareIdentifier = buildSquareIdentifier(column, row)
                print(grid[squareIdentifier]?.value)
            }
            println()
        }
    }


}

fun main() {

    val solver = SudokuSolver()
    solver.solve()
    solver.printSolution()

}