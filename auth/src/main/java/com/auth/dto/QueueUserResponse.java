package com.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueUserResponse {

    private String email;
    private String firstName;
    private String lastName;
}
