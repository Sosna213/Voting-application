package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTOs.*;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @PostMapping("/voting/add")
    public ResponseEntity<AddVotingDTO> addVoting(@RequestBody AddVotingDTO addVotingDTO) {
        AddVotingDTO returnedVoting = votingService.addVoting(addVotingDTO);
        return ResponseEntity.ok().body(returnedVoting);
    }

    @GetMapping("/voting/{votingId}")
    public ResponseEntity<VotingWithAnswersDTO> getVotingByIdWithAnswers(@PathVariable Long votingId) {
        VotingWithAnswersDTO votingWithAnswersDTO = votingService.getVotingWithAnswers(votingId);
        return ResponseEntity.ok().body(votingWithAnswersDTO);
    }

    @GetMapping("/voting/forUser/{username}")
    public ResponseEntity<List<VotingDTO>> getVotingForUser(@PathVariable String username) {
        List<VotingDTO> votingList = votingService.getVotingByUsername(username);
        return ResponseEntity.ok().body(votingList);
    }

    @DeleteMapping("/voting/delete/{votingId}")
    public ResponseEntity<Long> deleteVoting(@PathVariable Long votingId) {
        Long deleteVotingId = votingService.deleteVotingAndAnswers(votingId);
        return ResponseEntity.ok().body(deleteVotingId);
    }

    @PutMapping("/voting-edit")
    public ResponseEntity<EditVotingDTO> editVoting(@RequestBody EditVotingDTO editVotingDTO) {
        EditVotingDTO votingEdited = votingService.editVoting(editVotingDTO);
        return ResponseEntity.ok().body(votingEdited);
    }

    @PostMapping("/shareToUser")
    public ResponseEntity<Long> shareVotingToUser(@RequestBody ShareVotingToUserDTO shareVotingToUserDTO) {
        Long addedUserId = votingService.shareVotingToUser(shareVotingToUserDTO);
        return ResponseEntity.ok().body(addedUserId);
    }
    @GetMapping("/sharedUsers/{votingId}")
    public ResponseEntity<Collection<User>> shareVotingToUser(@PathVariable Long votingId) {
        Collection<User> sharedUsers = votingService.getSharedUsersVoting(votingId);
        return ResponseEntity.ok().body(sharedUsers);
    }
    @GetMapping("/votingSharedToUser/{username}")
    public ResponseEntity<List<SharedVotingDTO>> votingSharedToUser(@PathVariable String username){
        List<SharedVotingDTO> votingList = votingService.getVotingSharedToUser(username);
        return ResponseEntity.ok().body(votingList);
    }
}
