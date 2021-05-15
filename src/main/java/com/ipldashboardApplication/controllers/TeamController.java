package com.ipldashboardApplication.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ipldashboardApplication.models.Team;
import com.ipldashboardApplication.services.TeamService;
import com.ipldashboardApplication.validators.TeamValidator;

@RestController
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
	@GetMapping("/ipldashboard/{teamName}")
    public String toFatchTeam(@PathVariable String teamName) {
	   log.info("received team name {} ", teamName);
	   try
	   {
		  teamValidator.getTeamParameterValidator(teamName);
		  Team team = teamService.getTeamData(teamName);
		  return team.toString();
	   }
	   catch(Exception e)
	   {
		   return e.getMessage();
	   }
   }
}
