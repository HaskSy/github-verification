package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.Pull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.List;

public interface GitHubApiInterface {

    @GET("repos/{owner}/{repo}/pulls")
    Call<List<Pull>> listUserRepoPulls(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                                       @Path("repo") String repo, @Path("owner") String owner);

    @GET("repos/{owner}/{repo}/pulls/{number}/commits")
    Call<List<CommitNode>> listCommits(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                                       @Path("repo") String repo, @Path("owner") String owner, @Path("number") int number);
}