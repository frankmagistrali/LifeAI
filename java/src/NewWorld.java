/*
 * NewWorld.java
 *
 * created on: 9/18/15 1:33 PM
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

import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;


import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

/**
 * TODO - add class description here
 *
 * @author [Frank Magistrali] - fmagistrali@thetus.com
 */
public class NewWorld extends Frame{

    Color darkGreen  = new Color(0, 100, 0);
    Color lightGreen = new Color(0, 255, 0);
    Color Green      = new Color(0, 175, 0);
    private final int size = 702;
    private final int        worldSizeLength = size / 6;
    private final int        worldSize       = worldSizeLength * worldSizeLength;
    private final WorldSpace VOID_SPACE      = WorldSpace.voidSpace();


    public static void main(String[] args) {
        NewWorld world = new NewWorld();
        world.setVisible(true);
    }

    private void prepareGUI(){
        setSize(size,size);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }
    public void setGrazers(Grazer[] grazers) {
        this.grazers = grazers;
    }

    private Map<Integer, Map<Integer, WorldSpace>> worldSpaceMap;
    private List<WorldSpace> worldSpaces = newArrayList();
    int start = 100;
    private static Grazer[] grazers    = new Grazer[300];
    private static int      numGrazers = 0;


    private void setup() {

        initializeWorld3();

        newPond(xyToIndex(43, 34), 15);
        newPond(xyToIndex(25, 52), 12);
        newPond(xyToIndex(32, 60), 14);
        newPond(xyToIndex(35, 70), 18);
        newPond(xyToIndex(38, 80), 10);
        newPond(xyToIndex(87, 93), 10);

        for (int i = 0; i < 40; i++) {
            int iStart = (int)((worldSizeLength - 1) * Math.random());
            int jStart = (int)((worldSizeLength - 1) * Math.random());
            WorldSpace space = worldSpaces.get(xyToIndex(iStart, jStart));

            if (space.getTerrainType().equals(TerrainType.LAND)) {
                space.setGrass(30);
            }
//            if (land[iStart][jStart] != -1) {
//                land[iStart][jStart] += 30;
//            }
        }
        //
        // Grazer g1=new Grazer(60,58);
        // Grazer g2=new Grazer(60,42);
        // Grazer g3=new Grazer(12,12);
        // Grazer g4=new Grazer(57,48);
        //
        // grazers[0]=g1;
        // grazers[1]=g2;
        // grazers[2]=g3;
        // grazers[3]=g4;
        newGrazer(60, 58);
        newGrazer(60, 58);
        newGrazer(69, 58);
        newGrazer(50, 58);
        newGrazer(60, 48);
        newGrazer(60, 40);
//        setLand(land);
        setGrazers(grazers);
    }

    private void initializeWorld() {
        worldSpaceMap = new HashMap<>();
        for (int i = 0; i < worldSizeLength; i++) {
            worldSpaceMap.put(i, new HashMap<>());
            for (int j = 0; j < worldSizeLength; j++) {
                final WorldSpace newSpace = new WorldSpace(i, j);
                worldSpaceMap.get(i).put(j, newSpace);
                worldSpaces.add(newSpace);
                //TODO: ok, next steps will be to finish this off. need to find a good way to get the neighbor squares
                //TODO: but it's possible I'll just have to force it...
            }
        }

        //TODO: this work, though it's ugly. Also, it doesn't account for the edge squares.
        for (int i = 1; i < worldSizeLength - 1; i+=2) {
            for (int j = 1; j < worldSizeLength - 1; j+=2) {
                WorldSpace space = worldSpaceMap.get(i).get(j);

                WorldSpace upleftNeighbor = worldSpaceMap.get(i-1).get(j-1);
                WorldSpace upNeighbor = worldSpaceMap.get(i).get(j-1);
                WorldSpace upRightNeighbor = worldSpaceMap.get(i+1).get(j-1);
                WorldSpace leftNeighbor = worldSpaceMap.get(i-1).get(j);
                WorldSpace rightNeighbor = worldSpaceMap.get(i+1).get(j);
                WorldSpace downLeftNeighbor = worldSpaceMap.get(i-1).get(j+1);
                WorldSpace downNeighbor = worldSpaceMap.get(i).get(j+1);
                WorldSpace downRightNeighbor = worldSpaceMap.get(i+1).get(j+1);

                space.getNeighborSpaces().add(upleftNeighbor);
                space.getNeighborSpaces().add(upNeighbor);
                space.getNeighborSpaces().add(upRightNeighbor);
                space.getNeighborSpaces().add(leftNeighbor);
                space.getNeighborSpaces().add(rightNeighbor);
                space.getNeighborSpaces().add(downLeftNeighbor);
                space.getNeighborSpaces().add(downNeighbor);
                space.getNeighborSpaces().add(downRightNeighbor);
                final WorldSpace newSpace = new WorldSpace(i, j);
                worldSpaceMap.get(i).put(j, newSpace);
                worldSpaces.add(newSpace);
                //TODO: ok, next steps will be to finish this off. need to find a good way to get the neighbor squares
                //TODO: but it's possible I'll just have to force it...
            }
        }
    }

