/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.musante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author markm
 */
public class Bouncer extends JPanel {
    
    private List<Ball> balls = new ArrayList<>();
    Thread runner;
    
    public Bouncer() {
        for (int i = 0; i < 26; i++) {
            balls.add(new Ball(200 + 500 * i / 26, 300, i * Math.PI / 26.0, 2.0, 
                    (char) ('A' + i)));
        }

        runner = new Thread() {
            @Override
            public void run() {
                BufferedImage image = new BufferedImage(800, 600, 1);
                Graphics2D g = (Graphics2D) image.getGraphics();
                Graphics2D screen = (Graphics2D) getGraphics();
                
                long nextFrame = System.nanoTime();
                while (true) {
                    do {
                        nextFrame += 16666667;
                        updatePositions();
                    } while (nextFrame < System.nanoTime());
                    
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, 800, 600);
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setColor(Color.BLACK);
                    for (Ball b : balls) {
                        b.draw(g);
                    }
                    
                    screen.drawImage(image, 0, 0, null);
                    while (nextFrame - System.nanoTime() > 0) {
                        Thread.yield();
                    }
                }
            }
        };
    }
    
    public void updatePositions() {
        for (Ball b : balls) {
            b.move(800, 600);
        }
        for (Ball b : balls) {
            /*
            boolean colliding = true;
            
            while (colliding) {
                boolean found = false;
                colliding = false;
                for (Ball bb : balls) {
                    if (b == bb) {
                        found = true;
                        continue;
                    } else if (!found) {
                        continue;
                    }
                    colliding = colliding || b.collide(bb);
                }
            }
            */
            for (Ball bb : balls) { b.collide(bb); }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Bouncer b = new Bouncer();
        frame.add(b, BorderLayout.CENTER);
        frame.setVisible(true);
        b.start();
        //b.updatePositions();
        //b.showPositions();
    }

    private void start() {
        runner.start();
    }

    private void showPositions() {
        for (Ball b : balls) {
            b.log();
        }
    }
}
