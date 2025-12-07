package resolver;

import model.Player;
import model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResolverTest {
    private List<Player> testPlayers;
    private Resolver resolver;

    @BeforeEach
    void setUp() {
        testPlayers = Arrays.asList(
                new Player("Iva Streich", "Nevada whales", Position.MIDFIELD, "Colombia", "D'Amore LLC", "75012006", 19, 7),
                new Player("Miss Buck Bradtke", "Nevada whales", Position.DEFENDER, "Brazil", "D'Amore LLC", "33850831", 26, 9),
                new Player("Everette Kovacek MD", "Wisconsin prophets", Position.DEFENDER, "Palau", "Walker and Sons", "64396675", 11, 5),
                new Player("Ms. Adolph Hartmann", "North Carolina dolphins", Position.FORWARD, "Croatia", "", "52944545", 10, 8),
                new Player("Kenyetta Emard", "Mississippi witches", Position.GOALKEEPER, "Ecuador", "D'Amore LLC", "94715977", 24, 7),
                new Player("Mrs. Celina Abshire", "Massachusetts enchanters", Position.GOALKEEPER, "Qatar", "D'Amore LLC", "41436703", 4, 0),
                new Player("Dr. Royal King", "Florida tigers", Position.GOALKEEPER, "Ecuador", "Walker and Sons", "50056961", 6, 4),
                new Player("Rory Koss", "Utah nemesis", Position.MIDFIELD, "Ghana", "Jakubowski and Sons", "4483194", 13, 2),
                new Player("Lorinda Labadie", "Utah nemesis", Position.MIDFIELD, "Germany", "Price-Hirthe", "33413181", 13, 4),
                new Player("Ms. Erwin Hoeger", "Minnesota giants", Position.DEFENDER, "Qatar", "Fadel LLC", "71207725", 28, 1),
                new Player("Terry Hegmann", "Wisconsin prophets", Position.DEFENDER, "Germany", "Graham-Powlowski", "7294362", 11, 8),
                new Player("Serina Moen", "Nevada whales", Position.MIDFIELD, "Brazil", "Bogisich-Rempel", "91242062", 0, 6),
                new Player("Moshe Wiegand", "North Carolina dolphins", Position.DEFENDER, "Brazil", "Jakubowski and Sons", "30073197", 19, 0),
                new Player("Zackary Kilback", "Nevada whales", Position.MIDFIELD, "Korea (Democratic People's Republic of)", "D'Amore LLC", "63619334", 23, 4),
                new Player("Beckie Lindgren", "Nevada whales", Position.DEFENDER, "Germany", "Walker and Sons", "11576194", 16, 7),
                new Player("Eusebio Beatty IV", "New Mexico ogres", Position.DEFENDER, "Ecuador", "D'Amore LLC", "17429288", 10, 4),
                new Player("Lannie Brakus", "Utah nemesis", Position.GOALKEEPER, "Ghana", "Bogisich-Rempel", "71830024", 1, 6)
        );
        resolver = new Resolver(testPlayers);
    }

    @Test
    @DisplayName("Should return quantity playear without Agency")
    void testGetCountWithoutAgency() {
        int count = resolver.getCountWithoutAgency();
        assertEquals(1, count, " Only Ms. Adolph without agency");
    }

    @Test
    @DisplayName("Should consider NULL like absence Agency")
    void testGetCountWithoutAgency_WithNull() {
        List<Player> withNull = Arrays.asList(
                new Player("Player1", "Team", Position.FORWARD, "Country", null, "1000000", 10, 0),
                new Player("Player2", "Team", Position.FORWARD, "Country", "", "1000000", 10, 0),
                new Player("Player3", "Team", Position.FORWARD, "Country", "Agency", "1000000", 10, 0)
        );
        Resolver testResolver = new Resolver(withNull);

        assertEquals(2, testResolver.getCountWithoutAgency());
    }

    @Test
    @DisplayName("Should group players by position")
    void testGetPlayersByPosition() {
        Map<String, String> playersByPosition = resolver.getPlayersByPosition();

        assertNotNull(playersByPosition);
        assertTrue(playersByPosition.containsKey("Полузащитник"));
        assertTrue(playersByPosition.containsKey("Защитник"));
        assertTrue(playersByPosition.containsKey("Нападающий"));
        assertTrue(playersByPosition.containsKey("Вратарь"));

        String midfielders = playersByPosition.get("Полузащитник");
        assertTrue(midfielders.contains("Iva Streich"));
        assertTrue(midfielders.contains("Rory Koss"));
        assertTrue(midfielders.contains("Lorinda Labadie"));
        assertTrue(midfielders.contains("Serina Moen"));
        assertTrue(midfielders.contains("Zackary Kilback"));
    }

    @AfterEach
    void tearDown() {
    }
}
