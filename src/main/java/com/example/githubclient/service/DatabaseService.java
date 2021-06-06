package com.example.githubclient.service;

import com.example.githubclient.model.db.dbStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<dbStudent> getStudents() {
        return jdbcTemplate.query(
                "SELECT * FROM student",
                (rs, rowNum) ->
                        new dbStudent(
                                rs.getInt("student_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("login")
                        )
        );
    }

    public List<List<String>> getRepoLogin() {
        String sql = "SELECT name, login FROM repo INNER JOIN student ON student.student_id = repo.student_id";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        Arrays.asList(rs.getString("name"), rs.getString("login"))
        );
    }

    public void deleteStudent(int id){
        String sqlQuery = "DELETE FROM student WHERE student_id=?";
        jdbcTemplate.update(sqlQuery, id);

    }
    public void addStudent(int id, String firstName, String lastName, String login){
        String sql = "INSERT INTO student(student_id, first_name, last_name, login) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, id, firstName, lastName, login);
    }
}