package minesweeper

enum class Cell(private val display: String) {
    HIDDEN("."), EMPTY("/"), MINE("X"), GUESS("*"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    override fun toString() = this.display

    fun inc() = when(this) {
        EMPTY -> ONE
        ONE -> TWO
        TWO -> THREE
        THREE -> FOUR
        FOUR -> FIVE
        FIVE -> SIX
        SIX -> SEVEN
        SEVEN -> EIGHT
        EIGHT -> NINE
        else -> this
    }

    fun isNumeric() = this in ONE..NINE
    fun isMine() = this == MINE

    fun guess() = if (this == GUESS) HIDDEN else GUESS
    fun validForOpen() = this == GUESS || this == HIDDEN
}