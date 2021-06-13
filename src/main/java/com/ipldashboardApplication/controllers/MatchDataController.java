package com.ipldashboardApplication.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ipldashboardApplication.models.MatchData;
import com.ipldashboardApplication.services.MatchDataService;

@RestController
@CrossOrigin
public class MatchDataController {

	
	@Autowired
	public MatchDataService matchDataService;
	
	
	/**
	 * THIS API IS RESPONSIBLE FOR RETRIEVING ALL MATCHES DATA FROM DATABASE.
	 *  
	 * @return WILL RETURN MATCHES DATA OF PERTICULAR PAGE.
	 */
	@GetMapping("ipldashboard/allmatches/{pageNo}")
	public Page<MatchData> toFetchPerticularPageMatches(@PathVariable int pageNo)
	{
		return matchDataService.getPerticularMatches(pageNo);
		 	
	}
	
	@GetMapping("/ipldashboard/team/{teamName}/matches/{year}")
	public List<MatchData> toFetchMatchesOfPerticularTeam(@PathVariable String  teamName, @PathVariable int year)
	{
		LocalDate startDate = LocalDate.of(year,1,1);
		LocalDate lastDate = LocalDate.of(year,12,30);
	   	return matchDataService.getPerticularTeamMatches(teamName,startDate,lastDate);
		 	
	}
	
    
}
