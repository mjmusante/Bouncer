/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.musante;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author markm
 */
public class Ball {
    
    Point point;
    double real_x;
    double real_y;
    double angle;
    double speed;
    char letter;
    
    public Ball(int x, int y, double angle, double speed, char letter) {
        point = new Point(x, y);
        real_x = x;
        real_y = y;
        this.angle = angle;
        this.speed = speed;
        this.letter = letter;
    }
    
    public void move(int width, int height) {
        double new_x = real_x + Math.cos(angle) * speed;
        double new_y = real_y + Math.sin(angle) * speed;
        double mul = -1;
        
        if (new_x < 10) {
            mul = 1;
        } else if (new_x + 40 > width) {
            mul = 3;
        }
        if (new_y < 10) {
            mul = 2;
        } else if (new_y + 40 > height) {
            mul = 0;
        }
        
        if (mul >= 0) {
            angle = mul * Math.PI - angle;
            new_x = real_x + Math.cos(angle) * speed;
            new_y = real_y + Math.sin(angle) * speed;
        }
        
        point.x = (int) (real_x = new_x);
        point.y = (int) (real_y = new_y);
    }

    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(point.x - 10, point.y - 10, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString("" + letter, point.x - 4, point.y + 5);
    }

    void log() {
        System.out.println("Ball(" + real_x + ", " + real_y + ", " +
                angle + ", " + speed + ")");
    }
    
    boolean overlaps(Ball b) {
        double xval = real_x - b.real_x;
        double yval = real_y - b.real_y;
        double distance = Math.sqrt(xval * xval + yval * yval);

        return distance <= 20.001;
    }
    boolean collide(Ball b) {
        if (overlaps(b)) {
            double s = speed;
            speed = b.speed;
            b.speed = s;
            
            double a = angle;
            angle = b.angle;
            b.angle = a;
            move(800, 600);
            return true;
        }
        return false;
    }
    
}
