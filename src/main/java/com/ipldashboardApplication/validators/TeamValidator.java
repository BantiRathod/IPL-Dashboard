package com.ipldashboardApplication.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipldashboardApplication.models.Team;
import com.ipldashboardApplication.repository.TeamRepository;

@Service
public class TeamValidator {

	@Autowired 
	public TeamRepository teamRepository;
	
	
	public void getTeamParameterValidator(String teamName) throws Exception
	{
		if(teamName==null)
			throw new Exception("Invalid Value Passed !!!");
	}

	public void teamRequestBusinessLogicVaidator(String teamName) throws Exception
	{
	    Team team = teamRepository.findByTeamName(teamName);	
	    if(team==null)
	    {
	    	throw new Exception("Invalid Team Name Passed !!!");
	    }
		
	}
}
