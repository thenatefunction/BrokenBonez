package com.dragonfruitstudios.brokenbonez;

import com.dragonfruitstudios.brokenbonez.Math.Collisions.Circle;
import com.dragonfruitstudios.brokenbonez.Math.Collisions.Line;
import com.dragonfruitstudios.brokenbonez.Math.Collisions.Manifold;
import com.dragonfruitstudios.brokenbonez.Math.Collisions.Polygon;
import com.dragonfruitstudios.brokenbonez.Math.Collisions.Rect;
import com.dragonfruitstudios.brokenbonez.Math.Collisions.Triangle;
import com.dragonfruitstudios.brokenbonez.Math.VectorF;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollisionTests {

    @Test
    public void distance_isCorrect() {
        Rect r = new Rect(new VectorF(10, 200), 290, 50);
        Rect r2 = new Rect(new VectorF(0, 410), 3000, 50);

        float distSq = r.distanceSquared(new VectorF(30, 30));
        float distSq2 = r2.distanceSquared(new VectorF(30, 30));

        assertEquals(28900, distSq, 0.0001f);
        assertEquals(144400, distSq2, 0.0001f);
    }

    @Test
    public void lineCollision_isCorrect() {
        Line l = new Line(new VectorF(5, 5), new VectorF(50, 5));
        Line l2 = new Line(new VectorF(5, 5), new VectorF(50, 50));

        assertTrue(l.collidesWith(new VectorF(10, 5)));
        assertFalse(l.collidesWith(new VectorF(10, 6)));
        assertFalse(l.collidesWith(new VectorF(60, 5)));
        assertTrue(l.collidesWith(new VectorF(5, 5)));

        assertTrue(l2.collidesWith(new VectorF(5, 5)));
        assertTrue(l2.collidesWith(new VectorF(50, 50)));
        assertFalse(l2.collidesWith(new VectorF(0, 5)));
    }

    @Test
    public void polygonCollision_isCorrect() {
        Line triangleLeft = new Line(200, 300, 200, 150);
        Line triangleBottom = new Line(200, 300, 300, 300);
        Line triangleMiddle = new Line(200, 150, 300, 300);

        Polygon triangle = new Polygon(new Line[] {triangleLeft, triangleBottom, triangleMiddle});
        assertFalse(triangle.collidesWith(new VectorF(140, 200)));
        assertTrue(triangle.collidesWith(new VectorF(300, 300)));
        assertFalse(triangle.collidesWith(new VectorF(210, 170)));
    }

    @Test
    public void circleCollision_isCorrect() {
        // Ensure that the computed normals are correct.
        Triangle triangle = new Triangle(new VectorF(200, 190), 300, 110);
        Circle wheel = new Circle(336.8f, 218.9f, 20f);
        Manifold.Collection collisionTest = wheel.collisionTest(triangle);
        assertTrue(collisionTest.hasCollisions());
        assertEquals(-0.34425464, collisionTest.get(0).getNormal().getX(), 0.0001);
        assertEquals(0.9388763, collisionTest.get(0).getNormal().getY(), 0.0001);
        assertTrue(collisionTest.get(0).getPenetration() < 0.1);

        wheel.setCenter(719.3f, 196.6f);

        // Just a simple sanity check.
        collisionTest = wheel.collisionTest(triangle);
        assertFalse(collisionTest.hasCollisions());

        triangle = new Triangle(new VectorF(900, 150), -400, 150);
        collisionTest = wheel.collisionTest(triangle);
        assertTrue(collisionTest.hasCollisions());
        assertEquals(0.3511226, collisionTest.get(0).getNormal().getX(), 0.0001);
        assertEquals(0.9363294, collisionTest.get(0).getNormal().getY(), 0.0001);

        wheel.setCenter(625.5f, 231.9f);
        collisionTest = wheel.collisionTest(triangle);
        assertTrue(collisionTest.hasCollisions());
        assertEquals(0.35112345, collisionTest.get(0).getNormal().getX(), 0.0001);
        assertEquals(0.9363292, collisionTest.get(0).getNormal().getY(), 0.0001);

        // Ensure that when the circle is inside the polygon that it can
        // be detected.
        Rect rect = new Rect(new VectorF(0, 0), 1000, 1000);
        wheel.setCenter(500, 500);
        collisionTest = wheel.collisionTest(rect);
        assertTrue(collisionTest.hasCollisions());
        wheel.setCenter(1500, 500);
        collisionTest = wheel.collisionTest(rect);
        assertFalse(collisionTest.hasCollisions());

        // Some test cases taken from game.
        wheel.setCenter(258.7f, 176.0f);
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(new Line(new VectorF(250f, 194f), new VectorF(322, 169)));
        Polygon polygon = new Polygon(lines);
        collisionTest = wheel.collisionTest(polygon);
        assertTrue(collisionTest.hasCollisions());

        wheel.setCenter(190.7f, 177.4f);
        lines = new ArrayList<>();
        lines.add(new Line(new VectorF(177.00f, 192.00f), new VectorF(250.00f, 194.00f)));
        polygon = new Polygon(lines);
        collisionTest = wheel.collisionTest(polygon);
        assertTrue(collisionTest.hasCollisions());
    }


    /*
    @Test
    public void bodySize_isCorrect() {
        // TODO: This currently fails, use mockito to mock RectF
        // https://github.com/soonick/conversion-graph/blob/master/tests/unit/src/com/ncona/conversiongraph/views/BarsViewTest.java#L103
        Rect rect = new Rect(new VectorF(50, 50), 500, 500);
        assertEquals(500, rect.getSize().x, 0.0001);
        assertEquals(500, rect.getSize().y, 0.0001);


        Triangle triangle = new Triangle(new VectorF(0, 0), 300, 150);
        assertEquals(300, triangle.getSize().x, 0.001);
        assertEquals(150, triangle.getSize().y, 0.001);
    }
    */

}
