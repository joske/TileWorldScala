package tileworld

class Agent(id: Int, location : Location) extends GridObject(id, location) {
    var score : Int = 0
    var tile : Option[Tile] = None
    var hole : Option[Hole] = None
    var hasTile : Boolean = false
}
