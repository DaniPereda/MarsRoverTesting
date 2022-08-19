package infra.comunicator

import domain.Communicator
import domain.Errors
import domain.Position
import domain.Size

class ConsoleComunicator() : Communicator {
    override fun retrieveOrders(): String {
        println("Welcome to the order rover comunicator")
        println("Please, write the orders to communicate")
        return readLine().toString()
    }

    override fun retrieveInitialPosition(): Position {
        println("Welcome to the landing rover comunicator")
        println("Please, enter X value")
        val x = readLine()!!.toInt()
        println("Please, enter Y value")
        val y = readLine()!!.toInt()

        return Position(x, y)
    }

    override fun retrieveInitialRowDirection(): Char {
        println("Please, Enter the rover facing direction ")
        return readLine()!!.toCharArray()[0]
    }


    override fun retrieveSizeMap(): Size {
        println("Welcome to the map sizing communicator")
        println("Please, enter the width of your map")
        val width = readLine()!!.toInt()
        println("Please, enter the height of your map")
        val height = readLine()!!.toInt()

        return Size(width, height)
    }

    override fun printPositionWithDirection(formattedPositionWithDirection: String) {
        println("y la posicion es  $formattedPositionWithDirection")
    }

    override fun printError(error: Errors) {
        if (error == Errors.ERROR_OUT_OF_PLATEAU)
            println("ERROR-OUT OF PLATEAU")
    }
}