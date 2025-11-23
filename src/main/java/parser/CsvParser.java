package parser;

import model.Player;
import model.Position;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CsvParser {
    public static List<Player> parseCsvToList(String pathString) throws IOException {
        return Files.readAllLines(Paths.get(pathString))
                .stream()
                .skip(1)
                .map(CsvParser::parsePlayerRow)
                .toList();
    }

    public static Player parsePlayerRow(String row) {
        var cells = row.split(";");
        return new Player(
                cells[0],
                cells[1],
                Position.valueOf(cells[3]),
                cells[4],
                cells[5],
                cells[6],
                Integer.parseInt(cells[8]),
                Integer.parseInt(cells[11])
        );
    }
}
