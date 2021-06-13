package com.ipldashboardApplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipldashboardApplication.models.MatchData;
import com.ipldashboardApplication.models.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team , Long>{

//	@Query("select t from Team t where t.teamName = :teamName")
//	public Team getTeam(@Param("teamName") String teamName);

    public Team findByTeamName(String teamName);

	
	
	
}
