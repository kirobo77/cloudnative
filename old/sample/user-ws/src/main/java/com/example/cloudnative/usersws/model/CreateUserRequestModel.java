package com.example.cloudnative.usersws.model;

import lombok.Data;

@Data
public class CreateUserRequestModel {
    private String name;
    private String pwd;
    private String email;
}

