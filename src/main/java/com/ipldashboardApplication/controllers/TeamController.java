package com.ipldashboardApplication.controllers;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ipldashboardApplication.models.Team;
import com.ipldashboardApplication.services.TeamService;
import com.ipldashboardApplication.validators.TeamValidator;

@RestController
@CrossOrigin
public class TeamController {

	Logger log = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	public TeamService teamService;

	@Autowired
	public TeamValidator teamValidator;

    /**
     * THIS API IS RESPONSIBLE FOR RETRIEVING TEAM DATA FROM DATABASE.
     * 	
     * getTeamParameterValidator() METHOD OF teamValidator CLASS WILL VALIDATE PASSED TEAM NAME AS A ARGUMENT. 
     * @param teamName
     * 
     * @return  WILL RETURN TEAM DATA IN STRING FORMAT OBJECT.
     */
	@GetMapping("/ipldashboard/team/{teamName}")
    public ResponseEntity<Team> toFatchTeam(@PathVariable String teamName) {
	   log.info("received {} ", teamName);
	   try
	   {
		  teamValidator.getTeamParameterValidator(teamName);
		  Team team = teamService.getTeamData(teamName);
		  team.setLatestMatches(teamService.getLatestMatches(teamName,4));
		  return new ResponseEntity<Team> (team, HttpStatus.OK);
	   }
	   catch(Exception e)
	   {
	    	 log.error("Exception occured, " +e.getMessage());
	         return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
       }
	}
	
	@GetMapping("/ipldashboard/allteams")
	public List<Team> toFetchAllTeam()
	{
		return teamService.getAllTeam();
	}
}
