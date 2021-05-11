package tileworld

object State extends Enumeration {
    type State = Value
    val Idle, MoveToHole, MoveToTile = Value
}
class Agent(id: Int, location : Location) extends GridObject(id, location) {
    var score : Int = 0
    var tile : Option[Tile] = None
    var hole : Option[Hole] = None
    var hasTile : Boolean = false
    var state : State.Value = State.Idle

    def update(grid : Grid) {
        state match {
            case State.Idle => {
                idle(grid)
            }
            case State.MoveToTile => {
                moveToTile(grid)
            }
            case State.MoveToHole => {
                moveToHole(grid)
            }
        }
    }

    def idle(grid: Grid) {
        tile = grid.findClosestTile(location)
        printf("agent %d got tile %s\n", id, tile)
        state = State.MoveToTile
    }

    def moveToTile(grid: Grid) {

    }

    def moveToHole(grid : Grid) {

    }
}
