package com.minestopix.javaPanels;

import com.minestopix.tilegame.GameRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by MinestoPix on 2/11/2015.
 */
public class Main extends Canvas implements Runnable{



    private static final long serialVersionUID = 1L;

    public static final int HEIGHT = 720;
    public static final int WIDTH = HEIGHT / 9 * 16;
    public static final String NAME = "Game";
    public static int maxFps = 0;

    private Thread animator;
    private JFrame frame;

    private Hanoi hanoi;

    public volatile boolean running = false;

    public Main(){
        setBackground(Color.white);
        setMaximumSize(new Dimension(WIDTH - 10, HEIGHT - 10));
        setMinimumSize(new Dimension(WIDTH - 10, HEIGHT - 10));
        setPreferredSize(new Dimension(WIDTH - 10, HEIGHT - 10));


        //Create game components

        frame = new JFrame(NAME);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        hanoi = new Hanoi();

        MouseListener mouseListener = new MouseListener();

        addMouseListener(mouseListener);
    }

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;
        double nsPerFrame = 1000000000D/maxFps;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        double deltaFps = 0;

        running = true;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            if (maxFps != 0) deltaFps += (now - lastTime) / nsPerFrame;
            lastTime = now;

            while (delta >= 1){
                ticks++;
                tick();
                delta -= 1;
            }
            if (maxFps == 0){
                frames++;
                render();
            }else{
                if (deltaFps >= 1){
                    frames++;
                    render();
                    deltaFps -= 1;
                }
            }

            if(System.currentTimeMillis() - lastTimer > 1000){
                lastTimer += 1000;
                frame.setTitle(NAME + " - " + ticks + " ups | " + frames + " fps");
                frames = 0;
                ticks = 0;
            }
        }
        System.exit(0);
    }

    private int sequenceCount = 0;

    public void tick(){



        if(sequenceCount>120)
            hanoi.tick();
        else
            sequenceCount++;

    }


    private Graphics2D dbg2d;
    private Image dbImage = null;
    private GameRenderer gameRenderer;

    public void render(){

        if (dbImage == null){
            dbImage = createImage(WIDTH, HEIGHT);
            if (dbImage == null){
                System.out.println("dbImage is null");
                return;
            }else {
                dbg2d = (Graphics2D) dbImage.getGraphics();
            }
        }

        dbg2d.setColor(Color.white);
        dbg2d.fillRect(0, 0, WIDTH, HEIGHT);

        hanoi.render(dbg2d);

        paintScreen();

    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)){
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }catch (Exception e){
            System.out.println("Graphics context error: " + e);
        }
    }

    public static void main(String[] args){
        new Main().start();
    }

    public synchronized void start() {
        if(animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }
    public synchronized void stop() {
        running = false;
    }


}
