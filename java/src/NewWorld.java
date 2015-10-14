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
import lombok.Getter;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.google.common.collect.Lists.newArrayList;
import static src.TerrainType.LAND;
import static src.TerrainType.VOID;

/**
 * TODO - add class description here
 *
 * @author [Frank Magistrali] - fmagistrali@thetus.com
 */
public class NewWorld {

    private final int        worldSizeLength;
    private final int        worldSize;
    private final WorldSpace NULL_SPACE = WorldSpace.nullSpace();
    private final int size;

    @Getter
    private List<WorldSpace> worldSpaces = newArrayList();
    int start = 100;
    @Getter
    private List<Grazer> grazers    = newArrayList();

    GrazerState grazeState = new GrazerState();



    public NewWorld(final int size, final int worldSizeLength, final int worldSize) {
        this.size = size;
        this.worldSizeLength = worldSizeLength;
        this.worldSize = worldSize;
    }

    public void setup() {

        initializeWorld3();

        //TODO: HERE IS WHERE I WAS!! there's something funky going on here, I was trying to figure out why the ponds were uneven, it's
        //TODO: and issue with the recursion I believe.
        newPond(xyToIndex(43, 34), 3);
//        newPond(xyToIndex(25, 52), 12);
//        newPond(xyToIndex(32, 60), 14);
//        newPond(xyToIndex(35, 70), 18);
//        newPond(xyToIndex(38, 80), 10);
//        newPond(xyToIndex(87, 93), 10);

        for (int i = 0; i < 40; i++) {
            int iStart = (int)((worldSizeLength - 1) * Math.random());
            int jStart = (int)((worldSizeLength - 1) * Math.random());
            WorldSpace space = worldSpaces.get(xyToIndex(iStart, jStart));

            if (space.getTerrainType().equals(TerrainType.LAND)) {
                space.setGrass(30);
            }
        }
        newGrazer(xyToIndex(60, 58));
        newGrazer(xyToIndex(60, 58));
        newGrazer(xyToIndex(69, 58));
        newGrazer(xyToIndex(50, 58));
        newGrazer(xyToIndex(60, 48));
        newGrazer(xyToIndex(60, 40));

    }

    private void initializeWorld3() {
        IntStream.range(0, worldSize).boxed()
                                    .forEach(i -> {
                                        int x = i % worldSizeLength;
                                        int y = (i - x) / worldSizeLength;
                                        WorldSpace ws = new WorldSpace(i, x, y);

                                        if (i < worldSizeLength   // top edge
                                                 || i >= ((worldSize - 1) - worldSizeLength)  // bottom edge
                                                 || i % worldSizeLength == 0  // left edge
                                                 || (i+1) % (worldSizeLength ) == 0) {// right edge


                                            ws.setTerrainType(VOID);
                                        } else {
                                            ws.setTerrainType(LAND);
                                        }

                                        worldSpaces.add(ws);
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

    public void grow() {

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


    }

    private void newPond(int index, int size) {

        // this was a NULL_SPACE, don't do anything with it or it's na
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



    public void newGrazer(int index) {
        int sex = (int)(Math.random() * 2);
        Grazer g5 = new Grazer(worldSpaces.get(index), sex);
        worldSpaces.get(index).setContainsCritter(true);

        grazers.add(g5);
        System.out.println("breeding!" + " " + grazers.size());

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// UTILITY METHODS ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @NotNull
    private WorldSpace getNWNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() - (worldSizeLength + 1));
        }

        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getNNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() - worldSizeLength);
        }
        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getNENeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() - (worldSizeLength - 1));
        }
        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getWNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() - 1);
        }
        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getENeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() + 1);
        }
        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getSWNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() + (worldSizeLength - 1));
        }
        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getSNeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() + worldSizeLength);
        }
        return NULL_SPACE;
    }

    @NotNull
    private WorldSpace getSENeighbor(@NotNull final WorldSpace space) {
        if (!space.getTerrainType().equals(VOID)) {
            return worldSpaces.get(space.getIndex() + (worldSizeLength + 1));
        }
        return NULL_SPACE;
    }

    @NotNull
    private List<WorldSpace> getAllNeighbors(@NotNull final WorldSpace space) {
        List<WorldSpace> neighbors = newArrayList();
        if (!VOID.equals(space.getTerrainType())){
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
