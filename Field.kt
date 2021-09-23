package minesweeper

import kotlin.random.Random

class Field(private val width: Int, private val height: Int, private val minesNumber: Int) {
    private val field : MutableMap<Pair<Int, Int>, Cell> = mutableMapOf()
    private val playerField : MutableMap<Pair<Int, Int>, Cell> = mutableMapOf()
    private var lastError : String = ""

    init {
        if (minesNumber > width * height) {
            throw Exception("Wrong mines number")
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                field[Pair(i, j)] = Cell.EMPTY
                playerField[Pair(i, j)] = Cell.HIDDEN
            }
        }

        var minesPlaced = 0
        val random = Random.Default

        while (minesPlaced < minesNumber) {
            val nextMine = random.nextInt(width * height)
            val place = Pair(nextMine / width, nextMine % width)

            if (!placeMine(place)) continue
            minesPlaced++
        }
    }

    fun getWidth() = width
    fun getHeight() = height
    fun getPlayerField() = playerField

    fun getLastError() : String = lastError
    fun mark(place: Pair<Int, Int>) : Boolean {
        if (!playerField.containsKey(place)) {
            lastError = "Place is out of the field"
            return false
        }

        if (playerField[place]!!.isNumeric()) {
            lastError = "There is a number here!"
            return false
        }

        playerField[place] = playerField[place]!!.guess()
        return true
    }

    fun explore(place: Pair<Int, Int>) : Boolean {
        if (field[place]!! == Cell.MINE) {
            showAllMines()
            return false
        }

        open(place)
        return true
    }

    private fun showAllMines() {
        val mines = field.filter { it.value == Cell.MINE }

        for (mine in mines) {
            playerField[mine.key] = mine.value
        }
    }

    private fun open(place: Pair<Int, Int>) {
        if (!field.containsKey(place) || !playerField[place]!!.validForOpen()) return

        if (!field[place]!!.isMine()) {
            playerField[place] = field[place]!!

            if (field[place] == Cell.EMPTY) {
                for (i in place.first - 1..place.first + 1) {
                    for (j in place.second - 1..place.second + 1) {
                        open(Pair(i, j))
                    }
                }
            }
        }
    }

    fun allMinesAreFound() : Boolean {
        val possibleMines = playerField.filter { it.value == Cell.GUESS || it.value == Cell.HIDDEN }

        // if remaining possible mines number equals initial mines number
        // then all mines are found
        if (possibleMines.size == minesNumber) {
            return true
        }

        val guesses = possibleMines.filter { it.value == Cell.GUESS }

        // if number of guesses isn't equal to initial mines number
        if (guesses.size != minesNumber) {
            return false
        }

        // if not all guesses are correct
        if (guesses.filter { field[it.key] != Cell.MINE }.isNotEmpty()) {
            return false
        }

        return true
    }

    private fun placeMine(place: Pair<Int, Int>) : Boolean {
        if (field[place]!! == Cell.MINE) return false

        field[place] = Cell.MINE

        for (i in place.first - 1..place.first + 1) {
            for (j in place.second - 1..place.second + 1) {
                val placeToInc = Pair(i, j)
                if (field.containsKey(placeToInc)) {
                    field[placeToInc] = field[placeToInc]!!.inc()
                }
            }
        }

        return true
    }
}