package org.cypher;


import java.util.Arrays;

public class Vector {

    private double R;
    private double theta;
    private double[] components;

    public Vector(double comp1, double comp2) {
        components = new double[] {comp1, comp2};
        genAngles();
    }

    public Vector(double newTheta, double newMag, boolean isRad) {

        R = newMag;
        theta = newTheta;

        if (!isRad) {
            theta = Math.toRadians(theta);
        }

        genComp();
    }

    public Vector(Vector prevV) {

        components = prevV.getComponents();
        R = prevV.getMag();
        theta = prevV.getTheta();
    }



    public void genComp() {
        components = new double[2];

        components[0] = R * Math.cos(theta);
        components[1] = R * Math.sin(theta);
    }

    public void genMag() {

        R = Math.sqrt(Math.pow(components[0], 2) + Math.pow(components[1], 2));
    }

    public void genAngles() {
        genMag();

        if (components[0] == 0 && components[1] == 0) {
            theta = 0;
        } else {

            if (components[0] == 0) {

                theta = Math.PI / 2;

                if (components[1] < 0) {
                    theta *= -1;
                }
            } else {

                theta = Math.atan(components[1] / components[0]);

                if (components[0] < 0) {

                    if (components[1] >= 0) {

                        theta += Math.PI;
                    } else if (components[1] < 0) {
                        theta -= Math.PI;
                    } else {

                        theta = Math.PI;
                    }
                }
            }
        }
    }



    public double getMag() {

        return R;
    }

    public double getTheta() {

        return theta;
    }

    public double[] getComponents() {

        return Arrays.copyOf(components, components.length);
    }

    public double getX() {
        return getComponent(0);
    }

    public double getY() {
        return getComponent(1);
    }

    public Double getComponent(int component) {

        if (component >= 0 && component < 2) {

            return components[component];
        }

        return null;
    }

    //---------- Vector Operations ----------//

    public void setComponents(double[] newComp) {

        if (newComp.length == 2) {

            components = Arrays.copyOf(newComp, newComp.length);
            genAngles();
        }
    }

    public void add(Vector term_two) {

        double[] two_comp = term_two.getComponents();

        components[0] += two_comp[0];
        components[1] += two_comp[1];

        genAngles();
    }

    public void sub(Vector term_two) {
        components[0] -= term_two.components[0];
        components[1] -= term_two.components[1];

        genAngles();
    }

    public static Vector add(Vector term_one, Vector term_two) {

        double[] one_comp = term_one.getComponents();
        double[] two_comp = term_two.getComponents();

        double newX = one_comp[0] + two_comp[0];
        double newY = one_comp[1] + two_comp[1];

        return (new Vector(newX, newY));
    }

    public static Vector sub(Vector term_one, Vector term_two) {

        return Vector.add(term_one, invert(term_two));
    }

    public static Vector invert(Vector term_two) {

        Vector iTwo = new Vector(term_two);
        iTwo.scale(-1);

        return iTwo;
    }

    public static Vector scale(Vector term_one, double newScalar) {

        double angle = term_one.getTheta();
        double scalar = term_one.getMag();

        return new Vector(angle, scalar * newScalar, true);
    }

    public void zero() {

        this.scale(0);
    }

    public void scale(double scalar) {

        components[0] *= scalar;
        components[1] *= scalar;
    }

    public double dot(Vector term_two) {

        double[] two_comp = term_two.getComponents();

        return ((components[0] * two_comp[0]) + (components[1] * two_comp[1]));
    }

    public double angleBetween(Vector v2) {

        double dotProduct = this.dot(v2);
        double magnitudeProducts = R * v2.getMag();

        return Math.acos(dotProduct / magnitudeProducts);
    }

    public static double standardPosAngle(Vector v) {

        Vector i = new Vector(1d, 0d);
        Vector j = new Vector(0d, 1d);

        double iAngle = v.angleBetween(i);

        if (v.angleBetween(j) > (Math.PI / 2.0)) {

            iAngle = (Math.PI * 2.0) - iAngle;
        }

        return iAngle;
    }

    /**
     * reverses specified component of the vector
     * @param dimension
     */
    public void flipDimension(int dimension) {

        components[dimension] *= -1;
        genAngles();
    }

    /**
     * This function rotates the vector x radians counterclockwise
     * @param radians
     */
    public void rotate(double radians) {

        theta = (theta + radians) % (2 * Math.PI);
        genComp();
    }
    public String toStringWithoutWeirdBracketThingsSoThatTheLoggerCanWork(){
        String vector = "";
        vector = components[0]+","+components[1];
        return vector;
    }
    public String toString() {

        String vector = "<";
        vector += components[0] + ", ";
        vector += components[1] + ">";

        return vector;
    }

    public boolean equals(Vector compare) {

        for (int c = 0; c < components.length; c++) {

            if (compare.getComponent(c) != this.getComponent(c)) {

                return false;
            }
        }

        return true;
    }

    public static Vector[] copy(Vector[] original) {

        Vector[] newList = new Vector[original.length];

        for (int v = 0; v < original.length; v++) {

            newList[v] = new Vector(original[v]);
        }

        return newList;
    }

}