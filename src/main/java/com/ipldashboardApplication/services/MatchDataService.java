package com.ipldashboardApplication.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ipldashboardApplication.models.MatchData;
import com.ipldashboardApplication.repository.MatchDataRepository;

@Service
public class MatchDataService {
	
	@Autowired
	public MatchDataRepository matchDataRepository;

	public Page<MatchData> getPerticularMatches(int page) {
		Pageable pageRequet = PageRequest.of(page , 5);
		return  matchDataRepository.findAll(pageRequet);
	}

	public List<MatchData> getPerticularTeamMatches( String teamName, LocalDate startDate, LocalDate lastDate) {
		return  matchDataRepository.findMatches(teamName, startDate, lastDate);
	}

	public List<MatchData> findLatestMathesByTeamName(String teamName, int size) {
		  
		return  matchDataRepository.findLatestMathesByTeamName(teamName,size);
	}

}
