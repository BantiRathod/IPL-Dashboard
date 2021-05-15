package com.ipldashboardApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.ipldashboardApplication.models.Team;
import com.ipldashboardApplication.repository.TeamRepository;

public class TeamService {

	@Autowired
	public TeamRepository teamRepository;
	
	public Team getTeamData(String teamName) 
	{
		return teamRepository.getTeam(teamName);
	}
}
