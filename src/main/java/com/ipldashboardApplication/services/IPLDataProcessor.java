package com.ipldashboardApplication.services;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import com.ipldashboardApplication.models.FetchMatchData;
import com.ipldashboardApplication.models.MatchData;


@Service
public class IPLDataProcessor implements ItemProcessor <FetchMatchData , MatchData> {

	Logger logger=LoggerFactory.getLogger(IPLDataProcessor.class);
	
	@Override
	public MatchData process(FetchMatchData fetchMatchData) throws Exception {
		
		logger.info("fetched match record {} ", fetchMatchData);
		
		MatchData matchData = new MatchData();
		matchData.setId(Long.parseLong(fetchMatchData.getId()));
		matchData.setCity(fetchMatchData.getCity());
		matchData.setDate(LocalDate.parse(fetchMatchData.getDate()));
		matchData.setPlayerOfMatch(fetchMatchData.getPlayerOfMatch());
		matchData.setResult(fetchMatchData.getResult());
		matchData.setResultMargin(fetchMatchData.getResultMargin());
		
		String firstTeam, secondTeam;
		
		// we will set team1 and team2 depending on batting order
		if("bat".equals(fetchMatchData.getTossDecision()))
		{
			firstTeam  = fetchMatchData.getTossWinner();
			if(fetchMatchData.getTossWinner().equals(fetchMatchData.getTeam1()))
				secondTeam = fetchMatchData.getTeam2();
			else
				secondTeam = fetchMatchData.getTeam1(); 
		}
		else
		{
			secondTeam  = fetchMatchData.getTossWinner();
			if(fetchMatchData.getTossWinner().equals(fetchMatchData.getTeam1()))
				firstTeam = fetchMatchData.getTeam2();
			else
				firstTeam = fetchMatchData.getTeam1(); 
		}
		
		matchData.setTeam1(firstTeam);
		matchData.setTeam2(secondTeam);
		matchData.setTossDecision(fetchMatchData.getTossDecision());
		matchData.setTossWinner(fetchMatchData.getTossWinner());
		matchData.setUmpire1(fetchMatchData.getUmpire1());
		matchData.setUmpire2(fetchMatchData.getUmpire2());
		matchData.setVenue(fetchMatchData.getVenue());
		matchData.setTeamWinner(fetchMatchData.getTeamWinner());

		return matchData;
	} 
    
	
	
	
}
