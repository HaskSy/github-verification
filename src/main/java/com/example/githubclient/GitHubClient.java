package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class GitHubClient {

    static final String API_BASE_URL = "https://api.github.com/";
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";
    static final String JSON_CONTENT_TYPE = "application/json";

    private final String accessToken;

    private final GitHubApiInterface service;

    public GitHubClient() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubApiInterface.class);
        BufferedReader br = new BufferedReader(new FileReader("token"));
        this.accessToken = "token " + br.readLine(); // System.getenv("ACCESS_TOKEN");
        br.close();
    }

    public List<Pull> getUserRepoPulls(String repo, String owner) throws IOException {
        Call<List<Pull>> retrofitCall = service.listUserRepoPulls(accessToken, API_VERSION_SPEC, repo, owner);

        Response<List<Pull>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public List<CommitNode> getCommitNodes(String repo, String owner, int number) throws IOException {
        Call<List<CommitNode>> retrofitCall = service.listCommits(accessToken, API_VERSION_SPEC, repo, owner, number);

        Response<List<CommitNode>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<ReviewComment> getPullReview(String repo, String owner, int number) throws IOException {
        Call<List<ReviewComment>> retrofitCall = service.listReviewComments(accessToken, API_VERSION_SPEC, repo, owner, number);

        Response<List<ReviewComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<IssueComment> getPullIssue(String repo, String owner, int number) throws IOException {
        Call<List<IssueComment>> retrofitCall = service.listIssueComments(accessToken, API_VERSION_SPEC, repo, owner, number);

        Response<List<IssueComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }
}