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
    val MAG = 20

    override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)
        g.drawRect(0, 0, MAG*grid.COLS, MAG*grid.ROWS)
        for (r <- 0 until grid.ROWS) {
            for (c <- 0 until grid.COLS) {
                g.setColor(Color.black)
                val x = c * MAG
                val y = r * MAG
                val o = grid.getObject(c, r)
                o match {
                    case Some(_) => {
                        val go = o.get
                        go match {
                            case a : Agent => {
                                setColor(g, a.id)
                                if (a.hasTile) {
                                    g.drawRect(x, y, MAG, MAG)
                                    g.drawOval(x, y, MAG, MAG)
                                } else {
                                    g.drawRect(x, y, MAG, MAG)
                                }
                            }
                            case _ : Tile => {
                                g.drawOval(x, y, MAG, MAG)
                            }
                            case _ : Hole => {
                                g.fillOval(x, y, MAG, MAG)
                            }
                            case _ : Obstacle => {
                                g.fillRect(x, y, MAG, MAG)
                            }
                        }
                    }
                    case None => {}
                }
            }
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
