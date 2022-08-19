package infra

import domain.Errors
import domain.Position
import domain.Size
import infra.comunicator.HardcodeComunicator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class HardcodeComunicatorTest {
    @Test
    fun `retrieve orders`() {
        // GIVEN
        val sut = HardcodeComunicator()

        // WHEN
        val result = sut.retrieveOrders()

        // THEN
        Assertions.assertEquals(result, "MLLMMRM")
    }

    @Test
    fun `retrieve initial position`() {
        // GIVEN
        val sut = HardcodeComunicator()

        // WHEN
        val result = sut.retrieveInitialPosition()

        // THEN
        Assertions.assertEquals(result, Position(2, 5))
    }

    @Test
    fun `retrieve initial facing direction`() {
        // GIVEN
        val sut = HardcodeComunicator()

        // WHEN
        val result = sut.retrieveInitialRowDirection()

        // THEN
        Assertions.assertEquals(result, "N")
    }

    @Test
    fun `retrieve size map`() {
        // GIVEN
        val sut = HardcodeComunicator()

        // WHEN
        val result = sut.retrieveSizeMap()

        // THEN
        Assertions.assertEquals(result, Size(10,10))
    }

    @Test
    fun `print position with direction test`() {
        // GIVEN
        val sut = HardcodeComunicator()

        // WHEN
        val result = sut.printPositionWithDirection("2:5:N")

        // THEN
        Assertions.assertEquals(result, "the final position is  2:5:N")
    }

    @Test
    fun `print error test`() {
        // GIVEN
        val sut = HardcodeComunicator()

        // WHEN
        val result = sut.printError(Errors.ERROR_OUT_OF_PLATEAU)

        // THEN
        Assertions.assertEquals(result, "ERROR-OUT OF PLATEAU")
    }


}