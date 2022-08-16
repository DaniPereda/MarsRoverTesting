package domain

data class Plateau(var size:Size=Size(10,10)) {
    init{
        if(size.width < 1 || size.height < 1)
            size = Size(0,0)
    }
    fun isInPlateau(position:Position):Boolean
    {
        if (isXCoordenateOK(position) && isYCoordenateOK(position)) {
            return true
        }
        return false
    }

    private fun isXCoordenateOK(position: Position) = position.x >= 0 && position.x < size.height

    private fun isYCoordenateOK(position: Position) = position.y >= 0 && position.y < size.height
}