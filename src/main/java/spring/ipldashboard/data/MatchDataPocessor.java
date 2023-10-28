package spring.ipldashboard.data;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import spring.ipldashboard.model.Match;



public class MatchDataPocessor implements ItemProcessor<MatchInput, Match> {

  private static final Logger log = LoggerFactory.getLogger(MatchDataPocessor.class);

  @Override
  public Match process(final MatchInput matchInput) {
   
    Match match = new Match();
    match.setId(Long.parseLong(matchInput.getId()));
    match.setCity(matchInput.getCity());
    match.setDate(LocalDate.parse(matchInput.getDate()));
    match.setSeason(matchInput.getSeason());
    match.setMatchNumber(matchInput.getMatchNumber());
    match.setVenue(matchInput.getVenue());
    
    String firstInningsTeam , secondInningsTeam;

    if("bat".equals(matchInput.getTossDecision())){
        firstInningsTeam=matchInput.getTossWinner();
        secondInningsTeam=matchInput.getTossWinner().equals(matchInput.getTeam1()) 
        ? matchInput.getTeam2():matchInput.getTeam1();
    } else{
        secondInningsTeam=matchInput.getTossWinner();
        firstInningsTeam=matchInput.getTossWinner().equals(matchInput.getTeam1()) 
        ? matchInput.getTeam2():matchInput.getTeam1();
    }

    match.setTeam1(firstInningsTeam);
    match.setTeam2(secondInningsTeam);


  match.setTossWinner(matchInput.getTossWinner());
  match.setTossDecision(matchInput.getTossDecision());
  match.setWinningTeam(matchInput.getWinningTeam());
  match.setWonBy(matchInput.getWonBy());
  match.setMargin(matchInput.getMargin());
  match.setPlayer_of_Match(matchInput.getPlayer_of_Match());
  match.setUmpire1(matchInput.getUmpire1());
  match.setUmpire2(matchInput.getUmpire2());

  
    

    return match;
  }

}


