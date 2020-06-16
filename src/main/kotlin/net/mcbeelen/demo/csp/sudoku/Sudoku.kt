package net.mcbeelen.demo.csp.sudoku

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar


val COLUMNS = 'A'..'I'
val ROWS = 1..9
const val BOX_SIZE = 3

fun buildSquareIdentifier(column: Char, row: Int) = "$column$row"


fun createSudokuGrid(model: Model): Map<String, IntVar> {
    val variables = HashMap<String, IntVar>().toMutableMap()
    ROWS.forEach { row ->
        COLUMNS.forEach { column ->
            createSudokuField(model, column, row) { variableName, variable ->
                variables[variableName] = variable
            }
        }
    }
    return variables
}

private fun createSudokuField(model: Model, column: Char, row: Int, callback: (variableName: String, variable: IntVar) -> Unit) {
    val variableName = "$column$row"
    val square = model.intVar(variableName, 1, 9, false)
    callback(variableName, square)
}




fun registerDefaultSudokuRulesAsConstraints(model: Model, variables: Map<String, IntVar>) {
    registerDigitsMustBeUniqueWithinEachRowRule(variables, model)
    registerDigitsMustBeUniqueWithinEachColumnRule(variables, model)
    registerDigitsMustBeUniqueWithinEachBoxRule(variables, model)
}



private fun registerDigitsMustBeUniqueWithinEachRowRule(variables: Map<String, IntVar>, model: Model) {
    ROWS.forEach { row ->
        val fieldsInEachRow = COLUMNS.map { column -> buildSquareIdentifier(column, row) }
                .map { variables[it]!! }
                .toList()
        model.allDifferent(fieldsInEachRow)
    }
}

private fun registerDigitsMustBeUniqueWithinEachColumnRule(variables: Map<String, IntVar>, model: Model) {
    COLUMNS.forEach { column ->
        val fieldsInEachRow = ROWS.map { row -> buildSquareIdentifier(column, row) }
                .map { variables[it]!! }
                .toList()
        model.allDifferent(fieldsInEachRow)
    }
}

private fun registerDigitsMustBeUniqueWithinEachBoxRule(variables: Map<String, IntVar>, model: Model) {
    for (rowCounter in 0..2) {
        val rowsInBox = ROWS.drop(rowCounter * BOX_SIZE).take(BOX_SIZE)
        for (columnCounter in 0..2) {
            val fieldsInBox = rowsInBox.map { row ->
                COLUMNS.drop(columnCounter * BOX_SIZE).take(BOX_SIZE)
                        .map { column -> buildSquareIdentifier(column, row) }
                        .map { variables[it]!! }
            }.flatMap { it }
            model.allDifferent(fieldsInBox)
        }
    }
}




fun setInitialDigits(givenDigits: Map<String, Int>, model: Model, variables: Map<String, IntVar>) {
    givenDigits.forEach { t, u ->
        println("$t --> $u");
        model.member(variables[t], u, u).post()
    }
}



fun printSolution(variables: Map<String, IntVar>) {

    print("    ")
    COLUMNS.forEachIndexed { index, column ->
        if (index % 3 == 0) {
            print(" ")
        }
        print(column)

    }
    println()

    ROWS.forEach { row ->
        print(" $row: ")
        COLUMNS.forEachIndexed { index, column ->
            if (index % 3 == 0) {
                print(" ")
            }
            val squareIdentifier = buildSquareIdentifier(column, row)
            print(variables[squareIdentifier]?.value)
        }
        println()
        if (row % 3 == 0) {
            println()
        }
    }
}

fun Model.allDifferent(fields: List<IntVar>) {
    val allDifferentConstraint = this.allDifferentExcept0(fields.toTypedArray())
    allDifferentConstraint.post()
}

