package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.config.JwtUtil;
import jpaprojects.foodorderingsystem.convertor.UserConverter;
import jpaprojects.foodorderingsystem.dto.request.UserRegisterRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.LoginRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.ProfileUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.LoginResponseDTO;
import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
import jpaprojects.foodorderingsystem.entity.User;
import jpaprojects.foodorderingsystem.enums.Role;
import jpaprojects.foodorderingsystem.exception.EmailAlreadyExistsException;
import jpaprojects.foodorderingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserConverter userConverter;

    // Müştəri qeydiyyatı
    public UserResponseDTO registerCustomer(UserRegisterRequestDTO requestDTO) {
        return registerWithRole(requestDTO, Role.CUSTOMER);
    }

    // Admin qeydiyyatı
    public UserResponseDTO registerAdmin(UserRegisterRequestDTO requestDTO) {
        return registerWithRole(requestDTO, Role.ADMIN);
    }

    // Kuryer qeydiyyatı
    public UserResponseDTO registerCourier(UserRegisterRequestDTO requestDTO) {
        return registerWithRole(requestDTO, Role.COURIER);
    }

    // Qeydiyyat üçün ümumi metod
    private UserResponseDTO registerWithRole(UserRegisterRequestDTO requestDTO, Role role) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        User user = userConverter.toEntity(requestDTO, role, encodedPassword);
        User savedUser = userRepository.save(user);

        return userConverter.toDto(savedUser);
    }

    // Login əməliyyatı
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword() ,
                authorities
        );
        String token = jwtUtil.generateToken(authentication);
        return new LoginResponseDTO(token, "Müştəri uğurla daxil oldu!");
    }

    // Profil yeniləmə əməliyyatı
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
