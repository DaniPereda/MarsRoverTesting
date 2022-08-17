package infra.Translator

import domain.*

class USTranslator : Translator {
    private enum class RowDirections(val value: Char) {
        NORTH('N'),
        SOUTH('S'),
        EAST('E'),
        WEST('W'),
    }

    private enum class RowOrders(val value: Char) {
        MOVE('M'),
        LEFT('L'),
        RIGHT('R'),
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

    override fun resultToString(rover: Rover): String {
        return when(rover.error){
            Errors.WITHOUT_ERRORS -> {
                "${rover.positionWithDirection.position.x.toString()}:${rover.positionWithDirection.position.y}:${directionToChar(
                    rover.positionWithDirection.direction)}"
            }
            Errors.ERROR_OUT_OF_PLATEAU ->{
                "ERROR: Rover Outside of plateau"
            }
        }

    }


}