package com.example.cloudnative.usersws.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserResponseModel {
    private String name;
    private String email;
    private String userId;
}
