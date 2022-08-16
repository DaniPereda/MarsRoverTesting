package application

import domain.*
import domain.Communicator
import domain.Translator

class MainApplication (val communicator:Communicator, val translator:Translator) {




    private var drivingService = DrivingService()

    fun start() {
        var positionWithDirection = PositionWithDirection(communicator.retrieveInitialPosition(), translator.translateDirection(communicator.retrieveInitialRowDirection()))

        initPlateau()
        initRover(positionWithDirection)

        positionWithDirection = moveRover()

        printResult(positionWithDirection)
    }

    private fun printResult(positionWithDirection: PositionWithDirection) {
        if (drivingService.rover.error != Errors.WITHOUT_ERRORS) {

        }else {
            val formattedPositionWithDirection = translator.positionWithDirectiontoString(positionWithDirection)
            communicator.printPositionWithDirection(formattedPositionWithDirection)
        }
    }

    private fun moveRover(): PositionWithDirection {
        return drivingService.processMovementOrders(getListOfTranslatedOrders())
    }

    private fun initRover(positionWithDirection: PositionWithDirection) {
        drivingService.placeRover(positionWithDirection)
    }

    private fun initPlateau() {
        drivingService.createPlateau(communicator.retrieveSizeMap())
    }


    private fun getListOfTranslatedOrders() = translator.translateOrders(communicator.retrieveOrders())







}