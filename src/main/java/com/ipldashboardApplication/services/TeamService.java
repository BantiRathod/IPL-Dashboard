package com.ipldashboardApplication.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ipldashboardApplication.models.MatchData;
import com.ipldashboardApplication.models.Team;
import com.ipldashboardApplication.repository.MatchDataRepository;
import com.ipldashboardApplication.repository.TeamRepository;
import com.ipldashboardApplication.validators.TeamValidator;


@Service
public class TeamService {

	@Autowired
	public MatchDataRepository matchDataRepository; 

	@Autowired
	public MatchDataService matchDataService; 
	
	@Autowired
	public TeamRepository teamRepository;
	
	@Autowired
	public TeamValidator teamValidator;
	
	public Team getTeamData(String teamName) throws Exception
	{
		teamValidator.teamRequestBusinessLogicVaidator(teamName);
		return teamRepository.findByTeamName(teamName);
	}

	public List<MatchData> getLatestMatches(String teamName, int size) {
		return matchDataService.findLatestMathesByTeamName(teamName,size);
	}

	public List<Team> getAllTeam() {
		return (List<Team>) teamRepository.findAll();
	}

}
