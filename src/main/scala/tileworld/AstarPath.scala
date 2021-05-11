package tileworld

import scala.collection.mutable.TreeSet
import scala.collection.mutable.HashSet
import scala.collection.mutable.ListBuffer

class Node (var location : Location, var parent : Option[Node], var score : Int) {
    var path = new ListBuffer[Location]()
}

object AstarPath {
    def path(grid: Grid, from : Location, to : Location) : List[Location] = {
        Console.println(s"finding path from $from to $to")
        var openList = TreeSet.empty[Node](Ordering.by(_.score))
        var closedList = HashSet[Node]();
        var fromNode = new Node(from, None, 0);
        openList += fromNode;
        while (openList.size > 0) {
            val current = openList.head;
            openList.remove(current)
            if (current.location == to) {
                // goal reached
                return current.path.toList
            }
            closedList += current
            checkNeighbor(grid, openList, closedList, current, Direction.Up, from, to)
            checkNeighbor(grid, openList, closedList, current, Direction.Down, from, to)
            checkNeighbor(grid, openList, closedList, current, Direction.Left, from, to)
            checkNeighbor(grid, openList, closedList, current, Direction.Right, from, to)
        }
        return List.empty[Location]
    }

    def checkNeighbor(grid : Grid, openList : TreeSet[Node], closedList : HashSet[Node], current: Node, d : Direction.Value, from : Location, to : Location) {
        Console.println(s"considering direction $d from ${current.location}")
        val nextLoc = current.location.nextLocation(d)
        if (nextLoc == to || grid.isValidLocation(nextLoc)) {
            Console.println(s"location $nextLoc is valid")
            val h = nextLoc.distance(to);
            val g = current.location.distance(from) + 1;
            val child = new Node(nextLoc, Some(current), g + h);
            child.path = current.path.clone()
            child.path += nextLoc
            if (!closedList.contains(child)) {
                if (openList.find((e:Node) => child.location == e.location && child.score < e.score) == None) {
                    openList += child;
                }
            }
        }
    }

}
