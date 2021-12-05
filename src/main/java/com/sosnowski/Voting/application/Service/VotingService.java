package com.sosnowski.Voting.application.Service;

import com.sosnowski.Voting.application.DTOs.*;
import com.sosnowski.Voting.application.Entity.Answer;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.Voting;
import com.sosnowski.Voting.application.Entity.VotingResult;
import com.sosnowski.Voting.application.Repository.AnswerRepository;
import com.sosnowski.Voting.application.Repository.UserRepository;
import com.sosnowski.Voting.application.Repository.VotingRepository;
import com.sosnowski.Voting.application.Repository.VotingResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final VotingResultRepository votingResultRepository;

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
        List<Answer> returnedAnswers = new ArrayList<>();
        addVotingDTO.getAnswers().forEach(answer -> {
            Answer newAnswer = new Answer();
            newAnswer.setVoting(addedVoting);
            newAnswer.setAnswer(answer.getAnswer());
            answerRepository.save(newAnswer);
            returnedAnswers.add(newAnswer);
        });
        return addVotingDTO;
    }

    private void deleteAnswersForVoting(Long votingId) {
        answerRepository.findAnswersByVotingVotingId(votingId).forEach(answer -> {
            answerRepository.delete(answer);
        });
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
        List<AnswerDTO> answers = new ArrayList<>();
        answerRepository.findAnswersByVotingVotingId(votingId).forEach(answer -> {
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setAnswerId(answer.getAnswerId());
            answerDTO.setAnswer(answer.getAnswer());
            answers.add(answerDTO);
        });
        votingWithAnswersDTO.setAnswers(answers);
        return votingWithAnswersDTO;
    }
    public List<VotingDTO> getVotingByUsername(String username){
        List<Voting> votingList = votingRepository.findByUserUsername(username);
        List<VotingDTO> votingDTOList = mapListOfVotingToVotingDTO(votingList);

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
        votingToEdit.setVotingName(editVotingDTO.getVotingName());
        votingToEdit.setEndDate(editVotingDTO.getEndDate());
        votingToEdit.setRestricted(editVotingDTO.getRestricted());
        votingToEdit.setQuestion(editVotingDTO.getQuestion());
        deleteAnswersForVoting(editVotingDTO.getVotingId());
        final Voting addedVoting = votingRepository.save(votingToEdit);
        editVotingDTO.getAnswers().forEach(answerDTO -> {
            Answer answerToAdd = new Answer();
            answerToAdd.setVoting(addedVoting);
            answerToAdd.setAnswer(answerDTO.getAnswer());
            answerRepository.save(answerToAdd);
        });
        return editVotingDTO;
    }

     public Long shareVotingToUser(ShareVotingToUserDTO shareVotingToUserDTO){
        User userToShare = userRepository.findByUsername(shareVotingToUserDTO.getUsername());
        Voting voting = votingRepository.getById(shareVotingToUserDTO.getVotingId());
        Collection<User> sharedToUsers = voting.getSharedToUsers();
        sharedToUsers.add(userToShare);
        voting.setSharedToUsers(sharedToUsers);
        votingRepository.save(voting);
        return userToShare.getUserId();
     }

    public Collection<User> getSharedUsersVoting(Long votingId) {
        Collection<User> sharedToUsers = votingRepository.getById(votingId).getSharedToUsers();
        return sharedToUsers;
    }
    public List<SharedVotingDTO> getVotingSharedToUser(String username){
        List<Voting> votingList = votingRepository.findBySharedToUsersUsername(username);
        List<VotingDTO> votingDTOList = mapListOfVotingToVotingDTO(votingList);

        List<VotingResult> votingResults = votingResultRepository.findVotingResultsByUserUsername(username);
        List<Long> votingIds = new ArrayList<>();

        votingResults.forEach(votingResult -> {
            votingIds.add(votingResult.getVoting().getVotingId());
        });

        List<SharedVotingDTO> sharedVotingDTOS = new ArrayList<>();
        votingDTOList.forEach(votingDTO -> {
            boolean voted = false;
            if(votingIds.contains(votingDTO.getVotingId())){
                voted = true;
            }
            SharedVotingDTO sharedVotingDTO = new SharedVotingDTO();
            sharedVotingDTO.setVotingDTO(votingDTO);
            sharedVotingDTO.setVoted(voted);
            sharedVotingDTOS.add(sharedVotingDTO);
        });
        return sharedVotingDTOS;
    }
    private List<VotingDTO> mapListOfVotingToVotingDTO(List<Voting> votingList){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
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

    public VotingResult vote(VoteDTO vote){
        VotingResult voteToSave = new VotingResult();
        voteToSave.setVoting(votingRepository.getById(vote.getVotingId()));
        voteToSave.setAnswer(answerRepository.getById(vote.getAnswerId()));
        voteToSave.setUser(userRepository.findByUsername(vote.getUsername()));
        return votingResultRepository.save(voteToSave);
    }
}
