package tileworld

import scala.util.Random
import scala.collection.mutable.ListBuffer

class Grid {
    val COLS = 10
    val ROWS = 10
    val rand = new Random()

    var grid = Array.fill(COLS, ROWS)(None:Option[GridObject])
    var agents = new ListBuffer[Agent]()
    var tiles = new ListBuffer[Tile]()
    var holes = new ListBuffer[Hole]()

    def init(nAgents : Int, nTiles : Int) {
        for (i <- 1 to nAgents) {
            createAgent(i)
        }
        for (i <- 1 to nTiles) {
            createTile(i)
        }
        for (i <- 1 to nTiles) {
            createHole(i)
        }
        for (i <- 1 to nTiles) {
            createObstacle(i)
        }
    }

    private def createAgent(id : Int) {
        val l = randomFreeLocation()
        val agent = new Agent(id, l)
        agents += agent
        grid(l.c)(l.r) = Some(agent)
    }

    private def createTile(id : Int) {
        val l = randomFreeLocation()
        val score = rand.nextInt(6)
        val tile = new Tile(id, l, score)
        tiles += tile
        grid(l.c)(l.r) = Some(tile)
    }

    private def createHole(id : Int) {
        val l = randomFreeLocation()
        val hole = new Hole(id, l)
        holes += hole
        grid(l.c)(l.r) = Some(hole)
    }

    private def createObstacle(id : Int) {
        val l = randomFreeLocation()
        val obstacle = new Obstacle(id, l)
        grid(l.c)(l.r) = Some(obstacle)
    }

    private def randomFreeLocation() : Location = {
        var c = rand.nextInt(COLS)
        var r = rand.nextInt(ROWS)
        while (grid(c)(r) == Some) {
            c = rand.nextInt(COLS)
            r = rand.nextInt(ROWS)
        }
        return new Location(c, r)
    }

    def update() {
        for (a <- agents) {
            a.update(this);
        }
    }

    def getObject(c : Int, r : Int) : Option[GridObject] = {
        return grid(c)(r)
    }

    def findClosestTile(l : Location) : Option[Tile] = {
        var best : Option[Tile] = None
        var dist = Int.MaxValue
        for (t <- tiles) {
            val d = t.location.distance(l)
            if (d < dist) {
                dist = d
                best = Some(t)
            }
        }
        return best
    }

    def findClosestHole(l : Location) : Option[Hole] = {
        var best : Option[Hole] = None
        var dist = Int.MaxValue
        for (h <- holes) {
            val d = h.location.distance(l)
            if (d < dist) {
                dist = d
                best = Some(h)
            }
        }
        return best
    }

    def print() {
        for (r <- 0 until ROWS) {
            for (c <- 0 until COLS) {
                val o = grid(c)(r)
                o match {
                    case Some(_) => {
                        val go = o.get
                        go match {
                            case a : Agent => {
                                if (a.hasTile) {
                                    Console.print("Å");
                                } else {
                                    Console.print("A");
                                }
                            }
                            case _ : Tile => {
                                Console.print("T")
                            }
                            case _ : Hole => {
                                Console.print("H")
                            }
                            case _ : Obstacle => {
                                Console.print("#")
                            }
                        }
                    }
                    case None => {
                        Console.print(".")
                    }
                }
            }
            Console.println();
        }
        for (a <- agents) {
            printf("Agent %s: %d\n", a.id, a.score)
        }
    }
}