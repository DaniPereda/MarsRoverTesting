package infra.comunicator

import domain.Communicator
import domain.Position
import domain.Size

class HardcodeComunicator() : Communicator {
    override fun retrieveOrders(): String {
        return "MLLMMRM"
    }

    override fun retrieveInitialPosition(): Position {

        return Position(2, 5)
    }

    override fun retrieveInitialRowDirection(): Char {
        println("Please, Enter the rover facing direction ")
        return 'W'
    }



    override fun retrieveSizeMap(): Size {

        return Size(10, 10)
    }

    override fun printPositionWithDirection(formattedPositionWithDirection: String) {
        println("the final position is  $formattedPositionWithDirection")
    }

    override fun printError(formattedPositionWithDirection: String) {
        println("ERROR-OUT OF PLATEAU")
    }

}