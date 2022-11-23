package net.httpmarco.minestom.tablist;

import net.minestom.server.MinecraftServer;
import net.minestom.server.scoreboard.TeamManager;

import java.util.ArrayList;
import java.util.List;

public class TablistGroup {

    private final TeamManager teamManager = new TeamManager();
    private final List<TablistTeam> tablistTeams = new ArrayList<>();

    public void calculateTeams() {
        this.tablistTeams.forEach(it -> it.toTeam(teamManager));
    }

    public void setTablist() {
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
            tablistTeams.stream().filter(it -> it.getTabPredicate().test(player)).findFirst().ifPresent(tablistTeam -> {
                if (!tablistTeam.getTeam().getMembers().contains(player.getUsername())) {
                    tablistTeam.getTeam().addMember(player.getUsername());
                }
            });
        });
    }

    public void addTeam(TablistTeam team) {
        this.tablistTeams.add(team);
    }

    public void remove() {
        this.tablistTeams.forEach(it -> it.getTeam().getPlayers().forEach(player -> it.getTeam().removeMember(player.getUsername())));
    }
}
