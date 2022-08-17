package application

import domain.*
import domain.Communicator
import domain.Translator

class MainApplication (val communicator:Communicator, val translator:Translator) {




    private var drivingService = DrivingService()

    fun start() {
        var positionWithDirection = PositionWithDirection(communicator.retrieveInitialPosition(), translator.translateDirection(communicator.retrieveInitialRowDirection()))

        val plateau = initPlateau()
        initRover(positionWithDirection)

        var rover = moveRover(positionWithDirection, plateau)

        printResult(rover)
    }

    private fun printResult(rover: Rover) {
        if (rover.error != Errors.WITHOUT_ERRORS) {

        }else {
            val formattedPositionWithDirection = translator.resultToString(rover)
            communicator.printPositionWithDirection(formattedPositionWithDirection)
        }
    }

    private fun moveRover(positionWithDirection: PositionWithDirection, plateau:Plateau):Rover {
        return drivingService.processMovementOrders(positionWithDirection, getListOfTranslatedOrders())
    }

    private fun initRover(positionWithDirection: PositionWithDirection) {
        drivingService.placeRover(positionWithDirection)
    }

    private fun initPlateau():Plateau {
        return drivingService.createPlateau(communicator.retrieveSizeMap())
    }


    private fun getListOfTranslatedOrders() = translator.translateOrders(communicator.retrieveOrders())







}