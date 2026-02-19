package domain

data class Coordinates (
    val x: Float,
    val y: Float
){
    init {
        require(x <= 547f) { "Поле x должно быть меньше 547" }
    }
}
