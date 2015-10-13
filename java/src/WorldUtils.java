/*
 * WorldUtils.java
 *
 * created on: 10/13/15 10:51 AM
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

/**
 * TODO - add class description here
 *
 * @author [Frank Magistrali] - fmagistrali@thetus.com
 */
public class WorldUtils {

    public static boolean trueOrFalse(int probability) {
        return 100*Math.random() >= 100 - probability;
    }
}
