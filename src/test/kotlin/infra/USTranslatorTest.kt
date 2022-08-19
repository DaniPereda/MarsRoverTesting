package infra

import domain.*
import infra.Translator.USTranslator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFailsWith

internal class USTranslatorTest {
       @Test
    fun `translate direction N`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
           val result = sut.translateDirection('N')

        // THEN
        assertEquals(result, Directions.NORTH)

    }

    @Test
    fun `translate direction S`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
        val result = sut.translateDirection('S')

        // THEN
        assertEquals(result, Directions.SOUTH)

    }

    @Test
    fun `translate direction E`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
        val result = sut.translateDirection('E')

        // THEN
        assertEquals(result, Directions.EAST)

    }

    @Test
    fun `translate direction W`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
        val result = sut.translateDirection('W')

        // THEN
        assertEquals(result, Directions.WEST)

    }

    @Test
    fun `direction to char direction N`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
        val result = sut.directionToChar(Directions.NORTH)

        // THEN
        assertEquals(result, 'N')

    }
    @Test
    fun `direction to char direction S`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
        val result = sut.directionToChar(Directions.SOUTH)

        // THEN
        assertEquals(result, 'S')

    }
    @Test
    fun `direction to char direction E`() {
        // GIVEN
        val sut = USTranslator()

        // WHEN
        val result = sut.directionToChar(Directions.EAST)

        // THEN
        assertEquals(result, 'E')

    }
    @Test
    fun `direction to char direction W`() {
        // GIVEN
        val sut = USTranslator()

        // WHENª
        val result = sut.directionToChar(Directions.WEST)

        // THEN
        assertEquals(result, 'W')

    }

    @Test
    fun `translateOrders all correct`() {
        // GIVEN
        val sut = USTranslator()

        // WHENª
        val result = sut.translateOrders("MLR")

        // THEN
        assertEquals(result, listOf(Orders.MOVE,Orders.LEFT,Orders.RIGHT))

    }

    @Test
    fun `translateOrders with error`() {
        // GIVEN
        val sut = USTranslator()

        // WHENª

        // THEN
        assertFailsWith<RuntimeException>(
            message = "Invalid order d. Mission canceled ",
            block = {
                sut.translateOrders("MdLR")
            }
        )

    }

    @Test
    fun `resultToString ok`() {
        // GIVEN
        val sut = USTranslator()
        val rover = Rover(PositionWithDirection(Position(2,5),Directions.NORTH))

        // WHEN
        val result = sut.resultToString(rover)
        // THEN
        assertEquals("2,5,N", result)

    }

    @Test
    fun `resultToString error`() {
        // GIVEN
        val sut = USTranslator()
        var rover = Rover(PositionWithDirection(Position(2,5),Directions.NORTH))
        rover.error = Errors.ERROR_OUT_OF_PLATEAU

        // WHEN
        val result = sut.resultToString(rover)
        // THEN
        assertEquals("ERROR: Rover Outside of plateau", result)

    }
}