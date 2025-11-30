package com.hms.user.dto;

import com.hms.user.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Enter a valid email")
    private String email;
    @NotBlank(message = "Password is mandatory")

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,15}$", message = "Password " +
            "should" +
            " contain at least 1 uppercase, 1 lowercase, 1 digit and 1 special character")
    private String password;

    @Enumerated(value = EnumType.ORDINAL)
    private Roles role;
    private Long profileId;

    //conversion from DTO to Entity
    public User toUserEntity() {
        return new User(this.id, this.name, this.email, this.password, this.role,this.profileId);
    }
}
