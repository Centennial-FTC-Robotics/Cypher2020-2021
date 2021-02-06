package org.cypher.purepursuit;

import org.cypher.util.Vector;

public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Vector vector) {
        x = vector.getX();
        y = vector.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistance(Point point2) {
        return Math.sqrt(Math.pow(x - point2.getX(), 2) + Math.pow(y - point2.getY(), 2));
    }

    public double getDistance(Vector point2) {
        return Math.sqrt(Math.pow(x - point2.getX(), 2) + Math.pow(y - point2.getY(), 2));
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Vector toVector() {
        return new Vector(x, y);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Waypoint)) {
            return false;
        }

        Waypoint other = (Waypoint) obj;
        return (getX() == other.getX() && getY() == other.getY());
    }

    public int hashCode() {
        return toString().hashCode();
    }

}

