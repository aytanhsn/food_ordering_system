package jpaprojects.foodorderingsystem.ServiceLayersTesting;

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
import jpaprojects.foodorderingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserConverter userConverter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer_Success() {
        UserRegisterRequestDTO request = new UserRegisterRequestDTO("Ali", "Aliyev", "ali@gmail.com", "1234", "123456789");
        User user = new User();
        User savedUser = new User();
        UserResponseDTO responseDTO = new UserResponseDTO();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded1234");
        when(userConverter.toEntity(request, Role.CUSTOMER, "encoded1234")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userConverter.toDto(savedUser)).thenReturn(responseDTO);

        UserResponseDTO result = userService.registerCustomer(request);

        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    void testRegisterCustomer_EmailAlreadyExists() {
        UserRegisterRequestDTO request = new UserRegisterRequestDTO("Ali", "Aliyev", "ali@gmail.com", "1234", "123456789");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerCustomer(request));
    }

    @Test
    void testLogin_Success() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("ali@gmail.com");
        loginRequest.setPassword("1234");

        User user = new User();
        user.setEmail("ali@gmail.com");
        user.setPassword("encoded1234");
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail("ali@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded1234")).thenReturn(true);
        when(jwtUtil.generateToken(any(Authentication.class))).thenReturn("jwt-token");

        LoginResponseDTO response = userService.login(loginRequest);

        assertEquals("jwt-token", response.getToken());
        assertEquals("Müştəri uğurla daxil oldu!", response.getMessage());
    }

    @Test
    void testLogin_InvalidPassword() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("ali@gmail.com");
        loginRequest.setPassword("wrong");

        User user = new User();
        user.setEmail("ali@gmail.com");
        user.setPassword("encoded1234");

        when(userRepository.findByEmail("ali@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded1234")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.login(loginRequest));
    }

    @Test
    void testUpdateProfile_Success() {
        // Mock current user in SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("ali@gmail.com", null));

        ProfileUpdateRequestDTO updateRequest = new ProfileUpdateRequestDTO();
        updateRequest.setFirstName("Yeni");
        updateRequest.setLastName("Soyad");
        updateRequest.setPhoneNumber("987654321");
        updateRequest.setPassword("newpass");

        User user = new User();
        user.setEmail("ali@gmail.com");

        when(userRepository.findByEmail("ali@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedPass");

        String result = userService.updateProfile(updateRequest);

        assertEquals("Profil uğurla yeniləndi!", result);
        verify(userRepository).save(user);
        assertEquals("Yeni", user.getFirstName());
        assertEquals("Soyad", user.getLastName());
        assertEquals("987654321", user.getPhoneNumber());
        assertEquals("encodedPass", user.getPassword());
    }
}
