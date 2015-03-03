package com.minestopix.javaPanels;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MinestoPix on 2/23/2015.
 */
public class Hanoi {

    private static HanoiStack[] stacks;
    private List<HanoiStack> queuedFrom = new LinkedList<HanoiStack>();
    private List<HanoiStack> queuedTo = new LinkedList<HanoiStack>();

    static int selectedN = 0;
    static HanoiStack selectedH = null;
    static HanoiStack selectedT = null;

    private static boolean animating = false;

    public Hanoi(int nStacks) {
        stacks = new HanoiStack[nStacks];
        for (int i = 0; i < nStacks; i++) {
            stacks[i] = new HanoiStack(i==0, i* ( Main.WIDTH/nStacks ) + ( Main.WIDTH/(2*nStacks) ) , i );
        }
    }

    public void render(Graphics2D g){

        // TODO rendering of select box


       for(HanoiStack h : stacks){
           h.render(g);
       }

        int feather = 10;
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));
        if (selectedN > 0) {
            int[] xPix = { selectedH.getOffset() - (selectedH.getTopWidth() / 2) - feather,
                    selectedH.getOffset() + (selectedH.getTopWidth() / 2) + feather,
                    selectedH.getOffset() + (selectedH.getWidth((selectedH.getHeight() - selectedN)) / 2) + feather * 3,
                    selectedH.getOffset() - (selectedH.getWidth((selectedH.getHeight() - selectedN)) / 2) - feather * 3,
                    selectedH.getOffset() - (selectedH.getTopWidth() / 2) - feather
            };
            int[] yPix = { 555 - (selectedH.getHeight() * 55) - feather,
                    555 - (selectedH.getHeight() * 55) - feather,
                    555 - ((selectedH.getHeight() - selectedN) * 55),
                    555 - ((selectedH.getHeight() - selectedN) * 55),
                    555 - (selectedH.getHeight() * 55) - feather
            };
            g.drawPolygon(xPix, yPix, 5);
        }
    }

    public void moveQueue(int i, HanoiStack from, HanoiStack to){
        for (HanoiStack h : stacks) {
            if (!h.equals(from) && !h.equals(to)) {
                moveQueue(i, from, to, h);
                return;
            }
        }
    }

        public void moveQueue(int i, HanoiStack from, HanoiStack to, HanoiStack help){

        if (i==1){
            moveQueue(from, to);
        } else {
            moveQueue(i-1, from, help, to);
            moveQueue(from, to);
            moveQueue(i-1, help, to, from);
        }
    }

    public void moveQueue(HanoiStack from, HanoiStack to){
        queuedFrom.add(from);
        queuedTo.add(to);
    }

    public void move(int i, HanoiStack from, HanoiStack to, HanoiStack help){

        if (i==1){
            move(from, to);
        } else {
            move(i - 1, from, help, to);
            move(from, to);
            move(i - 1, help, to, from);
        }
    }

    public void move(HanoiStack from, HanoiStack to){
        if(from.getTopWidth() < to.getTopWidth() || to.getTopWidth() == -1) {
            if (from.getTop() != null) {
                from.getTop().setDest(from, to);
//                to.addTop(from.cutTop());
//                completed();
            }
            else
                completed();
        } else {
            completed();
        }
    }

    public static void completed(){
        animating = false;
    }

    public void tick() {

        if (selectedT != null){
            moveQueue(selectedN, selectedH, selectedT);
            selectedN = 0;
            selectedH = null;
            selectedT = null;
        }

        if (queuedFrom.size() > 0 && !animating) {
            animating = true;
            move(queuedFrom.get(0), queuedTo.get(0));
            queuedFrom.remove(0);
            queuedTo.remove(0);
        }

        for(HanoiStack s : stacks){
            s.tick();
        }
    }

    static void clicked(int x, int y){
        for (HanoiStack s : stacks){
            if (x >= s.getOffset() - (s.maxWidth / 2) && x <= s.getOffset() + (s.maxWidth / 2) && !animating){
                if ( !s.equals(selectedH) && selectedN > 0){
                    selectedT = s;
                    return;
                } else {
                    selectedH = s;
                }
                if (y >= 555 - ( s.getHeight() * 55 ) && y <= 555){
                    selectedN = s.getHeight() - ((555 - y) / 55);
                } else {
                    selectedN = 0;
                }
            }
        }
    }
}
