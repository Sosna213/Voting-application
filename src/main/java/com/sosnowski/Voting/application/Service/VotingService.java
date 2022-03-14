package com.sosnowski.Voting.application.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sosnowski.Voting.application.DTO.*;
import com.sosnowski.Voting.application.Entity.Answer;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.Voting;
import com.sosnowski.Voting.application.Entity.VotingResult;
import com.sosnowski.Voting.application.Repository.AnswerRepository;
import com.sosnowski.Voting.application.Repository.UserRepository;
import com.sosnowski.Voting.application.Repository.VotingRepository;
import com.sosnowski.Voting.application.Repository.VotingResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final VotingResultRepository votingResultRepository;

    @Transactional
    public AddVotingDTO addVoting(AddVotingDTO addVotingDTO) {
        Voting votingToAdd = getVotingFromAddVotingDTO(addVotingDTO);
        addEditAnswers(votingToAdd, addVotingDTO.getAnswers());
        return addVotingDTO;
    }

    public VotingWithAnswersDTO getVotingWithAnswers(Long votingId) {
        VotingWithAnswersDTO votingWithAnswersDTO = getVotingWithAnswersDTOFromVoting(votingId);
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

    public List<VotingDTO> getVotingByUsername(String username) {
        List<Voting> votingList = votingRepository.findByUserUsername(username);

        return mapListOfVotingToVotingDTO(votingList);
    }

    @Transactional
    public Long deleteVotingAndAnswers(Long votingId) {
        deleteResultForVoting(votingId);
        deleteAnswersForVoting(votingId);
        votingRepository.deleteById(votingId);

        return votingId;
    }

    @Transactional
    public EditVotingDTO editVoting(EditVotingDTO editVotingDTO) {
        Voting votingToEdit = getVotingFromVotingDTO(editVotingDTO);
        deleteResultForVoting(editVotingDTO.getVotingId());
        deleteAnswersForVoting(editVotingDTO.getVotingId());
        addEditAnswers(votingToEdit, editVotingDTO.getAnswers());

        return editVotingDTO;
    }

    public List<String> shareVotingToUsers(ShareVotingToUsersDTO shareVotingToUsersDTO) {
        HashSet<User> users = new HashSet<>();
        shareVotingToUsersDTO.getUsernames().forEach(username -> users.add(userRepository.findByUsername(username)));
        Voting voting = votingRepository.getById(shareVotingToUsersDTO.getVotingId());
        Collection<User> sharedToUsers = voting.getSharedToUsers();
        users.addAll(sharedToUsers);
        voting.setSharedToUsers(users);
        votingRepository.save(voting);

        return shareVotingToUsersDTO.getUsernames();
    }

    public Long deactivateVoting(Long votingId) {
        Voting votingToDeactivate = votingRepository.getById(votingId);
        votingToDeactivate.setActive(false);
        Voting votingDeactivated = votingRepository.save(votingToDeactivate);

        return votingDeactivated.getVotingId();
    }

    public Collection<User> getSharedUsersForVoting(Long votingId) {
        return votingRepository.getById(votingId).getSharedToUsers();
    }

    public List<SharedVotingDTO> getVotingSharedToUser(String username) {
        List<Voting> votingList = votingRepository.findBySharedToUsersUsername(username);
        List<VotingDTO> votingDTOList = mapListOfVotingToVotingDTO(votingList);
        List<VotingResult> votingResults = votingResultRepository.findVotingResultsByUserUsername(username);
        List<Long> votingIds = new ArrayList<>();

        votingResults.forEach(votingResult -> votingIds.add(votingResult.getVoting().getVotingId()));

        List<SharedVotingDTO> sharedVotingDTOS = new ArrayList<>();
        votingDTOList.forEach(votingDTO -> {
            boolean voted = votingIds.contains(votingDTO.getVotingId());
            SharedVotingDTO sharedVotingDTO = new SharedVotingDTO();
            sharedVotingDTO.setVotingDTO(votingDTO);
            sharedVotingDTO.setVoted(voted);
            sharedVotingDTOS.add(sharedVotingDTO);
        });
        return sharedVotingDTOS;
    }

    public VotingResult vote(VoteDTO vote) {
        VotingResult voteToSave = new VotingResult();
        voteToSave.setVoting(votingRepository.getById(vote.getVotingId()));
        voteToSave.setAnswer(answerRepository.getById(vote.getAnswerId()));
        voteToSave.setUser(userRepository.findByUsername(vote.getUsername()));

        return votingResultRepository.save(voteToSave);
    }

    public List<ResultDTO> getResultForVoting(Long votingId) {
        List<Answer> votingAnswers = answerRepository.findAnswersByVotingVotingId(votingId);
        List<ResultDTO> resultDTOS = new ArrayList<>();

        votingAnswers.forEach(answer -> {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setName(answer.getAnswer());
            resultDTO.setAnswerId(answer.getAnswerId());
            List<VotingResult> votingResultsForAnswer = votingResultRepository.findVotingResultsByAnswerAnswerId(answer.getAnswerId());
            resultDTO.setValue(votingResultsForAnswer.size());
            List<String> usernames = new ArrayList<>();
            if (votingRepository.getById(votingId).getExplicit()) {
                votingResultsForAnswer.forEach(votingResult -> usernames.add(votingResult.getUser().getUsername()));
                resultDTO.setUsernames(usernames);
            }
            resultDTOS.add(resultDTO);
        });
        return resultDTOS;
    }

    public VotingTokenDTO getVotingToken(Long votingId) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        VotingTokenDTO tokenDTO = new VotingTokenDTO();

        String votingToken = JWT.create()
                .withSubject(votingRepository.getById(votingId).getVotingId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(algorithm);
        tokenDTO.setToken(votingToken);

        return tokenDTO;
    }

    private Voting getVotingFromVotingDTO(EditVotingDTO editVotingDTO) {
        Voting votingToEdit = votingRepository.getById(editVotingDTO.getVotingId());
        votingToEdit.setVotingName(editVotingDTO.getVotingName());
        votingToEdit.setEndDate(editVotingDTO.getEndDate());
        votingToEdit.setRestricted(editVotingDTO.getRestricted());
        votingToEdit.setQuestion(editVotingDTO.getQuestion());
        votingToEdit.setActive(true);
        votingToEdit.setExplicit(editVotingDTO.getExplicit());
        return votingToEdit;
    }

    private List<VotingDTO> mapListOfVotingToVotingDTO(List<Voting> votingList) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<VotingDTO> votingDTOList = new ArrayList<>();
        votingList.forEach(voting -> {
            VotingDTO votingDTO = new VotingDTO();
            votingDTO.setVotingId(voting.getVotingId());
            votingDTO.setVotingName(voting.getVotingName());
            votingDTO.setActive(voting.getActive());
            votingDTO.setExplicit(voting.getExplicit());
            votingDTO.setRestricted(voting.getRestricted());
            if (voting.getEndDate() != null) {
                String endDate = dateFormat.format(voting.getEndDate());
                votingDTO.setEndDate(endDate);
            }
            votingDTO.setQuestion(voting.getQuestion());
            votingDTOList.add(votingDTO);
        });
        return votingDTOList;
    }

    private void addEditAnswers(Voting votingToEdit, List<AnswerDTO> answers) {
        final Voting addedVoting = votingRepository.save(votingToEdit);

        answers.forEach(answerDTO -> {
            Answer answerToAdd = new Answer();
            answerToAdd.setVoting(addedVoting);
            answerToAdd.setAnswer(answerDTO.getAnswer());
            answerRepository.save(answerToAdd);
        });
    }

    private VotingWithAnswersDTO getVotingWithAnswersDTOFromVoting(Long votingId) {
        VotingWithAnswersDTO votingWithAnswersDTO = new VotingWithAnswersDTO();
        votingWithAnswersDTO.setVotingId(votingId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        votingWithAnswersDTO.setQuestion(votingRepository.getById(votingId).getQuestion());
        votingWithAnswersDTO.setVotingName(votingRepository.getById(votingId).getVotingName());
        votingWithAnswersDTO.setActive(votingRepository.getById(votingId).getActive());
        votingWithAnswersDTO.setExplicit(votingRepository.getById(votingId).getExplicit());
        votingWithAnswersDTO.setRestricted(votingRepository.getById(votingId).getRestricted());
        if(votingRepository.getById(votingId).getEndDate() != null) {
            String endDate = dateFormat.format(votingRepository.getById(votingId).getEndDate());
            System.out.println(endDate);
            votingWithAnswersDTO.setEndDate(endDate);
        }
        return votingWithAnswersDTO;
    }

    private Voting getVotingFromAddVotingDTO(AddVotingDTO addVotingDTO) {
        Voting votingToAdd = new Voting();
        votingToAdd.setVotingName(addVotingDTO.getVotingName());
        votingToAdd.setQuestion(addVotingDTO.getQuestion());
        User userToAddVoting = new User();
        votingToAdd.setRestricted(addVotingDTO.getRestricted());
        votingToAdd.setEndDate(addVotingDTO.getEndDate());
        votingToAdd.setActive(true);
        votingToAdd.setExplicit(addVotingDTO.getExplicit());
        userToAddVoting.setUserId(addVotingDTO.getUserId());
        votingToAdd.setUser(userToAddVoting);
        return votingToAdd;
    }

    private void deleteResultForVoting(Long votingId) {
        votingResultRepository.deleteAll(votingResultRepository.findVotingResultsByVotingVotingId(votingId));
    }

    private void deleteAnswersForVoting(Long votingId) {
        answerRepository.deleteAll(answerRepository.findAnswersByVotingVotingId(votingId));
    }
}
