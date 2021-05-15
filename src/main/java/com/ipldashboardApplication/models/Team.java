package com.ipldashboardApplication.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {
      
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	private long totalMatch;
	private long totalWon;
	private String teamName;
	
	public Team() {}
	
	public Team(String name, long totalMatches) {
		
		this.teamName = name;
		this.totalMatch = totalMatches;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getTotalMatch() {
		return totalMatch;
	}
	public void setTotalMatch(long totalMatches) {
		this.totalMatch = totalMatches;
	}
	public long getTotalWon() {
		return totalWon;
	}
	public void setTotalWon(long totalWon) {
		this.totalWon = totalWon;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", totalMatch=" + totalMatch + ", totalWon=" + totalWon + ", teamName=" + teamName
				+ "]";
	}
	
		
}
