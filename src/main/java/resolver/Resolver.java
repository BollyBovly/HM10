package resolver;

import model.Player;
import model.Position;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class Resolver implements IResolver {
    private final List<Player> players;

    public Resolver(List<Player> players) {
        this.players = players;
    }

    @Override
    public List<Player> getPlayers() {
        return List.of();
    }

    @Override
    public int getCountWithoutAgency() {
        return (int) players.stream()
                .filter(player -> player.agency() == null || player.agency().isEmpty())
                .count();
    }

    @Override
    public int getMaxDefenderGoalsCount() {
        return players.stream()
                .filter(player -> player.position() == Position.DEFENDER)
                .mapToInt(Player::goals)
                .max()
                .orElse(0);
    }


    // нужно вывести русское название позиции самого дорогого немецкого игрока
    @Override
    public String getTheExpensiveGermanPlayerPosition() {
        return players.stream()
                .filter(player -> player.country().equals("Germany"))
                .max(Comparator.comparing(Player::cost))
                .map(Player::position)
                .map(Position::translateToRussian)
                .orElse("Player didn't find");
    }

    // надо вернуть имена игроков, сгруппированных по позициям, на которых они играют
    @Override
    public Map<String, String> getPlayersByPosition() {
        return players.stream()
                .collect(Collectors.groupingBy(
                        player -> player.position().translateToRussian(),
                        Collectors.mapping(Player::name, Collectors.joining(", "))
                ));
    }

    @Override
    public Set<String> getTeams() {
        return players.stream()
                .map(Player::agency)
                .collect(Collectors.toSet());
    }

    // надо вернуть топ-5 команд по количеству забитых мячей, и количество этих мячей.
    @Override
    public Map<String, Integer> getTop5TeamsByGoalsCount() {
        return players.stream()
                .collect(Collectors.groupingBy(
                        Player::team,
                        Collectors.summingInt(Player::goals)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

    }

    // надо вернуть агентство, сумма игроков которого наименьшая
    @Override
    public String getAgencyWithMinPlayersCount() {
        return players.stream()
                .collect(Collectors.groupingBy(
                        Player::agency,
                        Collectors.counting()))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Agency didn't find");
    }

    // надо вернуть команду с наибольшим средним числом удалений на одного игрока.
    @Override
    public String getTheRudestTeam() {
        return players.stream()
                .collect(Collectors.groupingBy(
                        Player::team,
                        Collectors.averagingInt(Player::redCards)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("team didn't find");
    }

    // Вариант 3 Продемонстрируйте зависимость количества забитых голов от трансферной стоимости для нападающих. (line chart)
    public void showForward() {
        List<Player> forwards = players.stream()
                .filter(player -> player.position().equals(Position.FORWARD))
                .filter(player -> {
                    try {
                        Long.parseLong(player.cost());
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());

        XYSeries series = new XYSeries("Forwards");

        for (Player forward : forwards) {
            series.add(Long.parseLong(forward.cost()), forward.goals());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Зависимость голов от стоимости (Нападающие)",
                "Трансферная стоимость",
                "Количество голов",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("График");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

}
