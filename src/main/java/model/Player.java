package model;

public record Player (
        String name,
        String team,
        Position position,
        String country,
        String agency,
        String cost,
        int goals,
        int redCards
) {
}
