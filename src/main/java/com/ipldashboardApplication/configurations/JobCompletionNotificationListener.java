package com.ipldashboardApplication.configurations;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.ipldashboardApplication.models.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	  private Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	 
	  private final EntityManager em;

	  @Autowired
	  public JobCompletionNotificationListener(EntityManager em) {
	    this.em = em;
	  }
      	
	  
	  @Override
	  @Transactional
	  public void afterJob(JobExecution jobExecution)
	  {
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED)
	    {
	     log.info("!!! JOB FINISHED! Time to verify the results");

	     Map<String,Team> teamData = new HashMap<String,Team>();
	     
	     //TO SET TOTAL NUMBER OF MATCHES PLAYED BY EACH TEAM, TWO STEPS ARE BELOW
	     // IN FIRST STEP, QURY WILL RETURN LIST OF OBJECTS ARRAY BECAUSE WE DODNT KNOW THAT WHAT TYPE OF FIELDS ARE PRESENT IN ROW
	     // AND HERE, EACH ROW WOULD BE CONSIDERED AS A ARRAY.
	     em.createQuery("select m.team1, count(*) from MatchData m group By(m.team1)", Object[].class)
	     .getResultList()
	     .stream()
	     .map(e -> new Team((String) e[0], (long) e[1]))                   // create team instances
	     .forEach(team -> teamData.put(team.getTeamName(), team));        // map team name with team instances
	      
	     //THIS STEP FOR SETTING THE TOTAL MATCHES PLAYED BY TEAM.
	     em.createQuery("select m.team2, count(*) from MatchData m group By(m.team2)", Object[].class)
	     .getResultList()
	     .stream()
	     .forEach(e -> { Team team = teamData.get((String) e[0]);   
	      if(team!=null)
	      team.setTotalMatch(team.getTotalMatch()+(long) e[1]);
	     });
	     
	     //THIS STEP FOR SETTING THE TOTAL MATCHES WON BY TEAM.
	     em.createQuery("select m.teamWinner, count(*) from MatchData m group By(m.teamWinner)", Object[].class)
	     .getResultList()
	     .stream()
	     .forEach(e -> { Team team = teamData.get((String) e[0]); 
	     if(team!=null)
	      team.setTotalWon((long) e[1]);
	     });
	     
	     teamData.values().forEach(team -> em.persist(team));
	     teamData.values().forEach(team -> System.out.println(team));
	    }  
	  
	  }
}

