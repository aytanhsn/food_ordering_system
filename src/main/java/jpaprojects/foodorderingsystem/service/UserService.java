package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.config.JwtUtil;
import jpaprojects.foodorderingsystem.dto.request.UserRegisterRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.LoginRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.ProfileUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.LoginResponseDTO;
import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
import jpaprojects.foodorderingsystem.entity.User;
import jpaprojects.foodorderingsystem.enums.Role;
import jpaprojects.foodorderingsystem.exception.EmailAlreadyExistsException;
import jpaprojects.foodorderingsystem.mapper.UserMapper;
import jpaprojects.foodorderingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper; // MapStruct bu instance-ı Spring-ə inject etdirəcək

    public UserResponseDTO registerCustomer(UserRegisterRequestDTO requestDTO) {
        return registerWithRole(requestDTO, Role.CUSTOMER);
    }

    public UserResponseDTO registerAdmin(UserRegisterRequestDTO requestDTO) {
        return registerWithRole(requestDTO, Role.ADMIN);
    }

    public UserResponseDTO registerCourier(UserRegisterRequestDTO requestDTO) {
        return registerWithRole(requestDTO, Role.COURIER);
    }

    private UserResponseDTO registerWithRole(UserRegisterRequestDTO requestDTO, Role role) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        User user = userMapper.toEntity(requestDTO); // INSTANCE yox, Spring-in verdiyi obyekt!
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(token, "Müştəri uğurla daxil oldu!");
    }

    public String updateProfile(ProfileUpdateRequestDTO requestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı."));

        if (requestDTO.getFirstName() != null) {
            user.setFirstName(requestDTO.getFirstName());
        }

        if (requestDTO.getLastName() != null) {
            user.setLastName(requestDTO.getLastName());
        }

        if (requestDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(requestDTO.getPhoneNumber());
        }

        if (requestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }

        userRepository.save(user);

        return "Profil uğurla yeniləndi!";
    }
}
