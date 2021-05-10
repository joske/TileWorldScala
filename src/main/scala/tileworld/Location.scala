package tileworld

object Direction extends Enumeration {
    type Direction = Value
    val Up, Down, Left, Right = Value
}

case class Location(c : Int, r : Int) {
    def nextLocation(d: Direction.Value ): Location = {
        return this
    }
}
