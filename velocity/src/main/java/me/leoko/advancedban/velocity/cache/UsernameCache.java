package me.leoko.advancedban.velocity.cache;

import com.google.common.collect.Lists;

import java.util.List;

public class UsernameCache {

    private static final List<String> PLAYERS = Lists.newArrayList();

    public static void add(String player) {
        PLAYERS.add(player.toLowerCase());
    }

    public static void remove(String player) {
        PLAYERS.remove(player.toLowerCase());
    }

    public static boolean contains(String player) {
        return PLAYERS.contains(player);
    }

}
