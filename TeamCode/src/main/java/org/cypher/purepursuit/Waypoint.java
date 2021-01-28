package org.cypher.purepursuit;

import org.cypher.util.Vector;

public class Waypoint extends Point{
    private double heading;
    double lookaheadRadius = 10; //default lookahead radius of 10 inches

    public Waypoint(double x, double y, double heading){
        super(x, y);
        this.heading = heading;
    }

    public Waypoint(double x, double y, double heading, double lookaheadRadius){
        super(x, y);
        this.heading = heading;
        this.lookaheadRadius = lookaheadRadius;
    }

    public Waypoint(Vector position, double heading){
        super(position);
        this.heading = heading;
    }

    public double getHeading(){
        return heading;
    }

    public double getLookaheadRadius(){
        return lookaheadRadius;
    }

    public String toString() {
        return "(" + x + "," + y + ") " + heading + " degrees";
    }

    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }

        if (!(obj instanceof Waypoint)) {
            return false;
        }

        Waypoint other = (Waypoint)obj;
        return (getX() == other.getX() && getY() == other.getY() && getHeading() == other.getHeading());
    }

    public int hashCode() {
        return toString().hashCode();
    }

}
