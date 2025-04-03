package jpaprojects.foodorderingsystem.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequestDTO {
    private String name;
    private String address;
    private String phoneNumber;
}
