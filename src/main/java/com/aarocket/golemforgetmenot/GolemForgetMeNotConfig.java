package com.aarocket.golemforgetmenot;

public class GolemForgetMeNotConfig {
    private static int visitsUntilCooldown = 24;
    private static int heightReachIncrease = 1;

    public static int getVisitsUntilCooldown() {
        return visitsUntilCooldown;
    }

    public static void setVisitsUntilCooldown(int value) {
        visitsUntilCooldown = value;
    }

    public static int getHeightReachIncrease() {
        return heightReachIncrease;
    }

    public static void setHeightReachIncrease(int value) {
        if (value < 0) value = 0;
        if (value > 2) GolemForgetMeNot.LOGGER.warn("A copper golem reach height greater than 4 is likely to break!");
        heightReachIncrease = value;
    }
}
