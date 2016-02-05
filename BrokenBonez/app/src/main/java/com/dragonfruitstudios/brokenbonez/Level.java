package com.dragonfruitstudios.brokenbonez;

import android.graphics.Color;
import android.util.Log;

import com.dragonfruitstudios.brokenbonez.BoundingShapes.Circle;
import com.dragonfruitstudios.brokenbonez.BoundingShapes.Intersector;
import com.dragonfruitstudios.brokenbonez.BoundingShapes.Line;
import com.dragonfruitstudios.brokenbonez.BoundingShapes.Polygon;
import com.dragonfruitstudios.brokenbonez.BoundingShapes.Rect;

import java.util.ArrayList;

// Currently just a simple class to draw the level.
// TODO: Load level design from file.
// TODO: Scroll the level based on camera position
public class Level implements GameObject {
    VectorF startPoint; // Holds the coordinates which determine where the bike starts.
    ArrayList<Intersector> intersectors;

    public Level() {
        startPoint = new VectorF(0, 0); // Just a reasonable default.
        intersectors = new ArrayList<Intersector>();
        // TODO: Hardcoded for now.
        intersectors.add(new Rect(0, calcGroundHeight(), 3000, calcGroundHeight() + 50));

        intersectors.add(new Rect(10, 200, 500, 260));

        // Add a triangle

        Line triangleLeft = new Line(200, 300, 200, 150);
        Line triangleBottom = new Line(200, 300, 300, 300);
        Line triangleMiddle = new Line(200, 150, 300, 300);

        Polygon triangle = new Polygon(new Line[] {triangleLeft, triangleBottom, triangleMiddle});
        intersectors.add(triangle);
    }

    public void updateSize(int w, int h) {
        Log.d("UpdateSize", "Updating size in Level: " + w + " " + h);
        startPoint = calcStartPoint(w, h);
    }

    public void draw(GameView gameView) {
        float currHeight = calcGroundHeight();
        // Draw the sky
        gameView.drawRect(0, 0, gameView.getWidth(), currHeight,
                Color.parseColor("#06A1D3"));

        // Draw debug info.
        String debugInfo = String.format("Level[grndY: %.1f, colY: %.1f, totalY: %d]",
                currHeight, ((Rect)intersectors.get(0)).getTop(), gameView.getHeight());
        gameView.drawText(debugInfo, 100, 30, Color.WHITE);

        // Draw the grass.
        gameView.drawRect(0, currHeight, gameView.getWidth(),
                currHeight + 20, Color.parseColor("#069418"));
        currHeight += 20;
        // Draw the ground.
        gameView.drawRect(0, currHeight, gameView.getWidth(),
                gameView.getHeight(), Color.parseColor("#976600"));

        // More debug info drawing.
        for (Drawable r : intersectors) {
            r.draw(gameView);
        }

    }

    public void update(float lastUpdate) {
        // TODO
    }

    /**
     * Returns the Bike's starting point.
     */
    public VectorF getStartPoint() {
        Log.d("StartPoint", startPoint.toString());
        return startPoint;
    }

    /**
     * Calculates the bike's starting point based on the screen width and height.
     *
     * @param w The width of the screen.
     * @param h The height of the screen.
     */
    private VectorF calcStartPoint(int w, int h) {
        return new VectorF(5, 40);
    }

    private float calcGroundHeight() {
        return 410.0f; // TODO
    }

    // <editor-fold desc="Collision detection">

    /**
     * Returns the distance to the nearest solid object that the VectorF could
     * collide with (or is currently colliding with).
     */
    public float getNearestSolid(VectorF point) {
        // TODO: Make this more efficient.
        // Go through each collision rectangle and check if it's the closest rectangle.
        float closest = -1;
        for (Intersector r : intersectors) {
            float temp = r.distanceSquared(point);
            if (closest > temp || closest == -1) {
                closest = temp;
            }
        }
        if (closest == -1) {
            throw new RuntimeException("Could not get nearest solid.");
        }
        return (float)Math.sqrt(closest);
    }

    public boolean intersectsGround(Circle c) {
        // TODO: Make this more efficient.
        for (Intersector r : intersectors) {
            if (c.collidesWith(r)) {
                return true;
            }
        }
        return false;
    }

    // </editor-fold>

}
