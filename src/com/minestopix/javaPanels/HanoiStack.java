package com.minestopix.javaPanels;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MinestoPix on 2/23/2015.
 */
public class HanoiStack {

    private List<HanoiBar> bars = new LinkedList<HanoiBar>();
    int maxWidth = 420;

    private int offset;
    private int id;

    public HanoiStack(boolean populated, int offset, int id, int nbars){
        this.offset = offset;
        this.id = id;
        if(populated){
            for(int i = 0; i < nbars; i++){
                bars.add(new HanoiBar(maxWidth - (i * (maxWidth / nbars))));
            }
        }
    }

    public HanoiStack(boolean populated, int offset, int id){
        this.offset = offset;
        this.id = id;
        if(populated){
            for(int i = 0; i < 4; i++){
                bars.add(new HanoiBar(maxWidth - (i * (maxWidth / 4))));
            }
        }
    }

    public int getOffset() {
        return offset;
    }

    public void render(Graphics2D g){
        g.setColor(Color.orange);
//        g.fillRect(offset - (25), 60, 50, 500);
        g.fillRect(offset - (5), 60, 10, 500);

        int i = 0;
        for (HanoiBar h : bars){
            h.render(g, offset, i);
            i++;
        }
    }

    public int getTopWidth(){
        if (bars.size() < 1)
            return -1;
        else
            return bars.get(bars.size()-1).getWidth();
    }

    public HanoiBar getTop(){
        if (bars.size()<1)
            return null;
        else
            return bars.get(bars.size()-1);
    }

    public int getWidth(int i){
        return bars.get(i).getWidth();
    }


    public int getHeight(){
        return bars.size();
    }

    public HanoiBar cutTop(){
        HanoiBar h = bars.get(bars.size()-1);
        bars.remove(bars.size()-1);
        return h;
    }

    public void addTop(HanoiBar h){
        bars.add(h);
    }

    public void tick() {
        for(HanoiBar h : bars){
            h.tick();
        }
    }

    public String toString(){
        return "HanoiStack with id <" + id +">" ;
    }
}
