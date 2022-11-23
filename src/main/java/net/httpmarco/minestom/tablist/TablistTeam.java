package net.httpmarco.minestom.tablist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.scoreboard.TeamBuilder;
import net.minestom.server.scoreboard.TeamManager;

import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public class TablistTeam {

    private final String name;
    private final int id;
    private final String prefix;
    private final NamedTextColor namedTextColor;
    private final Predicate<Player> tabPredicate;
    private Team team;

    public void toTeam(TeamManager teamManager) {
        this.team = new TeamBuilder(name, teamManager).updateTeamColor(namedTextColor).updatePrefix(Component.text(prefix)).build();
    }

}
