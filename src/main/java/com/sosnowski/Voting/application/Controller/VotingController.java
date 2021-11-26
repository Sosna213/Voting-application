package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTOs.VotingWithAnswersDTO;
import com.sosnowski.Voting.application.Entity.Answer;
import com.sosnowski.Voting.application.Entity.Voting;
import com.sosnowski.Voting.application.Service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @PostMapping("/voting/add")
    public ResponseEntity<Voting> addVoting(@RequestBody Voting voting){
        Voting returnedVoting = votingService.addVoting(voting);
        return ResponseEntity.ok().body(returnedVoting);
    }
    @PostMapping("/voting/add-answers")
    public ResponseEntity<List<Answer>> addAnswerToVoting(@RequestBody List<Answer> answers){
        List<Answer> resultAnswers = votingService.addAnswersToVoting(answers);
        return ResponseEntity.ok().body(resultAnswers);
    }
    @GetMapping("/voting/{votingId}")
    public ResponseEntity<VotingWithAnswersDTO> getVotingByIdWithAnswers(@PathVariable Long votingId){
        VotingWithAnswersDTO votingWithAnswersDTO = votingService.getVotingWithAnswers(votingId);
        return ResponseEntity.ok().body(votingWithAnswersDTO);
    }
}