    private void initializeWorld2() {
        worldSpaceMap = new HashMap<>();
        for (int i = 0; i < worldSizeLength * worldSizeLength; i++) {

            final WorldSpace newSpace = new WorldSpace(i);
            worldSpaces.add(newSpace);
            if (i < worldSizeLength || i > (worldSizeLength * worldSizeLength - worldSizeLength)|| (i % worldSizeLength) == 0 || (i % (worldSizeLength - 1)) == 0 ){
                newSpace.setTerrainType(TerrainType.VOID);
            }
        }
    }

    private void initializeWorld3() {
        IntStream.range(0, worldSize).boxed()
                                    .forEach(i -> {
                                        if (i < worldSizeLength   // top edge
                                                 || i >= ((worldSize - 1) - worldSizeLength)  // bottom edge
                                                 || i % worldSizeLength == 0  // left edge
                                                 || (i+1) % (worldSizeLength ) == 0) {// right edge


                                            worldSpaces.add(VOID_SPACE);
                                        } else {
                                            WorldSpace ws = new WorldSpace(i);
                                            worldSpaces.add(ws);
                                        }
                                    });

    }



    @NotNull
    public String pickADirection() {
        int direction = (int)(8 * Math.random());

        switch (direction) {
            case 0:
                return "n";
            case 1:
                return "ne";
            case 2:
                return "e";
            case 3:
                return "se";
            case 4:
                return "s";
            case 5:
                return "sw";
            case 6:
                return "w";
            case 7:
                return "nw";
            default:
                return "none";
        }
    }


    public void grow() throws IOException {

        final Consumer<WorldSpace> maybeGrowGrass = (s -> {
            if (s.getGrass() > 0) {
                s.increaseGrass();
            }

            if (s.isNearWater()) {
                s.setGrass(1);
                s.setUpdateGraphics(true);
            } else {
                List<WorldSpace> neighbors = getAllNeighbors(s);
                neighbors.stream()
                         .filter(n -> n.getTerrainType().equals(TerrainType.LAND))
                         .max((n1, n2) -> Integer.compare(n1.getGrass(), n2.getGrass()))
                         .ifPresent(biggest -> {
                             if (biggest.getGrass() > 0) {
                                 if (WorldUtils.trueOrFalse(biggest.getGrass())) {
                                     s.setGrass(1);
                                     s.setUpdateGraphics(true);
                                 }

                             }
                         });
            }
        });



        worldSpaces.stream()
                   .filter(s -> s.getTerrainType().equals(TerrainType.LAND) && s.getGrass() < 50)
                   .forEach(s -> maybeGrowGrass.accept(s));


        paint(getGraphics());
    }


//    public void pond(int x, int y, int size) {
//        for (int i = 0; i < worldSizeLength; i++) {
//            for (int j = 0; j < worldSizeLength; j++) {
//                if (distance(i, j, x, y) < size) {
//                    land[i][j] = WATER;
//                }
//            }
//        }
//    }

