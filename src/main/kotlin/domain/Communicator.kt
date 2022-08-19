package domain

interface Communicator {
    fun retrieveOrders():String

    fun retrieveInitialPosition():Position

    fun retrieveInitialRowDirection():Char

    fun retrieveSizeMap():Size
    fun printPositionWithDirection(formattedPositionWithDirection:String)
    fun printError(errorCode:Errors)
}