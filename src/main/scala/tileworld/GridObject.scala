package tileworld

class GridObject(var id : Int = 0, var location : Location) {
    def move(newLocation : Location) {
        location = newLocation
    }
}