    private void newPond(int index, int size) {

        // this was a VOID_SPACE, don't do anything with it or it's na
        if (index == -1) {
            return;
        }

        final WorldSpace worldSpace = worldSpaces.get(index);

        // we're past the edge of the pond, so if this space is LAND, it is next to water. Mark it.
        if (size < 0 && worldSpace.getTerrainType().equals(TerrainType.LAND)) {
            worldSpace.setNearWater(true);
            return;
        }

        worldSpace.setTerrainType(TerrainType.WATER);

        List<WorldSpace> neighbors = getAllNeighbors(worldSpace);

        neighbors.stream()
                 .filter(n -> n.getTerrainType().equals(TerrainType.LAND)) // if the space is VOID or WATER, don't go to it.
                 .forEach(n -> newPond(n.getIndex(), size - 1));

    }

    public double distance(int i, int j, int x, int y) {
        return Math.sqrt((i - x) * (i - x) + (j - y) * (j - y));
    }

    public double distance2(int i, int j, int x, int y) {
        return (i - x) * (i - x) + (j - y) * (j - y);
    }

//    public void paint(Graphics g) {
//
//        if (start == 100) {
//            setup();
//            start--;
//        }
//        // Get the drawing area
//        setSize(size, size);
//        // int n = 100;
//        int dY = getSize().height;
//        int dX = getSize().width;
//        int midY = dY / 2;
//        int midX = dX / 2;
//        int x = dX / worldSizeLength;
//        int y = dY / worldSizeLength;
//
//        for (int i = 0; i < worldSizeLength; i++) {
//            for (int j = 0; j < worldSizeLength; j++) {
//                if (land[i][j] > 0) {
//                    grass = new Color(0, 255 - land[i][j] * 3, 0);
//                    g.setColor(grass);
//                } else if (land[i][j] == WATER) {
//                    g.setColor(Color.blue);
//                } else {
//                    g.setColor(Color.yellow);
//
//                }
//                g.fillRect(i * x, j * y, 1 * x, 1 * y);
//            }
//        }
//        if (start == 0) {
//            for (int i = 0; i < numGrazers; i++) {
//                if (grazers[i].alive) {
//                    grazeState.update(grazers[i]);
//                    if (grazers[i].sex == 0) {
//                        g.setColor(Color.CYAN);
//                    } else {
//                        g.setColor(Color.red);
//                    }
//                    g.fillOval(grazers[i].iPos * x, grazers[i].jPos * y, x, y);
//                }
//            }
//            grazerCleanup();
//        } else {
//            start--;
//        }
//        // g.fillOval(0, 0, 100, 100);
//        //
//        // try {
//        // if( System.in.read()!=-2828){
//        // }
//        // } catch (IOException e1) {
//        // // TODO Auto-generated catch block
//        // e1.printStackTrace();
//        // }
//        // System.out.println("waiting");
//
//        // System.out.println("in world: "+land[grazers[0].iPos][grazers[0].jPos]);
//        try {
//            try{
//                //do what you want to do before sleeping
//                Thread.currentThread().sleep(100);//sleep for 1000 ms
//                //do what you want to do after sleeptig
//            }
//            catch(InterruptedException ie){
//                //If this thread was intrrupted by nother thread
//            }
//            land = grow();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//
//
//    }

