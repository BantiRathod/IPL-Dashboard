package com.ipldashboardApplication.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ipldashboardApplication.models.Team;

public interface TeamRepository extends CrudRepository<Team , Long>{

	@Query("select t from Team t where t.teamName = :teamName")
	public Team getTeam(@Param("teamName") String teamName);
}
