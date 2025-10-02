package com.aarocket.golemforgetmenot;

public class GolemForgetMeNotConfig {
    private static int visitsUntilCooldown = 24;

    public static int getVisitsUntilCooldown() {
        return visitsUntilCooldown;
    }

    public static void setVisitsUntilCooldown(int value) {
        visitsUntilCooldown = value;
    }
}
