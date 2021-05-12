package tileworld

import scala.swing.{Panel, MainFrame, SimpleSwingApplication}
import java.awt.{Color, Graphics2D, Dimension}
import javax.swing.JFrame
import java.awt.BorderLayout
import scala.swing.BoxPanel
import scala.swing.Orientation

object TileWorld extends App {
    val grid = new Grid()
    grid.init(6, 20)
    val ui = new UI(grid)
    ui.size = new Dimension(grid.COLS * grid.MAG + 200, grid.ROWS * grid.MAG + grid.MAG)
    ui.visible = true
    while (true) {
        grid.print()
        grid.update()
        ui.repaint()
        Thread.sleep(200)
    }
}

class UI(val grid : Grid) extends MainFrame {
    val view = new View(grid)
    contents = new BoxPanel(Orientation.Vertical) {
        contents += view
    }
}