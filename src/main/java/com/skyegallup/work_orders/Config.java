package com.skyegallup.work_orders;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig
{
    @Entry(min = 0, max = 5) public static int minVillagerLevel = 2;
    @Comment public static Comment minVillagerLevelExplanation;

    @Entry(isSlider = true, min = 0, max = 1) public static float startChance = 0.25f;
    @Entry(min = 1) public static int durationInGameDays = 2;
    @Entry(min = 0) public static int rewardExp = 50;
}
