package tileworld

import java.awt.Panel
import java.awt.Graphics2D
import java.awt.Frame
import java.awt.Window
import scala.swing.Component
import java.awt.Graphics
import java.awt.Dimension
import java.awt.Color

class View(grid:Grid) extends Component {

    override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)
        g.drawRect(0, 0, grid.MAG*grid.COLS, grid.MAG*grid.ROWS)
        for (r <- 0 until grid.ROWS) {
            for (c <- 0 until grid.COLS) {
                g.setColor(Color.black)
                val x = c * grid.MAG
                val y = r * grid.MAG
                val o = grid.getObject(c, r)
                o match {
                    case Some(_) => {
                        val go = o.get
                        go match {
                            case a : Agent => {
                                setColor(g, a.id)
                                if (a.hasTile) {
                                    g.drawRect(x, y, grid.MAG, grid.MAG)
                                    g.drawOval(x, y, grid.MAG, grid.MAG)
                                } else {
                                    g.drawRect(x, y, grid.MAG, grid.MAG)
                                }
                            }
                            case _ : Tile => {
                                g.drawOval(x, y, grid.MAG, grid.MAG)
                            }
                            case _ : Hole => {
                                g.fillOval(x, y, grid.MAG, grid.MAG)
                            }
                            case _ : Obstacle => {
                                g.fillRect(x, y, grid.MAG, grid.MAG)
                            }
                        }
                    }
                    case None => {}
                }
            }
        }
        val x = grid.COLS * grid.MAG + 50;
        val y = 20;
        for (a <- grid.agents) {
            setColor(g, a.id)
            g.drawString(s"Agent ${a.id} : ${a.score}", x, y + a.id * grid.MAG)
        }

    }

    def setColor(g: Graphics2D, id : Int) {
        id match {
            case 1 => g.setColor(Color.blue)
            case 2 => g.setColor(Color.green)
            case 3 => g.setColor(Color.yellow)
            case 4 => g.setColor(Color.red)
            case 5 => g.setColor(Color.cyan)
            case 6 => g.setColor(Color.orange)
        }
    }
}
