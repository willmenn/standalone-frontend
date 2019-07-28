package com.example.standalone.cache;

import lombok.Value;

import java.io.Serializable;

@Value
public class UserToken implements Serializable {
    private String username;
    private String token;
}
