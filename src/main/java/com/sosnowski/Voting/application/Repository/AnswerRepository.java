package com.sosnowski.Voting.application.Repository;

import com.sosnowski.Voting.application.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByVotingVotingId(Long votingId);
}
