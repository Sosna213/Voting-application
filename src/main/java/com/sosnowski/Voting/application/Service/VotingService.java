package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTOs.VotingWithAnswersDTO;
import com.sosnowski.Voting.application.Entity.Answer;
import com.sosnowski.Voting.application.Entity.Voting;
import com.sosnowski.Voting.application.Repository.AnswerRepository;
import com.sosnowski.Voting.application.Repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final AnswerRepository answerRepository;

    public Voting addVoting(Voting voting){
        return votingRepository.save(voting);
    }

    @Transactional
    public List<Answer> addAnswersToVoting(List<Answer> answers){
        List<Answer> returnedAnswers= new ArrayList<Answer>();
        answers.forEach(answer -> {
            answerRepository.save(answer);
            returnedAnswers.add(answer);
        });
        return returnedAnswers;
    }

    public VotingWithAnswersDTO getVotingWithAnswers(Long votingId){
        VotingWithAnswersDTO votingWithAnswersDTO = new VotingWithAnswersDTO();
        votingWithAnswersDTO.setVotingId(votingId);
        votingWithAnswersDTO.setQuestion(votingRepository.getById(votingId).getQuestion());
        List<String> answers = new ArrayList<>();
        answerRepository.findAnswersByVotingVotingId(votingId).forEach(answer -> {
            answers.add(answer.getAnswer());
        });
        votingWithAnswersDTO.setAnswers(answers);

        return votingWithAnswersDTO;
    }
}
