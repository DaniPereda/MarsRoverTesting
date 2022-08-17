package application

import domain.*

class DrivingService {

    private var plateau:Plateau = Plateau(Size(10,10))

    fun processMovementOrders(positionWithDirection: PositionWithDirection, orders: List<Orders>): Rover {
        var rover = Rover(positionWithDirection)
        for (order in orders) {
            rover.executeOrder(order)
            if(!isInMap(rover.positionWithDirection.position)) {
                rover.error = Errors.ERROR_OUT_OF_PLATEAU
                break
            }
        }
        return rover
    }

    private fun isInMap(position:Position): Boolean = plateau.isInPlateau(position)

    fun createPlateau(size: Size):Plateau {
        plateau = Plateau(size)
        return plateau
    }

    fun placeRover(positionWithDirection: PositionWithDirection):Rover {
        var rover = Rover(positionWithDirection)
        if (!isInMap(positionWithDirection.position))
            rover.error = Errors.ERROR_OUT_OF_PLATEAU

        return rover
    }

}