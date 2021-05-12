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
        printf("%s got tile %s\n", this, tile)
        state = State.MoveToTile
    }

    def moveToTile(grid: Grid) {
        if (grid.getObject(tile.get.location.c, tile.get.location.r) != tile) {
            // our hole is gone
            tile = grid.findClosestTile(location)
        }
        if (tile.get.location == location) {
            // arrived
            pickTile(grid)
            return
        }
        val path = AstarPath.path(grid, location, tile.get.location)
        if (path.length > 0) {
            Console.println(s"$this got path $path")
            val nextLocation = path.head
            grid.moveAgent(this, nextLocation)
        }
    }

    def moveToHole(grid : Grid) {
        if (grid.getObject(hole.get.location.c, hole.get.location.r) != hole) {
            // our hole is gone
            hole = grid.findClosestHole(location)
        }
        if (hole.get.location == location) {
            // arrived
            dumpTile(grid)
            return
        }
        val path = AstarPath.path(grid, location, hole.get.location);
        if (path.length > 0) {
            Console.println(s"$this got path $path")
            val nextLocation = path.head
            grid.moveAgent(this, nextLocation)
        }
    }

    def pickTile(grid : Grid) {
        hasTile = true
        grid.removeTile(tile.get)
        hole = grid.findClosestHole(location)
        printf("%s got hole %s\n", this, hole)        
        state = State.MoveToHole
    }

    def dumpTile(grid : Grid) {
        printf("%s dumptile\n", this)        
        hasTile = false
        score = score + tile.get.score
        grid.removeHole(hole.get)
        hole = None
        tile = grid.findClosestTile(location)
        printf("%s got tile %s\n", this, tile)
        state = State.MoveToTile
    }

    override def toString(): String = {
        return s"Agent $id@$location"
    }

}
