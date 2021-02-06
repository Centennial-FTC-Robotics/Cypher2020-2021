package org.cypher.purepursuit;

import org.cypher.Vector;

import java.util.Set;

public abstract class PathComponent {
    Waypoint startPoint;
    Waypoint endPoint;
    int index;
    public abstract Set<IntersectionPoint> findIntersections(Vector robotPosition, double lookaheadRadius);
    public abstract double length();
}
