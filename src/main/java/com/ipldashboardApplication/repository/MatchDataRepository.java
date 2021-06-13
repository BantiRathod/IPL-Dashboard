package com.ipldashboardApplication.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ipldashboardApplication.models.MatchData;

//NOTE :- DEFAULT METHOD ALLOW US TO CREATE BODY OF THE FUNCTION.

public interface MatchDataRepository extends JpaRepository <MatchData,Long>    {
  
	//FIRST WAY TO PERFORM OPERATION OBER DATABASE.
	public List<MatchData> getMatchesByTeam1OrTeam2OrderByDateDesc(String teamName1 , String teamName2 , Pageable pageable);
	default List<MatchData> findLatestMathesByTeamName(String teamName, int size)
	{
		return getMatchesByTeam1OrTeam2OrderByDateDesc(teamName , teamName , PageRequest.of(0, size));
	}
	
	//SECOND WAY TO PERFORM OPERTION OVER DATBASE
	@Query("select m from MatchData m where ((m.team1 = :teamName or m.team2 = :teamName) and (m.date between :startDate and :lastDate))")
	public List<MatchData> findMatches(@Param("teamName") String teamName, @Param("startDate") LocalDate startDate, @Param("lastDate") LocalDate lastDate);
	

//	
//	//public Page<MatchData> getMatchesByTeam1OrTeam2AndDateBetweenOrderByDateDesc(String teamName1, String teamName2, LocalDate startDate, LocalDate lastDate);
//	
//	// DEFAULT METHOD ALLOW US TO CREATE BODY OF THE FUNCTION.
//	default Page<MatchData> findMatches(String teamName, LocalDate startDate, LocalDate lastDate)
//		{
//			return getMatchesByTeam1OrTeam2AndDateBetweenOrderByDateDesc(teamName, teamName, startDate, lastDate);
//		}
	 
}
