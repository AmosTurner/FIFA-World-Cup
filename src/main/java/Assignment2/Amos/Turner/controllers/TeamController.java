package Assignment2.Amos.Turner.controllers;

import Assignment2.Amos.Turner.beans.Team;
import Assignment2.Amos.Turner.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Team Controller receives input from the user through the View. Then, it processes the user's data
 * by means of the Model and the results are returned to the view
 *
 * @author Amos Turner
 * @since 2021-11-10
 */
@Controller
public class TeamController {

    // Database Object that is used for database operations
    @Autowired
    DatabaseAccess da;

    // Represents a model and view returned by a handler
    ModelAndView mv;

    /**
     * Responds to HTTP GET requests matched with "/"
     *
     * @return rendered in the home page
     */
    @GetMapping("/")
    public ModelAndView homePage() {
        mv = new ModelAndView("home");
        return mv;
    }

    /**
     * Responds to HTTP GET requests matched with "/addTeam"
     *
     * @return Team object and all teams in the database as model objects to be rendered in the addTeam page
     */
    @GetMapping("/addTeam")
    public ModelAndView addTeamPage() {

        mv = new ModelAndView("addTeam", "teams", da.getTeams());
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Adds team to H2 database
     *
     * @param team - represents a team object
     * @return Team object and all teams in the database as model objects to be rendered in the addTeam page
     */
    @PostMapping("/addTeam")
    public ModelAndView addTeam(@ModelAttribute Team team) {

        da.addTeam(team.getTeamFlag(), team.getTeamName(), team.getContinent(),
                team.getPlayed(), team.getWon(), team.getDrawn(), team.getLost());
        System.out.println(team.getTeamName());
        mv = new ModelAndView("addTeam", "teams", da.getTeams());
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Responds to HTTP GET requests matched with "/editTeam"
     *
     * @return Team object and all teams in the database as model objects to be rendered in the editTeam page
     */
    @GetMapping("/editTeam")
    public ModelAndView editTeamPage() {
        mv = new ModelAndView("editTeam", "teams", da.getTeams());
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Edits a team by teamID
     *
     * @param teamID - teamID of the team being edited
     * @return Team object and all teams in the database as model objects to be rendered in the updateTeam page
     */
    @GetMapping("/editTeam/{teamID}")
    public ModelAndView editTeam(@PathVariable int teamID) {
        Team team = da.getTeam(teamID).get(0);
        mv = new ModelAndView("updateTeam", "teams", da.getTeams());
        mv.addObject("myTeam", team);
        return mv;
    }

    /**
     * Allows a user to update a teams fields
     *
     * @param team - represents a team object
     * @return team edited is rendered in the editTeam page
     */
    @PostMapping("/updateTeam")
    public ModelAndView updateTeam(@ModelAttribute Team team) {

        da.updateTeam(team);
        mv = new ModelAndView("redirect:/editTeam", "teams", da.getTeams());
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Responds to HTTP GET requests matched with "/deleteTeam"
     *
     * @return Team object and all teams in the database as model objects to be rendered in the deleteTeam page
     */
    @GetMapping("/deleteTeam")
    public ModelAndView deleteTeamPage() {
        mv = new ModelAndView("deleteTeam", "teams", da.getTeams());
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Deletes a team by teamID
     *
     * @param teamID - teamId team is being removed by
     * @return team removed is rendered in the deleteTeam page
     */
    @GetMapping("/deleteTeam/{teamID}")
    public ModelAndView deleteTeam(@PathVariable int teamID) {
        da.deleteTeam(teamID);
        mv = new ModelAndView("redirect:/deleteTeam", "teams", da.getTeams());
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Responds to HTTP GET requests matched with "/displayResults"
     *
     * @param team - represents a team object
     * @return Team object and all teams in the database as model objects to be rendered in the displayResults page
     */
    @GetMapping("/displayResults")
    public ModelAndView displayResultsPage(@ModelAttribute Team team) {
        mv = new ModelAndView("displayResults", "teams", da.getTeams());
        mv.addObject("myTeam", team);
        return mv;
    }

    /**
     * Searches for a team in the delete page
     *
     * @param team - represents a team object
     * @return search results to be rendered in the deleteTeam page
     */
    @PostMapping("/searchDelete")
    public ModelAndView searchDeletePage(@ModelAttribute Team team) {

        mv = new ModelAndView("deleteTeam", "teams", da.searchTeam(team.getTeamName(),
                team.getContinent()));
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Searches for a team in the edit page
     *
     * @param team - represents a team object
     * @return search results to be rendered in the editTeam page
     */
    @PostMapping("/searchEdit")
    public ModelAndView searchEditPage(@ModelAttribute Team team) {

        mv = new ModelAndView("editTeam", "teams", da.searchTeam(team.getTeamName(),
                team.getContinent()));
        mv.addObject("myTeam", new Team());
        return mv;
    }

    /**
     * Sorts a table by team name, continent, or points
     *
     * @param team - represents a team object
     * @return search results to be rendered in the displayResults page
     */
    @PostMapping("/sort")
    public ModelAndView sortTable(@ModelAttribute Team team) {

        mv = new ModelAndView("displayResults", "teams", da.sortTeams(team.getSort()));
        mv.addObject("myTeam", new Team());
        return mv;
    }
}
