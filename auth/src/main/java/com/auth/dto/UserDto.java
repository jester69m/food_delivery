package com.auth.dto;

import com.auth.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotBlank
    @Email
    private String email;
    @Size(max = 255)
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;

}
