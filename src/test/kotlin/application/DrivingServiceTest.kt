package application

import domain.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DrivingServiceTest {

    @Test
    fun `createPlateau should retrieve the expected plateau`() {
        // GIVEN
        val sut = DrivingService()
        val size = Size(10,10)


        // WHEN
        sut.createPlateau(size)
        val result = sut.plateau

        // THEN
        assertEquals(result, Plateau(size))
    }

    @Test
    fun `createPlateau should retrieve size0,0 if one dimension is lower than 1`() {
        // GIVEN
        val sut = DrivingService()
        val sizeZeroWidth = Size(0,10)
        val sizeZeroHeight = Size(10,0)
        val sizeNegativeWidth = Size(-1,10)
        val sizeNegativeHeight = Size(10,-1)


        // WHEN
        sut.createPlateau(sizeZeroWidth)
        val resultZeroWidth = sut.plateau
        sut.createPlateau(sizeZeroHeight)
        val resultZeroHeight = sut.plateau
        sut.createPlateau(sizeNegativeWidth)
        val resultNegativeWidth = sut.plateau
        sut.createPlateau(sizeNegativeHeight)
        val resultNegativeHeight = sut.plateau

        // THEN
        assertEquals(resultZeroWidth, Plateau(Size(0,0)))
        assertEquals(resultZeroHeight, Plateau(Size(0,0)))
        assertEquals(resultNegativeWidth, Plateau(Size(0,0)))
        assertEquals(resultNegativeHeight, Plateau(Size(0,0)))
    }

    @Test
    fun `check placeRover inside Plateau -direction & position-`(){
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10,10))
        val position45N = PositionWithDirection(Position(4,5), Directions.NORTH)
        val position12S = PositionWithDirection(Position(1,2), Directions.SOUTH)
        val position09E = PositionWithDirection(Position(0,9), Directions.EAST)
        val position90W = PositionWithDirection(Position(9,0), Directions.WEST)


        // WHEN
        sut.placeRover(position45N)
        val result45N = sut.rover.positionWithDirection
        sut.placeRover(position12S)
        val result12S = sut.rover.positionWithDirection
        sut.placeRover(position09E)
        val result09E = sut.rover.positionWithDirection
        sut.placeRover(position90W)
        val result90W = sut.rover.positionWithDirection

        // THEN
        assertEquals(result45N, PositionWithDirection(Position(4,5),Directions.NORTH))
        assertEquals(result12S, PositionWithDirection(Position(1,2),Directions.SOUTH))
        assertEquals(result09E, PositionWithDirection(Position(0,9),Directions.EAST))
        assertEquals(result90W, PositionWithDirection(Position(9,0),Directions.WEST))

    }

    @Test
    fun `check placeRover OUTSIDE Plateau -direction & position-`(){
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10,10))
        val positionOutN = PositionWithDirection(Position(2,50), Directions.NORTH)
        val positionOutS = PositionWithDirection(Position(-1,2), Directions.SOUTH)
        val positionOutE = PositionWithDirection(Position(10,9), Directions.EAST)
        val positionOutW = PositionWithDirection(Position(5,-1), Directions.WEST)

        // WHEN
        sut.placeRover(positionOutN)
        val resultRoverOutN = sut.rover.error
        sut.placeRover(positionOutS)
        val resultRoverOutS = sut.rover.error
        sut.placeRover(positionOutE)
        val resultRoverOutE = sut.rover.error
        sut.placeRover(positionOutW)
        val resultRoverOutW = sut.rover.error

        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, resultRoverOutN)
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, resultRoverOutS)
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, resultRoverOutE)
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, resultRoverOutW)
}

    @Test
    fun `turn Left from NSEW`() {

        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 5),Directions.NORTH))

        // WHEN
        sut.processMovementOrders(listOf(Orders.LEFT))
        val directionW = sut.rover.positionWithDirection.direction
        sut.processMovementOrders(listOf(Orders.LEFT))
        val directionS = sut.rover.positionWithDirection.direction
        sut.processMovementOrders(listOf(Orders.LEFT))
        val directionE = sut.rover.positionWithDirection.direction
        sut.processMovementOrders(listOf(Orders.LEFT))
        val directionN = sut.rover.positionWithDirection.direction

        // THEN
        assertEquals(Directions.WEST, directionW)
        assertEquals(Directions.SOUTH, directionS)
        assertEquals(Directions.EAST, directionE)
        assertEquals(Directions.NORTH, directionN)
    }

    @Test
    fun `turn Right from NSEW`() {

        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 5),Directions.NORTH))

        // WHEN
        sut.processMovementOrders(listOf(Orders.RIGHT))
        val directionE = sut.rover.positionWithDirection.direction
        sut.processMovementOrders(listOf(Orders.RIGHT))
        val directionS = sut.rover.positionWithDirection.direction
        sut.processMovementOrders(listOf(Orders.RIGHT))
        val directionW = sut.rover.positionWithDirection.direction
        sut.processMovementOrders(listOf(Orders.RIGHT))
        val directionN = sut.rover.positionWithDirection.direction

        // THEN
        assertEquals(Directions.EAST, directionE)
        assertEquals(Directions.SOUTH, directionS)
        assertEquals(Directions.WEST, directionW)
        assertEquals(Directions.NORTH, directionN)
    }

    @Test
    fun `move inside Plateau in each direction`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 5),Directions.NORTH))

        // WHEN
       /* sut.processMovementOrders(listOf(Orders.MOVE))
        val position56N = sut.rover.positionWithDirection
        sut.processMovementOrders(listOf(Orders.RIGHT, Orders.MOVE))
        val position66E = sut.rover.positionWithDirection
        sut.processMovementOrders(listOf(Orders.RIGHT, Orders.MOVE))
        val position65S = sut.rover.positionWithDirection
        sut.processMovementOrders(listOf(Orders.RIGHT, Orders.MOVE))
        val position55W = sut.rover.positionWithDirection

        // THEN
        assertEquals(PositionWithDirection(Position(5,6),Directions.NORTH), position56N)
        assertEquals(PositionWithDirection(Position(6,6),Directions.EAST), position66E)
        assertEquals(PositionWithDirection(Position(6,5),Directions.SOUTH), position65S)
        assertEquals(PositionWithDirection(Position(5,5),Directions.WEST), position55W)*/

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE))
        val position56N = sut.rover.positionWithDirection
        // THEN
        assertEquals(PositionWithDirection(Position(5,6),Directions.NORTH), position56N)
        // WHEN
        sut.processMovementOrders(listOf(Orders.RIGHT, Orders.MOVE))
        val position66E = sut.rover.positionWithDirection
        // THEN
        assertEquals(PositionWithDirection(Position(6,6),Directions.EAST), position66E)
        // WHEN
        sut.processMovementOrders(listOf(Orders.RIGHT, Orders.MOVE))
        val position65S = sut.rover.positionWithDirection
        assertEquals(PositionWithDirection(Position(6,5),Directions.SOUTH), position65S)
        // WHEN
        sut.processMovementOrders(listOf(Orders.RIGHT, Orders.MOVE))
        val position55W = sut.rover.positionWithDirection
        // THEN
        assertEquals(PositionWithDirection(Position(5,5),Directions.WEST), position55W)
        assertEquals(sut.rover.error, Errors.WITHOUT_ERRORS)

    }

    @Test
    fun `Move Outside the plateau North in final position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 9),Directions.NORTH))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau North in intermediate position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 9),Directions.NORTH))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE, Orders.LEFT, Orders.LEFT, Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau South in final position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 0),Directions.SOUTH))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau South in intermediate position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(5, 0),Directions.SOUTH))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE, Orders.LEFT, Orders.LEFT, Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau EAST in final position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(9, 5),Directions.EAST))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }
    @Test
    fun `Move Outside the plateau EAST in intermediate position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(9, 5),Directions.EAST))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE, Orders.LEFT, Orders.LEFT, Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau West in final position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(0, 5),Directions.WEST))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau West in intermediate position`() {
        // GIVEN
        val sut = DrivingService()
        sut.createPlateau(Size(10, 10))
        sut.placeRover(PositionWithDirection(Position(0, 5),Directions.WEST))

        // WHEN
        sut.processMovementOrders(listOf(Orders.MOVE, Orders.LEFT, Orders.LEFT, Orders.MOVE))
        val result = sut.rover.error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }


}