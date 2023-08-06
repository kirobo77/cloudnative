package com.example.cloudnative.usersws.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.cloudnative.usersws.client.OrderServiceClient;
import com.example.cloudnative.usersws.dto.UserDto;
import com.example.cloudnative.usersws.entity.UserEntity;
import com.example.cloudnative.usersws.model.OrderResponseModel;
import com.example.cloudnative.usersws.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UsersRepository repository;

    Environment env;

    OrderServiceClient orderServiceClient;

    @Autowired
    public UserServiceImpl(UsersRepository repository,
                            OrderServiceClient orderServiceClient,
                            Environment env) {
        this.repository = repository;
        this.orderServiceClient = orderServiceClient;
        this.env = env;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        repository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email);

        if (userEntity == null)
            log.error(String.format(""));

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = repository.findByUserId(userId);

        List<OrderResponseModel> ordersList = orderServiceClient.getOrders(userId);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return repository.findAll();
    }
}
