package com.sosnowski.Voting.application.Repository;

import com.sosnowski.Voting.application.Entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<Voting, Long> {
}
