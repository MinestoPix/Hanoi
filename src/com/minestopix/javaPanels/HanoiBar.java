package com.minestopix.javaPanels;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by MinestoPix on 2/23/2015.
 */
public class HanoiBar {

    private int width;
    private int animX = 0;
    private double animYPercent = 1;
    private HanoiStack start;
    private HanoiStack dest;
    private boolean destReached = false;
    private boolean yReached = false;
    private boolean xReached = false;
    private Random random = new Random();

    public HanoiBar(int width){

        this.width = width;


    }

    public void render(Graphics2D g, int xOffset, int yOffset) {

        random.setSeed(width*1000);
        g.setColor(Color.getHSBColor(random.nextFloat(), (float) (width) / 420f, 0.9f - (float) (width) / 600f ));
        int yPosition = (int)(animYPercent * (495 - (yOffset * 55))) + 5;
        g.fillRect(xOffset - (width / 2) + animX, yPosition, width, 50);

    }

    public int getWidth() {
        return width;
    }

    public void setDest(HanoiStack start, HanoiStack dest) {
        this.start = start;
        this.dest = dest;
    }

    private double exponential = 0D;
    private double speedE = 0.005;
    private double speedP = 0.05;
    private int speedI = 20;

    public void tick() {
        speedE = 0.05;
        speedP = 0.5;
        speedI = 100;
        if(dest != null && !destReached){
            // animate
            if(!yReached){
                animYPercent -= speedP;
                if(animYPercent <= speedP) {
                    animYPercent = 0;
                    yReached = true;
                }
            }else if(!xReached){
                if (animX < ( dest.getOffset() - start.getOffset() ))
                    if (animX < ( dest.getOffset() - start.getOffset() - speedI ))
                        animX += speedI;
                    else
                        animX = dest.getOffset() - start.getOffset();
                else if (animX > (  dest.getOffset() - start.getOffset() ))
                    if (animX > ( dest.getOffset() - start.getOffset() + speedI ))
                        animX -= speedI;
                    else
                        animX = dest.getOffset() - start.getOffset();
                else {
                    xReached = true;
                    animX = 0;
                    dest.addTop(start.cutTop());
                }

            }else if(animYPercent < 1){
                animYPercent += exponential;
                exponential += speedE;
                if(animYPercent >= 0.95) {
                    exponential = 0D;
                    animYPercent = 1;
                    dest = null;
                    xReached = false;
                    yReached = false;
                    Hanoi.completed();
                }
            }


        }
    }
}
