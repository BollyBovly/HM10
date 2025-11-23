import parser.CsvParser;
import resolver.Resolver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var players = CsvParser.parseCsvToList("C:/Users/stepa/IdeaProjects/HM10/fakePlayers.csv");
        System.out.println(players);

        Resolver resolver = new Resolver(players);
        System.out.println(resolver.getTop5TeamsByGoalsCount());
        System.out.println(resolver.getAgencyWithMinPlayersCount());
        System.out.println(resolver.getTheRudestTeam());
    }
}
