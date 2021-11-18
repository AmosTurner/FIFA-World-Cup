package Assignment2.Amos.Turner.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class contains information about a team such the team's name, continent, games played,
 * won, drawn, lost, and number of points
 *
 * @author Amos Turner
 * @since 2021-11-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable {

    private String teamFlag;
    private int teamID;
    private String teamName;
    private String continent;
    private final String[] continents = {"Africa", "Asia", "North and Central America and the Caribbean",
            "S. America", "Oceania", "Europe"};
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int points;
    private String sort;

    /**
     * Formats a string that represents Bootstraps notation for a country flag
     *
     * @return a string representation country flag
     */
    public String displayImage() {
        // Delimiter for spaces since there are no spaces in country flag names in Bootstrap
        String image[] = teamFlag.split(" ");

        // If the country name has one word in it format, use this form
        if (image.length == 1) {
            teamFlag = String.format("flag flag-%s", image[0].toLowerCase());
        }
        // If the country name has two words in it format, use this form
        else if (image.length == 2) {
            teamFlag = String.format("flag flag-%s-%s", image[0].toLowerCase(), image[1].toLowerCase());
        }

        return teamFlag;
    }

    /**
     * Calculates the total points for a team
     *
     * @return the amount of points for a team
     */
    public int calculatePoints() {
        points = (getWon() * 3) + getDrawn();
        return points;
    }
}
