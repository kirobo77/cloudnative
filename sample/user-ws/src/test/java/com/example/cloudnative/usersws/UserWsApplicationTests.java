package com.example.cloudnative.usersws;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.h2.api.ErrorCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cloudnative.usersws.entity.UserEntity;
import com.example.cloudnative.usersws.repository.UsersRepository;
import com.example.cloudnative.usersws.service.UserService;

@SpringBootTest
class UserWsApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
    @MockBean
    UsersRepository mockRepository;
    
    @Autowired
    UserService userService;
	
    @Test
    void contextLoads() {
    	  
    	Mockito.when(mockRepository.findByUserId("test")).thenReturn(UserEntity.builder().id(10).build());
    	
    	userService.getUserByUserId("test");
    }

}
