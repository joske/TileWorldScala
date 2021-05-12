package tileworld

import scala.collection.mutable.TreeSet
import scala.collection.mutable.HashSet
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.PriorityQueue

class Node (var location : Location, var parent : Option[Node], var score : Int) {
    var path = new ListBuffer[Location]()

    override def toString(): String = {
        return s"Node[$location, $score]"
    }
}

object AstarPath {
    def path(grid: Grid, from : Location, to : Location) : List[Location] = {
        Console.println(s"finding path from $from to $to")
        var openList = PriorityQueue.empty[Node](Ordering.by(_.score)).reverse
        var closedList = HashSet[Node]();
        var fromNode = new Node(from, None, 0);
        openList += fromNode;
        while (openList.size > 0) {
            val current = openList.head;
            openList = openList.tail
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

    def checkNeighbor(grid : Grid, openList : PriorityQueue[Node], closedList : HashSet[Node], current: Node, d : Direction.Value, from : Location, to : Location) {
        val nextLoc = current.location.nextLocation(d)
        if (nextLoc == to || grid.isValidLocation(nextLoc)) {
            val h = nextLoc.distance(to);
            val g = current.path.length + 1;
            val child = new Node(nextLoc, Some(current), g + h);
            child.path ++= current.path
            child.path += nextLoc
            if (!closedList.contains(child)) {
                if (openList.find((e:Node) => child.location == e.location && child.score > e.score) == None) {
                    openList += child;
                }
            }
        }
    }

}

