package minesweeper

class Printer {
    fun print(field: Field) {
        var i = 0
        printRow(" ", List(field.getWidth()) { "${++i}" })
        printRow("-", List(field.getWidth()) { "-" })

        for (j in 0 until field.getHeight()) {
            var i = 0
            printRow("${j + 1}", List(field.getWidth()) { field.getPlayerField()[Pair(i++, j)].toString() })
        }
        printRow("-", List(field.getWidth()) { "-" })
    }

    private fun printRow(firstElement: String, fieldElements: List<String>) = println(
        firstElement +
                "|" +
                fieldElements.joinToString("") +
                "|")
}