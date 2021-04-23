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
}
