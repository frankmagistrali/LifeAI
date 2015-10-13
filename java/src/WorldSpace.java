/*
 * WorldSpace.java
 *
 * created on: 9/18/15 1:25 PM
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
import lombok.Data;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.function.Consumer;

/**
 * TODO - add class description here
 *
 * @author [Frank Magistrali] - fmagistrali@thetus.com
 */
@Data
public class WorldSpace {
    private int     i;
    private int     j;
    private TerrainType terrainType = TerrainType.LAND;
    private boolean nearWater = false;
    private int     grass;
    private ICritter critter = null;
    private List<WorldSpace> neighborSpaces = newArrayList();
    private boolean updateGraphics = true;
    private int index;

    public WorldSpace(int iIndex, int jIndex) {
        i = iIndex;
        j = jIndex;
    }

    public WorldSpace(int index) {
        this.index = index;
    }

    public static WorldSpace voidSpace() {
        WorldSpace voidSpace = new WorldSpace(-1);
        voidSpace.setTerrainType(TerrainType.VOID);
        return voidSpace;
    }

    public void increaseGrass() {
        if (isNearWater()) {
            setGrass(getGrass() + 5);
            setUpdateGraphics(true);
        } else {
            setGrass(getGrass() + 1);
        }
    };

//    public void maybeGrowGrass() {
//        if (isNearWater()) {
//            setGrass(1);
//            setUpdateGraphics(true);
//        } else {
//            List<WorldSpace> neighbors = getAllNeighbors(s);
//            neighbors.stream()
//                     .filter(n -> n.getTerrainType().equals(TerrainType.LAND))
//                     .max((n1, n2) -> Integer.compare(n1.getGrass(), n2.getGrass()))
//                     .ifPresent(biggest -> {
//                         if (biggest.getGrass() > 0) {
//                             if (trueOrFalse(biggest.getGrass())) {
//                                 s.setGrass(1);
//                                 s.setUpdateGraphics(true);
//                             }
//
//                         }
//                     });
//        }
//    };
}
