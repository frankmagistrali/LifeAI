/*
 * WorldViewComponent.java
 *
 * created on: 10/14/15 8:35 AM
 * Copyright(c) 2002-2015 Thetus Corporation.  All Rights Reserved.
 *                        www.thetus.com
 *
 * Use of copyright notice does not imply publication or disclosure.
 * THIS SOFTWARE CONTAINS CONFIDENTIAL AND PROPRIETARY INFORMATION CONSTITUTING VALUABLE TRADE SECRETS
 *  OF THETUS CORPORATION, AND MAY NOT BE:
 *  (a) DISCLOSED TO THIRD PARTIES;
 *  (b) COPIED IN ANY FORM;
 *  (c) USED FOR ANY PURPOSE EXCEPT AS SPECIFICALLY PERMITTED IN WRITING BY THETUS CORPORATION.
 */
package src;

import apis.ICritter;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.List;


/**
 * TODO - add class description here
 *
 * @author [Frank Magistrali] - fmagistrali@thetus.com
 */
public class WorldViewComponent extends JPanel implements ActionListener {

    @Getter
    private Timer timer;
    private final int DELAY = 150;
    private NewWorld world;
    int start = 100; //TODO: get rid of this shit!


    private final int size = 702;
    private final int        worldSizeLength = size / 6;
    private final int        worldSize       = worldSizeLength * worldSizeLength;

    public WorldViewComponent() {
        world = new NewWorld(size, worldSizeLength, worldSize);
        world.setup();

        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        world.grow();
        repaint();

    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //TODO: these x's and y's are really confusing, clean up the naming to make them make sense
        int dY = getSize().height;
        int dX = getSize().width;
        int x = dX / worldSizeLength;
        int y = dY / worldSizeLength;

        List<WorldSpace> worldSpaces = world.getWorldSpaces();
        IntStream.range(0, worldSpaces.size()).boxed().forEach(index -> {
            WorldSpace s = worldSpaces.get(index);

            switch (s.getTerrainType()) {
                case LAND:
                    if (s.getGrass() > 0) {
                        g2d.setPaint(new Color(0, 255 - s.getGrass() * 3, 0));
                    } else {
                        g2d.setPaint(Color.yellow);
                    }
                    break;
                case WATER:
                    g2d.setPaint(Color.blue);
                    break;
                default:
                    g2d.setPaint(Color.BLACK);
                    break;

            }
//            int i = index % worldSizeLength;
//            int j = (index - i) / worldSizeLength;
            g.fillRect(s.getDrawX() * x, s.getDrawY() * y, x, y);
//            if (s.getCritter() != null) {
//                ICritter grazer = s.getCritter();
//                g2d.setPaint(grazer.getSex() == 0 ? Color.CYAN : Color.RED);
//                g.fillOval(i*x, j*x, x, y);
//            }
            s.setUpdateGraphics(false);
        });

        world.getGrazers().stream().forEach(grazer -> {
            g2d.setPaint(grazer.getSex() == 0 ? Color.CYAN : Color.RED);
            g.fillOval(grazer.getCurrentSpace().getDrawX()*x, grazer.getCurrentSpace().getDrawY()*x, x, y);

        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }
}
