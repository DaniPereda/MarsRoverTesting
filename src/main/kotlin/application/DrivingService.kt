package application

import domain.*

class DrivingService {

    var plateau = Plateau()
    var rover = Rover()

    fun processMovementOrders(orders: List<Orders>): PositionWithDirection {
        for (order in orders) {
            rover.executeOrder(order)
            if(!isInMap(rover.positionWithDirection.position))
                rover.error = Errors.ERROR_OUT_OF_PLATEAU
        }
        return rover.positionWithDirection
    }

    private fun isInMap(position:Position): Boolean = plateau.isInPlateau(position)

    fun createPlateau(size: Size) {
        plateau = Plateau(size)
    }

    fun placeRover(positionWithDirection: PositionWithDirection) {
        if (isInMap(positionWithDirection.position)) {
            rover = Rover(positionWithDirection)
        } else
            rover.error = Errors.ERROR_OUT_OF_PLATEAU
    }

}