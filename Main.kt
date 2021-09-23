package minesweeper

fun main() {
    val printer = Printer()
    print("How many mines do you want on the field?")
    val minesNumber = readLine()!!.toInt()

    val field = Field(9, 9, minesNumber)
    printer.print(field)

    while(true) {
        print("Set/delete mine marks (x and y coordinates):")
        val input = readLine()!!.split(' ')
        val place = Pair(input[0].toInt() - 1, input[1].toInt() - 1)

        when (input[2]) {
            "free" ->
                if (!field.explore(place)) {
                    printer.print(field)
                    println("You stepped on a mine and failed!")
                    break
                }
            "mine" ->
                if (!field.mark(place)) {
                    println(field.getLastError())
                    continue
                }
        }

        if (field.allMinesAreFound()) {
            printer.print(field)
            println("Congratulations! You found all the mines!")
            break
        }

        printer.print(field)
    }
}