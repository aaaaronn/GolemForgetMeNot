package com.aarocket.golemforgetmenot;

public class GolemForgetMeNotConfig {
    private static int visitsUntilCooldown = 24;
    private static boolean completeStacks = true;

    public static int getVisitsUntilCooldown() {
        return visitsUntilCooldown;
    }

    public static void setVisitsUntilCooldown(int value) {
        visitsUntilCooldown = value;
    }

    public static boolean getCompleteStacks() {
        return completeStacks;
    }

    public static void setCompleteStacks(boolean value) {
        completeStacks = value;
    }

}
