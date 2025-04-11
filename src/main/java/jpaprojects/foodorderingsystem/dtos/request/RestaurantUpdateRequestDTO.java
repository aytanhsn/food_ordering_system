package jpaprojects.foodorderingsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantUpdateRequestDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private Double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
