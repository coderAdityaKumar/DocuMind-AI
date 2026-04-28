package com.aditya.documind.DTO;

import com.aditya.documind.Entity.Organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserResponse {
    private String name;
    private String email;
    private String organizationName;
}
