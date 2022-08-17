package domain

interface Translator {
    fun translateDirection(rowDirection:Char):Directions

    fun directionToChar(direction:Directions):Char

    fun translateOrders(orders:String):List<Orders>

    fun resultToString(rover:Rover):String



}