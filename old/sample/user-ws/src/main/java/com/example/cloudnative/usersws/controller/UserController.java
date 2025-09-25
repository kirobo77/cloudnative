package com.example.cloudnative.usersws.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudnative.usersws.dto.UserDto;
import com.example.cloudnative.usersws.entity.UserEntity;
import com.example.cloudnative.usersws.model.CreateUserRequestModel;
import com.example.cloudnative.usersws.model.CreateUserResponseModel;
import com.example.cloudnative.usersws.model.UserResponseModel;
import com.example.cloudnative.usersws.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user-ms")
public class UserController {
    @Autowired
    private Environment env;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String health() {
        return "Hi, there. I'm an Users microservice";
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {
    	log.info("create user");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createDto = userService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createDto, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value="/users")
    public ResponseEntity<List<UserResponseModel>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        log.info("get users");
        List<UserResponseModel> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, UserResponseModel.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value="/users/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
    	log.info("get user");
        UserDto userDto = userService.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
