package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.dto.request.UserRegisterRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.LoginRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.ProfileUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.LoginResponseDTO;
import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
import jpaprojects.foodorderingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO registerCustomer(@RequestBody UserRegisterRequestDTO dto) {
        return userService.registerCustomer(dto);
    }

    @PostMapping("/admin/register")
    public UserResponseDTO registerAdmin(@RequestBody UserRegisterRequestDTO dto) {
        return userService.registerAdmin(dto);
    }

    @PostMapping("/courier/register")
    public UserResponseDTO registerCourier(@RequestBody UserRegisterRequestDTO dto) {
        return userService.registerCourier(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // Burada LoginResponseDTO-nu birbaşa qaytarırıq, çünki artıq userService.login metodu bu tipdə cavab verir.
        return userService.login(loginRequestDTO);
    }
    @PutMapping("/profile")
    public String updateProfile(@RequestBody ProfileUpdateRequestDTO requestDTO) {
        return userService.updateProfile(requestDTO);
    }
}

