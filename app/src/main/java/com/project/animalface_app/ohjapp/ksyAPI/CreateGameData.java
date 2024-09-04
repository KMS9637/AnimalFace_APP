package com.project.animalface_app.ohjapp.ksyAPI;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class CreateGameData {

    private Long createGameNo;
    private String createGameName;
    private String createQuestion;
    private String createAnswer;
    private String createResult;

    public CreateGameData(Long createGameNo, String createGameName, String createQuestion,
                          String createAnswer, String createResult){
        this.createGameNo = createGameNo;
        this.createGameName = createGameName;
        this.createQuestion = createQuestion;
        this.createAnswer = createAnswer;
        this.createResult = createResult;
    }
}
