package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTOs.AddVotingDTO;
import com.sosnowski.Voting.application.DTOs.EditVotingDTO;
import com.sosnowski.Voting.application.DTOs.VotingDTO;
import com.sosnowski.Voting.application.DTOs.VotingWithAnswersDTO;
import com.sosnowski.Voting.application.Entity.Answer;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.Voting;
import com.sosnowski.Voting.application.Repository.AnswerRepository;
import com.sosnowski.Voting.application.Repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public AddVotingDTO addVoting(AddVotingDTO addVotingDTO){
        Voting votingToAdd = new Voting();
        votingToAdd.setVotingName(addVotingDTO.getVotingName());
        votingToAdd.setQuestion(addVotingDTO.getQuestion());
        User userToAddVoting = new User();
        votingToAdd.setRestricted(addVotingDTO.getRestricted());
        votingToAdd.setEndDate(addVotingDTO.getEndDate());
        userToAddVoting.setUserId(addVotingDTO.getUserId());
        votingToAdd.setUser(userToAddVoting);
        final Voting addedVoting = votingRepository.save(votingToAdd);
        List<Answer> returnedAnswers= new ArrayList<Answer>();
        addVotingDTO.getAnswers().forEach(answer -> {
            Answer newAnswer = new Answer();
            newAnswer.setVoting(addedVoting);
            newAnswer.setAnswer(answer.getAnswer());
            answerRepository.save(newAnswer);
            returnedAnswers.add(newAnswer);
        });
        return addVotingDTO;
    }

    public VotingWithAnswersDTO getVotingWithAnswers(Long votingId){
        VotingWithAnswersDTO votingWithAnswersDTO = new VotingWithAnswersDTO();
        votingWithAnswersDTO.setVotingId(votingId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        votingWithAnswersDTO.setQuestion(votingRepository.getById(votingId).getQuestion());
        votingWithAnswersDTO.setVotingName(votingRepository.getById(votingId).getVotingName());
        votingWithAnswersDTO.setRestricted(votingRepository.getById(votingId).getRestricted());
        if(votingRepository.getById(votingId).getEndDate() != null) {
            String endDate = dateFormat.format(votingRepository.getById(votingId).getEndDate());
            votingWithAnswersDTO.setEndDate(endDate);
        }
        List<String> answers = new ArrayList<>();
        answerRepository.findAnswersByVotingVotingId(votingId).forEach(answer -> {
            answers.add(answer.getAnswer());
        });
        votingWithAnswersDTO.setAnswers(answers);
        return votingWithAnswersDTO;
    }
    public List<VotingDTO> getVotingByUserId(String username){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        List<Voting> votingList = votingRepository.findByUserUsername(username);
        List<VotingDTO> votingDTOList = new ArrayList<>();
        votingList.forEach(voting -> {
            VotingDTO votingDTO = new VotingDTO();
            votingDTO.setVotingId(voting.getVotingId());
            votingDTO.setVotingName(voting.getVotingName());
            votingDTO.setRestricted(voting.getRestricted());
            if(voting.getEndDate() != null){
                String endDate = dateFormat.format(voting.getEndDate());
                votingDTO.setEndDate(endDate);
            }
            votingDTO.setQuestion(voting.getQuestion());
            votingDTOList.add(votingDTO);
        });
        return votingDTOList;
    }
    @Transactional
    public Long deleteVotingAndAnswers(Long votingId){
        List<Answer> answers = answerRepository.findAnswersByVotingVotingId(votingId);
        answers.forEach(answer -> {
            answerRepository.delete(answer);
        });
        votingRepository.deleteById(votingId);
        return votingId;
    }

    public EditVotingDTO editVoting(EditVotingDTO editVotingDTO){
        Voting votingToEdit = votingRepository.getById(editVotingDTO.getVotingId());
        deleteVotingAndAnswers(editVotingDTO.getVotingId());
        votingToEdit.setVotingName(editVotingDTO.getVotingName());
        votingToEdit.setEndDate(editVotingDTO.getEndDate());
        votingToEdit.setRestricted(editVotingDTO.getRestricted());
        votingToEdit.setQuestion(editVotingDTO.getQuestion());
        final Voting addedVoting = votingRepository.save(votingToEdit);
        editVotingDTO.getAnswers().forEach(answerDTO -> {
            Answer answerToAdd = new Answer();
            answerToAdd.setVoting(addedVoting);
            answerToAdd.setAnswer(answerDTO.getAnswer());
            answerRepository.save(answerToAdd);
        });
        return editVotingDTO;
    }
}
