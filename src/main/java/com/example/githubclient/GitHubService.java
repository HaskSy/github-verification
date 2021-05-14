package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }

    public void sendHelloMessage() throws IOException {
        String owner = "HaskSy";
        String repo = "java_au";

        String titles = client.getUserRepoPulls(owner, repo).stream()
                .map(Pull::getTitle)
                .collect(Collectors.joining(", "));

        client.createPullIssue(owner, repo, 1, titles);
    }

    public void sendVerificationMessage(String owner, String repo, int number) throws IOException {


        /*Pull pull = client.getUserRepoPulls(owner, repo).stream()
                .filter(p -> p.getNumber() == number)
                .collect(Collectors.toList()).get(0);
*/
        List<CommitNode> commitList = client.getCommitNodes(owner, repo, number).stream()
                .sorted(Comparator.comparing(CommitNode::getDate))
                .collect(Collectors.toList());

        List<IssueComment> issueCommentList = client.getPullIssue(owner, repo, number).stream()
                .sorted(Comparator.comparing(IssueComment::getCreationDate))
                .collect(Collectors.toList());

        List<ReviewComment> reviewCommentList = client.getPullReview(owner, repo, number).stream()
                .sorted(Comparator.comparing(ReviewComment::getCreationDate))
                .collect(Collectors.toList());

        String message = MessageTemplateVerifier.buildMessage(issueCommentList, reviewCommentList, commitList);

        if (!message.isEmpty()) {
            client.createPullIssue(owner, repo, number, message);
        }
    }

}
