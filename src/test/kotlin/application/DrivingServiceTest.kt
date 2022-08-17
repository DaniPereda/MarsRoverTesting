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
        val result = sut.createPlateau(size)

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
        val resultZeroWidth = sut.createPlateau(sizeZeroWidth)
        val resultZeroHeight = sut.createPlateau(sizeZeroHeight)
        val resultNegativeWidth = sut.createPlateau(sizeNegativeWidth)
        val resultNegativeHeight = sut.createPlateau(sizeNegativeHeight)

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
        val result45N = sut.placeRover(position45N).positionWithDirection
        val result12S = sut.placeRover(position12S).positionWithDirection
        val result09E = sut.placeRover(position09E).positionWithDirection
        val result90W = sut.placeRover(position90W).positionWithDirection

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

        val resultRoverOutN = sut.placeRover(positionOutN).error
        val resultRoverOutS =  sut.placeRover(positionOutS).error
        val resultRoverOutE = sut.placeRover(positionOutE).error
        val resultRoverOutW = sut.placeRover(positionOutW).error

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

        // WHEN
        val directionW = sut.processMovementOrders(PositionWithDirection(Position(5, 5),Directions.NORTH),  listOf(Orders.LEFT)).positionWithDirection.direction
        val directionS = sut.processMovementOrders(PositionWithDirection(Position(5, 5),directionW),  listOf(Orders.LEFT)).positionWithDirection.direction
        val directionE = sut.processMovementOrders(PositionWithDirection(Position(5, 5),directionS),  listOf(Orders.LEFT)).positionWithDirection.direction
        val directionN = sut.processMovementOrders(PositionWithDirection(Position(5, 5),directionE),  listOf(Orders.LEFT)).positionWithDirection.direction

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

        // WHEN
        val directionE = sut.processMovementOrders(PositionWithDirection(Position(5, 5),Directions.NORTH),  listOf(Orders.RIGHT)).positionWithDirection.direction
        val directionS = sut.processMovementOrders(PositionWithDirection(Position(5, 5),directionE),  listOf(Orders.RIGHT)).positionWithDirection.direction
        val directionW = sut.processMovementOrders(PositionWithDirection(Position(5, 5),directionS),  listOf(Orders.RIGHT)).positionWithDirection.direction
        val directionN = sut.processMovementOrders(PositionWithDirection(Position(5, 5),directionW),  listOf(Orders.RIGHT)).positionWithDirection.direction

        // THEN
        assertEquals(Directions.EAST, directionE)
        assertEquals(Directions.SOUTH, directionS)
        assertEquals(Directions.WEST, directionW)
        assertEquals(Directions.NORTH, directionN)
    }

    @Test
    fun `move inside Plateau North`() {
        // GIVEN
        val sut = DrivingService()
        val plateau = sut.createPlateau(Size(10, 10))
        val positionWithDirection = PositionWithDirection(Position(5, 5),Directions.NORTH)

        // WHEN
        val rover = sut.processMovementOrders(positionWithDirection, listOf(Orders.MOVE))
         // THEN
        assertEquals(PositionWithDirection(Position(5,6),Directions.NORTH), rover.positionWithDirection)
        assertEquals(Errors.WITHOUT_ERRORS, rover.error)
    }

    @Test
    fun `move inside Plateau South`() {
        // GIVEN
        val sut = DrivingService()
        val plateau = sut.createPlateau(Size(10, 10))
        val positionWithDirection = PositionWithDirection(Position(5, 5),Directions.SOUTH)

        // WHEN
        val rover = sut.processMovementOrders(positionWithDirection, listOf(Orders.MOVE))
        // THEN
        assertEquals(PositionWithDirection(Position(5,4),Directions.SOUTH), rover.positionWithDirection)
        assertEquals(Errors.WITHOUT_ERRORS, rover.error)
    }

    @Test
    fun `move inside Plateau East`() {
        // GIVEN
        val sut = DrivingService()
        val plateau = sut.createPlateau(Size(10, 10))
        val positionWithDirection = PositionWithDirection(Position(5, 5),Directions.EAST)

        // WHEN
        val rover = sut.processMovementOrders(positionWithDirection, listOf(Orders.MOVE))
        // THEN
        assertEquals(PositionWithDirection(Position(6,5),Directions.EAST), rover.positionWithDirection)
        assertEquals(Errors.WITHOUT_ERRORS, rover.error)
    }

    @Test
    fun `move inside Plateau West`() {
        // GIVEN
        val sut = DrivingService()
        val plateau = sut.createPlateau(Size(10, 10))
        val positionWithDirection = PositionWithDirection(Position(5, 5),Directions.WEST)

        // WHEN
        val rover = sut.processMovementOrders(positionWithDirection, listOf(Orders.MOVE))
        // THEN
        assertEquals(PositionWithDirection(Position(4,5),Directions.WEST), rover.positionWithDirection)
        assertEquals(Errors.WITHOUT_ERRORS, rover.error)
    }

    @Test
    fun `Move Outside the plateau North`() {
        // GIVEN
        val sut = DrivingService()
        val initialPosition = PositionWithDirection(Position(5, 9),Directions.NORTH)

        // WHEN
        val result = sut.processMovementOrders(initialPosition, listOf(Orders.MOVE)).error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }



    @Test
    fun `Move Outside the plateau South in final position`() {
        // GIVEN
        val sut = DrivingService()
        val initialPosition = PositionWithDirection(Position(5, 0),Directions.SOUTH)

        // WHEN
        val result = sut.processMovementOrders(initialPosition, listOf(Orders.MOVE)).error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }


    @Test
    fun `Move Outside the plateau EAST in final position`() {
        // GIVEN
        val sut = DrivingService()
        val initialPosition = PositionWithDirection(Position(9, 5),Directions.EAST)

        // WHEN
        val result = sut.processMovementOrders(initialPosition,  listOf(Orders.MOVE)).error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }

    @Test
    fun `Move Outside the plateau West in final position`() {
        // GIVEN
        val sut = DrivingService()
        val initialPosition = PositionWithDirection(Position(0, 5),Directions.WEST)

        // WHEN
        val result = sut.processMovementOrders(initialPosition, listOf(Orders.MOVE)).error
        // THEN
        assertEquals(Errors.ERROR_OUT_OF_PLATEAU, result)
    }


}