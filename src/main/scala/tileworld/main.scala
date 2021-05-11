package tileworld

object TileWorld extends App {
    val grid = new Grid()
    grid.init(5, 5)
    while (true) {
        grid.print()
        grid.update()
        Thread.sleep(1000)
    }
}