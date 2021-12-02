package com.sosnowski.Voting.application.Repository;

import com.sosnowski.Voting.application.Entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotingRepository extends JpaRepository<Voting, Long> {
    public List<Voting> findByUserUsername(String userId);
}
