package com.ipldashboardApplication.configurations;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.ipldashboardApplication.services.IPLDataProcessor;
import com.ipldashboardApplication.models.FetchMatchData;
import com.ipldashboardApplication.models.MatchData;



/*
 * THIS CLASS IS RESPONSIBLE FOR BATCH CONFIGURATION.
 * FIRST OF ALL CREATE JOB THEN DEFINE STEPS TO DO IT.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfigration {

	  @Autowired
	  public JobBuilderFactory jobBuilderFactory;

	  @Autowired
	  public StepBuilderFactory stepBuilderFactory;
	  
	  private String [] Fields_Name = new String []{"id", "city", "date","playerOfMatch","venue","neutralVenue","team1","team2",
			  "tossWinner","tossDecision","teamWinner","result","resultMargin","eliminator","method","umpire1","umpire2"};
	  
	  
	  // THIS METHOD IS RESPONSIBLE FOR READING OR RETRIEVING DATA FROM CSV FILE.
	  @Bean                                                      
	  public FlatFileItemReader<FetchMatchData> reader() {
	    return new FlatFileItemReaderBuilder<FetchMatchData>()
	      .name("IPLItemReader")  // NAME OF PROCESSOR CLASS
	      .resource(new ClassPathResource("IPL MATCH.csv")) // NAME OF FILE FROM WHERE DATA HAS TO BE READ.
	      .delimited()
	      .names(Fields_Name)  // FIELDS NAME WHERE DATA HAS TO SET.
	      .fieldSetMapper(new BeanWrapperFieldSetMapper<FetchMatchData>() {{
	        setTargetType(FetchMatchData.class);
	      }})
	      .build();
	  }

	  
	  //THIS METHOD IS RESPONSIBLE FOR PROCESSING THE RETRIEVED DATA FROM CSV FILE.
	  @Bean
	  public IPLDataProcessor processor() {
	    return new IPLDataProcessor();
	  }

	  
	  //THIS METHOD IS RESPONSIBLE FOR WRITTING THE PROCESSED DATA SOMEWHERE ELSE(DATA BASE).
	  @Bean
	  public JdbcBatchItemWriter<MatchData> writer(DataSource dataSource) {
	    return new JdbcBatchItemWriterBuilder<MatchData>()
	      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	      .sql("INSERT INTO match_data( id, city, date,player_of_match,venue,team1,team2," + 
	        " toss_winner,toss_decision,team_winner,result,result_margin,umpire1,umpire2) VALUES ( :id,:city,:date,:playerOfMatch,:venue,:team1,:team2," + 
	        ":tossWinner,:tossDecision,:teamWinner,:result,:resultMargin,:umpire1,:umpire2)")
	      .dataSource(dataSource)
	      .build();   
	  }
	  
	  // IN THIS METHOD DEFINE JOB.
	  @Bean
	  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	    return jobBuilderFactory.get("importUserJob")
	      .incrementer(new RunIdIncrementer())                    //TO MAINTAIN THE STATE OF THE DATABASE
	      .listener(listener)                                     // TO CALL JOBCOMPLETIONNOTIFICATIONLISTENER AFTER JOB COMPLETION FOR NOTIFY
	      .flow(step1)
	      .end()
	      .build();
	  }

	  // IN THIS METHOD DEFINE THE STEPS TO DO JOB.
	  @Bean
	  public Step step1(JdbcBatchItemWriter<MatchData> writer) {
	    return stepBuilderFactory.get("step1")
	      .<FetchMatchData, MatchData> chunk(10)              // HOW MUCH DATA WOULD BE WRITTEN AT A TIME IN DATA ABSE
	      .reader(reader())
	      .processor(processor())
	      .writer(writer)
	      .build();
	  }
}
