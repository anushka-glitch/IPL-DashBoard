package spring.ipldashboard.data;

import javax.sql.DataSource;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;


@Configuration
@EnableBatchProcessing

public class BatchConfig {

  private final String[] FIELD_NAMES = new String[] {
      "ID", "City", "Date", "Season", "MatchNumber", "Team1", "Team2", "Venue", "TossWinner", "TossDecision",
      "SuperOver", "WinningTeam", "WonBy", "Margin", "method", "Player_of_Match", "Team1Players", "Team2Players",
      "Umpire1", "Umpire2"

  };

  @Autowired
  public JobBuilder jobBuilder;

  @Autowired
  public StepBuilder stepBuilder;

  @Bean
  public FlatFileItemReader<MatchInput> reader() {
    return new FlatFileItemReaderBuilder<MatchInput>().name("MatchItemReader")
        .resource(new ClassPathResource("match-data.csv")).delimited()
        .names(FIELD_NAMES)
        .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
          {
            setTargetType(MatchInput.class);
          }
        })

        .build();
  }

  @Bean
  public MatchDataPocessor processor() {
    return new MatchDataPocessor();
  }

  @Bean
  public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Match>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .sql(
            "INSERT INTO match(id,city,date,season,match_number,team1,team2,venue,toss_winner,toss_decision,super_over,winning_team,won_by,margin,umpire1,umpire2)"
                + " VALUES (:id,:city,:date,:season,:matchNumber,:team1,:team2,:venue,:tossWinner,:tossDecision,:superOver,:winningTeam,:wonBy,:margin,:player_of_Match,:umpire1,:umpire2)")
        .dataSource(dataSource)
        .beanMapped()
        .build();
  }

  @Bean
  public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
    return new JobBuilder("importUserJob", jobRepository)
        .listener(listener)
        .start(step1)
        .build();
  }



  @Bean
  public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
          FlatFileItemReader<MatchInput> reader, ItemProcessor<MatchInput, Match >MatchDataPocessor, JdbcBatchItemWriter<Match> writer) {
  return new StepBuilder("step1", jobRepository)
    .<MatchInput, Match> chunk(30, transactionManager)
    .reader(reader)
    .writer(writer)
    .processor(MatchDataPocessor)
    .build();


  }
}
