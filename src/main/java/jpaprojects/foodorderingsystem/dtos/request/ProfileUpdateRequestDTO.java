package jpaprojects.foodorderingsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequestDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
