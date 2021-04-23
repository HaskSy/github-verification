package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GitHubClientController {

    @Autowired
    private GitHubClient githubService;

    @GetMapping("/repos/{owner}/{repo}/pulls")
    public List<Pull> getPulls(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName) throws IOException {
        return githubService.getUserRepoPulls(repoName, owner);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/commits")
    public List<CommitNode> getCommit(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName,
            @PathVariable("number") int number) throws IOException {
        return githubService.getCommitNodes(repoName, owner, number);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/review")

    public List<ReviewComment> getReviewComment(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName,
            @PathVariable("number") int number) throws IOException {
        return githubService.getPullReview(repoName, owner, number);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/issue")
    public List<IssueComment> getIssueComment(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName,
            @PathVariable("number") int number) throws IOException {
        return githubService.getPullIssue(repoName, owner, number);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/review/post")
    public ReviewComment leaveReviewComment(@PathVariable("owner") String owner,
                                          @PathVariable("repo") String repoName,
                                          @PathVariable("number") int number) throws IOException {

        Pull pull = getPulls(owner, repoName).stream()
                .filter(p -> p.getNumber() == number)
                .collect(Collectors.toList()).get(0);

        // List<CommitNode> commitNodeList = getCommit(owner, repoName, number);

        String messageBody = MessageTemplateVerifier.buildMessage(pull.getTitle());

        return githubService.createPullReview(owner, repoName, number, messageBody);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/issue/post")
    public IssueComment leaveIssueComment(@PathVariable("owner") String owner,
                                      @PathVariable("repo") String repoName,
                                      @PathVariable("number") int number) throws IOException {

        Pull pull = getPulls(owner, repoName).stream()
                .filter(p -> p.getNumber() == number)
                .collect(Collectors.toList()).get(0);

        // List<CommitNode> commitNodeList = getCommit(owner, repoName, number);

        String messageBody = MessageTemplateVerifier.buildMessage(pull.getTitle());

        return githubService.createPullIssue(owner, repoName, number, messageBody);
    }
}
