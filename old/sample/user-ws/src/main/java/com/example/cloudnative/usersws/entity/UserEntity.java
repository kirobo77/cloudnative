package com.example.cloudnative.usersws.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("USERS")
public class UserEntity implements Serializable {
    @Id
    private long id;

    private String name;

    private String email;
    
    private String pwd;

    private String userId;

    private Date createdAt;
}
