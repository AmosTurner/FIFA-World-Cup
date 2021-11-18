package Assignment2.Amos.Turner.database;

import Assignment2.Amos.Turner.beans.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The DatabaseAccess class uses SQL's CRUD operations to manipulate the database
 *
 * @author Amos Turner
 * @since 2021-11-10
 */
@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Adds a team to the H2 database
     */
    public void addTeam(String teamFlag, String teamName, String continent, int played, int won, int drawn, int lost) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO Teams " +
                "(TeamFlag, TeamName, Continent, Played, Won, Drawn, Lost)" +
                "VALUES (:TeamName1, :TeamName1, :Continent1, :Played1, :Won1, :Drawn1, :Lost1)";

        namedParameters.addValue("TeamFlag1", teamName); // not used
        namedParameters.addValue("TeamName1", teamName);
        namedParameters.addValue("Continent1", continent);
        namedParameters.addValue("Played1", played);
        namedParameters.addValue("Won1", won);
        namedParameters.addValue("Drawn1", drawn);
        namedParameters.addValue("Lost1", lost);
        jdbc.update(query, namedParameters);
    }

    /**
     * Gets all the teams in the H2 database
     *
     * @return List<Team> a list of all the teams in the database
     */
    public List<Team> getTeams() {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

    /**
     * Gets a team by teamID
     *
     * @param teamID - id of the team the user is requesting
     * @return List<Team> a list of all the teams in the database that match the given id
     */
    public List<Team> getTeam(int teamID) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams WHERE teamID=:teamID1";
        namedParameters.addValue("teamID1", teamID);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

    /**
     * Updates a team's fields
     *
     * @param team- team object containing team data
     */
    public void updateTeam(Team team) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE Teams SET teamFlag= :teamFlag, teamName= :teamName, continent= :continent, " +
                "played= :played, won= :won, drawn= :drawn, lost= :lost WHERE teamID = :teamID";
        namedParameters.addValue("teamID", team.getTeamID());
        namedParameters.addValue("teamFlag", team.getTeamName());
        namedParameters.addValue("teamName", team.getTeamName());
        namedParameters.addValue("continent", team.getContinent());
        namedParameters.addValue("played", team.getPlayed());
        namedParameters.addValue("won", team.getWon());
        namedParameters.addValue("drawn", team.getDrawn());
        namedParameters.addValue("lost", team.getLost());
        jdbc.update(query, namedParameters);
    }

    /**
     * Deletes a team by teamID
     *
     * @param teamID - id of the team the user is requesting to delete
     */
    public void deleteTeam(int teamID) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM Teams WHERE teamID = :teamID1";
        namedParameters.addValue("teamID1", teamID);
        jdbc.update(query, namedParameters);
    }

    /**
     * Searches for team or teams in a specific continent
     *
     * @param teamName  - the name of the team the user is searching for
     * @param continent - the name of the continent the team is in
     * @return a list of all the teams in the database that match the user's search request
     */
    public List<Team> searchTeam(String teamName, String continent) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        // Used to ignore cases
        String t = teamName.substring(0, 1).toUpperCase() + teamName.substring(1).toLowerCase();
        String c = continent.substring(0, 1).toUpperCase() + continent.substring(1).toLowerCase();

        namedParameters.addValue("teamName1", t + '%');
        namedParameters.addValue("continent1", c + '%');
        String query = "SELECT * FROM Teams WHERE continent LIKE :continent1 AND teamName LIKE :teamName1";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

    /**
     * Sorts teams by either team name, continent, or points
     *
     * @param sort - the sorting method the user has chosen
     * @return a list of sort teams
     */
    public List<Team> sortTeams(String sort) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = null;
        switch (sort) {
            case "teamName":
                query = "SELECT * FROM Teams ORDER BY teamName";
                break;
            case "continent":
                query = "SELECT * FROM Teams ORDER BY continent";
                break;
            case "points":
                query = "SELECT * FROM Teams ORDER BY (won * 3 + drawn) DESC";
                break;
        }
        namedParameters.addValue("sort1", sort);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Team>(Team.class));
    }

}