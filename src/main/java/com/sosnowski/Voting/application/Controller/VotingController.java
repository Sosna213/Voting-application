package com.sosnowski.Voting.application.Controller;

import com.sosnowski.Voting.application.DTO.*;
import com.sosnowski.Voting.application.Entity.User;
import com.sosnowski.Voting.application.Entity.VotingResult;
import com.sosnowski.Voting.application.Service.VotingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;


    @ApiOperation(value ="Add voting with answers to database", response = AddVotingDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added voting"),
    })
    @PostMapping("/voting/add")
    public ResponseEntity<AddVotingDTO> addVoting(@RequestBody AddVotingDTO addVotingDTO) {
        AddVotingDTO returnedVoting = votingService.addVoting(addVotingDTO);
        return ResponseEntity.ok().body(returnedVoting);
    }

    @ApiOperation(value ="Get voting form database by its id", response = VotingWithAnswersDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved voting"),
    })
    @GetMapping("/voting/{votingId}")
    public ResponseEntity<VotingWithAnswersDTO> getVotingByIdWithAnswers(@PathVariable Long votingId) {
        VotingWithAnswersDTO votingWithAnswersDTO = votingService.getVotingWithAnswers(votingId);
        return ResponseEntity.ok().body(votingWithAnswersDTO);
    }

    @ApiOperation(value ="Get user's voting form database by user id", response = VotingDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved voting"),
    })
    @GetMapping("/voting/forUser/{username}")
    public ResponseEntity<List<VotingDTO>> getVotingForUser(@PathVariable String username) {
        List<VotingDTO> votingList = votingService.getVotingByUsername(username);
        return ResponseEntity.ok().body(votingList);
    }

    @ApiOperation(value ="Delete voting form database by its id", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted voting with answers"),
    })
    @DeleteMapping("/voting/delete/{votingId}")
    public ResponseEntity<Long> deleteVoting(@PathVariable Long votingId) {
        Long deleteVotingId = votingService.deleteVotingAndAnswers(votingId);
        return ResponseEntity.ok().body(deleteVotingId);
    }

    @ApiOperation(value ="Edit voting form database", response = EditVotingDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited voting"),
    })
    @PutMapping("/voting-edit")
    public ResponseEntity<EditVotingDTO> editVoting(@RequestBody EditVotingDTO editVotingDTO) {
        EditVotingDTO votingEdited = votingService.editVoting(editVotingDTO);
        return ResponseEntity.ok().body(votingEdited);
    }

    @ApiOperation(value ="Deactivate voting form", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deactivated voting"),
    })
    @PutMapping("/deactivate-voting/{votingId}")
    public ResponseEntity<Long> deactivateVoting(@PathVariable Long votingId){
        Long deactivatedVoting = votingService.deactivateVoting(votingId);
        return ResponseEntity.ok().body(deactivatedVoting);
    }

    @ApiOperation(value ="Share voting to users by their usernames", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully shared voting"),
    })
    @PostMapping("/shareToUsers")
    public ResponseEntity<List<String>> shareVotingToUsers(@RequestBody ShareVotingToUsersDTO shareVotingToUsersDTO) {
        List<String> addedUserUsernames = votingService.shareVotingToUsers(shareVotingToUsersDTO);
        return ResponseEntity.ok().body(addedUserUsernames);
    }

    @ApiOperation(value ="Get users which can vote on voting by voting id", response = Collection.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved voting"),
    })
    @GetMapping("/sharedUsers/{votingId}")
    public ResponseEntity<Collection<User>> getUsersSharedForVoting(@PathVariable Long votingId) {
        Collection<User> sharedUsers = votingService.getSharedUsersForVoting(votingId);
        return ResponseEntity.ok().body(sharedUsers);
    }

    @ApiOperation(value ="Get voting shared to user by his id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved voting"),
    })
    @GetMapping("/votingSharedToUser/{username}")
    public ResponseEntity<List<SharedVotingDTO>> votingSharedToUser(@PathVariable String username){
        List<SharedVotingDTO> votingList = votingService.getVotingSharedToUser(username);
        return ResponseEntity.ok().body(votingList);
    }

    @ApiOperation(value ="Vote on voting", response = VoteDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully voted"),
    })
    @PostMapping("/vote")
    public ResponseEntity<VoteDTO> vote(@RequestBody VoteDTO vote){
        VotingResult votingResult = votingService.vote(vote);
        VoteDTO dtoToReturn = new VoteDTO();
        return ResponseEntity.ok().body(dtoToReturn);
    }

    @ApiOperation(value ="Get voting result by its id", response = ResultDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved voting result"),
    })
    @GetMapping("/voting-result/{votingId}")
    public ResponseEntity<List<ResultDTO>> getVotingResult(@PathVariable Long votingId){
        List<ResultDTO> resultDTOSToReturn = votingService.getResultForVoting(votingId);
        return ResponseEntity.ok().body(resultDTOSToReturn);
    }

    @ApiOperation(value ="Get voting voting JWT which contains voting data", response = VotingTokenDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved JWT"),
    })
    @GetMapping("/voting/get-token/{votingId}")
    public ResponseEntity<VotingTokenDTO> getVotingToken(@PathVariable Long votingId){
        VotingTokenDTO token = votingService.getVotingToken(votingId);
        return ResponseEntity.ok().body(token);
    }
}
