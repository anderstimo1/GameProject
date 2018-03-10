package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by anderstimo1 on 8/03/2018.
 */

public class Boid {

    //divider for speed
    private int divider = 100;

    //graphic
    private Bitmap boidGraphic;

    //position
    private float xPos;
    private float yPos;

    //speed
    private float xSpeed;
    private float ySpeed;

    //constructor
    public Boid (Bitmap graphic, float boidX, float boidY, float boidXSpd, float boidYSpd) {
        boidGraphic = graphic;
        xPos = boidX;
        yPos = boidY;
        xSpeed = boidXSpd;
        ySpeed = boidYSpd;
    }

    //Getters
    public Bitmap getGraphic() {
        return boidGraphic;
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public float getXSpd() {
        return xSpeed;
    }

    public float getYSpd() {
        return ySpeed;
    }

    //Setters
    public void setGraphic (Bitmap graphic) {
        boidGraphic = graphic;
    }

    public void setX (float x) {
        xPos = x;
    }

    public void setY (float y) {
        yPos = y;
    }

    public void setXSpd (float xSpd) {
        xSpeed = xSpd;
    }

    public void setYSpd (float ySpd) {
        ySpeed = ySpd;
    }

    //Method for finding the distance between this boid and another
    public float findDistance (Boid boidToCheck) {
        //gets the x and y co-ordinates
        float x = boidToCheck.getX();
        float y = boidToCheck.getY();

        //use Pythagoras to find the distance between the boids
        //find the absolute difference between the x positions and y positions of the boids
        float xDistance = Math.abs(xPos - x);
        float yDistance = Math.abs(yPos - y);

        //finds the length of the hypotenuse of the triangle formed between the two boids
        //this is the shortest path between them
        float shortestPath = (float) (Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));

        return shortestPath;
    }

    //Boid behaviour

    //Schooling
    //this method will make the boid move towards the centre of mass of all nearby boids
    public ArrayList<Float> school (ArrayList<Boid> boidList) {
        int neighbourCount = 0;
        float xTotal = 0;
        float yTotal = 0;

        //find boids that are nearby and get total X & Y
        for (Boid b : boidList) { //for each boid in the list
            if (!b.equals(this)){ //if the boid is not the current boid
                float distance = this.findDistance(b);
                if (distance < (getGraphic().getWidth()*3)){ //if the boids are less than 10 boid widths apart
                    xTotal += b.getX();
                    yTotal += b.getY();
                    neighbourCount++;
                }
            }
        }

        ArrayList<Float> speeds = new ArrayList<Float>();

            //find average X and Y positions of neighbours
            float xAvg = xTotal / neighbourCount;
            float yAvg = yTotal / neighbourCount;

            //return the speeds
            speeds.add((xAvg - getX()) / divider);
            speeds.add((yAvg - getY()) / divider);

        return speeds;
    }

    public ArrayList<Float> separate (ArrayList<Boid> boidList) {
        int neighbourCount = 0;
        float xTotal = 0;
        float yTotal = 0;

        //find all boids within one boid width, sum all of their x & y positions
        for (Boid b : boidList) {
            if (b != this) {
                float distance = this.findDistance(b);
                //if the boid being checked is less or equal to one boid width away
                if (distance <= boidGraphic.getWidth()) {
                    //add to totals and increment neighbour count
                    xTotal += b.getX();
                    yTotal += b.getY();
                    neighbourCount++;
                }
            }
        }

        //find the "centre of mass" for all the nearby boids
        float xAvg = xTotal/neighbourCount;
        float yAvg = yTotal/neighbourCount;

        //find the speeds needed to move away from this point
        float xSpd = xAvg + this.getX();
        float ySpd = yAvg + this.getY();

        //return the speeds
        ArrayList<Float> speeds = new ArrayList<Float>();
        speeds.add(xSpd/divider);
        speeds.add(ySpd/divider);

        return speeds;
    }

    public ArrayList<Float> align (ArrayList<Boid> boidList) {
        int neighbourCount = 0;
        float xSpdTotal = 0;
        float ySpdTotal = 0;

        for (Boid b : boidList) {
            if (b != this) {
                float distance = this.findDistance(b);
                if (distance < (getGraphic().getWidth()*3)) {
                    //finds the total speeds of all nearby boids
                    xSpdTotal += b.getXSpd();
                    ySpdTotal += b.getYSpd();
                    neighbourCount++;
                }
            }
        }

        //find the average speed
        float xSpdAvg = xSpdTotal/neighbourCount;
        float ySpdAvg = ySpdTotal/neighbourCount;

        ArrayList<Float> speeds = new ArrayList<Float>();
        speeds.add(xSpdAvg/divider);
        speeds.add(ySpdAvg/divider);

        return speeds;
    }

}