    @Override
    public void paint(Graphics g) {

        if (start == 100) {
            setup();
            setSize(size, size);
            start--;
        }
        // Get the drawing area
        // int n = 100;
        int dY = getSize().height;
        int dX = getSize().width;
        int midY = dY / 2;
        int midX = dX / 2;
        int x = dX / worldSizeLength;
        int y = dY / worldSizeLength;

        IntStream.range(0, worldSpaces.size()).boxed().forEach(index -> {
            WorldSpace s = worldSpaces.get(index);

            switch (s.getTerrainType()) {
                case LAND:
                    if (s.getGrass() > 0) {
                        g.setColor(new Color(0, 255 - s.getGrass() * 3, 0));
                    } else {
                        g.setColor(Color.yellow);
                    }
                    break;
                case WATER:
                    g.setColor(Color.blue);
                    break;
                default:
                    g.setColor(Color.BLACK);
                    break;

            }
            int i = index % worldSizeLength;
            int j = (index - i) / worldSizeLength;
            g.fillRect(i * x, j * y, x, y);
            s.setUpdateGraphics(false);
        });
//        worldSpaces.stream()
////                   .filter(s -> s.isUpdateGraphics())
//                   .forEach(s -> {
//                       switch (s.getTerrainType()) {
//                           case LAND:
//                               if (s.getGrass() > 0) {
//                                   g.setColor(new Color(0, 255 - s.getGrass() * 3, 0));
//                               } else {
//                                   g.setColor(Color.yellow);
//                               }
//                               break;
//                           case WATER:
//                               g.setColor(Color.blue);
//                               break;
//                           default:
//                               g.setColor(Color.BLACK);
//                               break;
//
//                       }
//                       int i = s.getIndex() % worldSizeLength;
//                       int j = (s.getIndex() - 1) / worldSizeLength;
//                       g.fillRect(i * x, j * y, x, y);
//                       s.setUpdateGraphics(false);
//                   });

        if (start == 0) {
//            for (int i = 0; i < numGrazers; i++) {
//                if (grazers[i].alive) {
//                    grazeState.update(grazers[i]);
//                    if (grazers[i].sex == 0) {
//                        g.setColor(Color.CYAN);
//                    } else {
//                        g.setColor(Color.red);
//                    }
//                    g.fillOval(grazers[i].iPos * x, grazers[i].jPos * y, x, y);
//                }
//            }
//            grazerCleanup();
        } else {
            start--;
        }
        // g.fillOval(0, 0, 100, 100);
        //
        // try {
        // if( System.in.read()!=-2828){
        // }
        // } catch (IOException e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }
        // System.out.println("waiting");

        // System.out.println("in world: "+land[grazers[0].iPos][grazers[0].jPos]);
            try{
                //do what you want to do before sleeping
                Thread.currentThread().sleep(100);//sleep for 1000 ms
                //do what you want to do after sleeptig
            }
            catch(InterruptedException ie){
                //If this thread was intrrupted by nother thread
            }

        try {
            grow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGrazer(int i, int j) {
        if (numGrazers < 300) {
            int sex = (int)(Math.random() * 2);
            Grazer g5 = new Grazer(i, j, sex);
            grazers[numGrazers] = g5;
            numGrazers++;
            System.out.println("breeding!" + " " + numGrazers);
        }

    }

    public void grazerCleanup() {
        for (int i = 0; i < numGrazers; i++) {
            if (!grazers[i].alive) {
                grazers[i] = grazers[numGrazers - 1];
                numGrazers--;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// UTILITY METHODS ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @NotNull
    private WorldSpace getNWNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() - (worldSizeLength + 1));
        }

        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getNNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() - worldSizeLength);
        }
        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getNENeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() - (worldSizeLength - 1));
        }
        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getWNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() - 1);
        }
        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getENeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() + 1);
        }
        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getSWNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() + (worldSizeLength - 1));
        }
        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getSNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() + worldSizeLength);
        }
        return VOID_SPACE;
    }

    @NotNull
    private WorldSpace getSENeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(TerrainType.VOID)) {
            return worldSpaces.get(space.getIndex() + (worldSizeLength + 1));
        }
        return VOID_SPACE;
    }

    @NotNull
    private List<WorldSpace> getAllNeighbors(@NotNull final WorldSpace space) {
        List<WorldSpace> neighbors = newArrayList();
        if (!TerrainType.VOID.equals(space.getTerrainType())){
            neighbors.add(getNWNeighbor(space));
            neighbors.add(getNNeighbor(space));
            neighbors.add(getNENeighbor(space));
            neighbors.add(getWNeighbor(space));
            neighbors.add(getENeighbor(space));
            neighbors.add(getSWNeighbor(space));
            neighbors.add(getSNeighbor(space));
            neighbors.add(getSENeighbor(space));
        }

        return neighbors;
    }

    @Contract(pure = true)
    @NotNull
    private int xyToIndex(int x, int y) {
        return (worldSizeLength * y) + x;
    }



}
