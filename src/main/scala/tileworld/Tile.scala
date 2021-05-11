package tileworld

class Tile(id: Int, location : Location, var score: Int) extends GridObject(id, location) {
    override def toString(): String = {
        return s"Tile $id@$location"
    }
}
