package com.github.yannicklamprecht.worldborder.testplugin;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.yannicklamprecht.worldborder.api.*;
import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

@CommandAlias("worldborderapitest|wbat")
@Description("Main command for Worldborder test")
public class BorderTestCommands extends BaseCommand {

    private final WorldBorderApi worldBorderApi;
    private final JavaPlugin javaPlugin;
    private final Gson gson = new Gson();


    public BorderTestCommands(WorldBorderApi worldBorderApi, JavaPlugin javaPlugin) {
        this.worldBorderApi = worldBorderApi;
        this.javaPlugin = javaPlugin;
    }

    @Subcommand("redscreen")
    public void redScreen(Player player, @Default("30") int time) {
        this.worldBorderApi.sendRedScreenForSeconds(player, time, javaPlugin);
    }

    @Subcommand("reset")
    public void reset(Player player) {
        this.worldBorderApi.resetWorldBorderToGlobal(player);
    }

    @Subcommand("set")
    public void set(Player player, @Default("30") int radius, @Default("60") int time) {
        this.worldBorderApi.setBorder(player, radius, time);
    }

    @Subcommand("set")
    public void set(Player player, @Default("30") int size, @Optional Double x, @Optional Double y, @Optional Double z) {
        Location location = player.getLocation();
        if(x != null && y != null && z != null){
            location = new Location(location.getWorld(), x, y, z);
        }
        this.worldBorderApi.setBorder(player, size, location);
    }



    @Subcommand("green")
    public void green(Player player) {
        IWorldBorder iWorldBorder = this.worldBorderApi.getWorldBorder(player);
        iWorldBorder.lerp(20, 22, TimeUnit.SECONDS.toMillis(10));
        iWorldBorder.setCenter(Position.of(player.getLocation()));
        iWorldBorder.send(player, WorldBorderAction.LERP_SIZE);
    }

    @Subcommand("red")
    public void red(Player player) {
        IWorldBorder iWorldBorder = this.worldBorderApi.getWorldBorder(player);
        iWorldBorder.lerp(22, 20, TimeUnit.SECONDS.toMillis(10));
        iWorldBorder.setCenter(Position.of(player.getLocation()));
        iWorldBorder.send(player, WorldBorderAction.LERP_SIZE);
    }

    @Subcommand("lerp")
    public void lerp(Player player, int oldSize, int newSize, @Default("10") int timeInSeconds) {
        IWorldBorder iWorldBorder = this.worldBorderApi.getWorldBorder(player);
        iWorldBorder.lerp(oldSize, newSize, TimeUnit.SECONDS.toMillis(timeInSeconds));
        iWorldBorder.setCenter(Position.of(player.getLocation()));
        iWorldBorder.send(player, WorldBorderAction.LERP_SIZE);
    }

    @Subcommand("print")
    public void lerp(Player player) {
        if (worldBorderApi instanceof PersistentWorldBorderApi) {
            WorldBorderData worldBorderData = ((PersistentWorldBorderApi) worldBorderApi).getWorldBorderData(player);
            player.sendMessage(gson.toJson(worldBorderData));
        }
    }

}
