package infra.Translator

import domain.Directions
import domain.Orders
import domain.PositionWithDirection
import domain.Translator

class URSSTranslator : Translator {
    private enum class RowDirections(val value: Char) {
        NORTH('A'),
        SOUTH('B'),
        EAST('C'),
        WEST('D'),
    }

    private enum class RowOrders(val value: Char) {
        MOVE('X'),
        LEFT('Y'),
        RIGHT('Z'),
    }

    override fun translateDirection(rowDirection: Char): Directions {
        return when (rowDirection) {
            RowDirections.NORTH.value -> Directions.NORTH
            RowDirections.SOUTH.value -> Directions.SOUTH
            RowDirections.EAST.value -> Directions.EAST
            RowDirections.WEST.value -> Directions.WEST
            else -> throw java.lang.RuntimeException("Invalid direction $rowDirection. Mission canceled ")
        }
    }

    override fun directionToChar(direction: Directions): Char {
        return when (direction) {
            Directions.NORTH -> RowDirections.NORTH.value
            Directions.SOUTH -> RowDirections.SOUTH.value
            Directions.EAST -> RowDirections.EAST.value
            Directions.WEST -> RowDirections.WEST.value
        }
    }

    override fun translateOrders(rowOrders: String): List<Orders> {
        val ordersList = mutableListOf<Orders>()
        for (rowOrder in rowOrders) {
            ordersList.add(translateOrder(rowOrder))
        }
        return ordersList
    }

    private fun translateOrder(rowOrder: Char): Orders {
        return when (rowOrder) {
            RowOrders.MOVE.value -> Orders.MOVE
            RowOrders.LEFT.value -> Orders.LEFT
            RowOrders.RIGHT.value -> Orders.RIGHT
            else -> throw java.lang.RuntimeException("Invalid order $rowOrder. Mission canceled ")
        }
    }

    override fun positionWithDirectiontoString(positionWithDirection: PositionWithDirection):String{
        return positionWithDirection.position.x.toString() + ":" + positionWithDirection.position.y + ":" + directionToChar(positionWithDirection.direction)
    }


}