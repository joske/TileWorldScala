package tileworld

object Direction extends Enumeration {
    type Direction = Value
    val Up, Down, Left, Right = Value
}

case class Location(var c : Int, var r : Int) {
    def nextLocation(d: Direction.Value ): Location = {
        return d match {
            case Direction.Up => new Location(c, r - 1)
            case Direction.Down => new Location(c, r + 1)
            case Direction.Left => new Location(c - 1, r)
            case Direction.Right => new Location(c + 1, r)
        }
    }

    def distance(other : Location) : Int = {
        return (this.c - other.c).abs + (this.r - other.r).abs
    }

    override def toString(): String = {
        return s"($c, $r)"
    }

}
