package spring.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;

import spring.ipldashboard.model.Team;
public interface TeamRepository extends CrudRepository<Team , Long> {

    Team findbyTeamName(String teamName);
    
  


    
}
