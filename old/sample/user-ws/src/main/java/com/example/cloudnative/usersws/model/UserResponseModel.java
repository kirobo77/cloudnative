package com.example.cloudnative.usersws.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseModel {
    private String userId;
    private String name;
    private String email;

    private List<OrderResponseModel> orders;
}
