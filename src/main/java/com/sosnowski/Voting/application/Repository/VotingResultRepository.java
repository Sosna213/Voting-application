package com.sosnowski.Voting.application.Repository;

import com.sosnowski.Voting.application.Entity.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingResultRepository extends JpaRepository<VotingResult, Long> {
    public List<VotingResult> findVotingResultsByUserUsername(String username);

}
