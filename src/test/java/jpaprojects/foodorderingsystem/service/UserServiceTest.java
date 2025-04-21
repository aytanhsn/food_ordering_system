//package jpaprojects.foodorderingsystem.service;
//
//import jpaprojects.foodorderingsystem.config.JwtUtil;
//import jpaprojects.foodorderingsystem.convertor.UserConverter;
//import jpaprojects.foodorderingsystem.dto.request.UserRegisterRequestDTO;
//import jpaprojects.foodorderingsystem.dtos.request.LoginRequestDTO;
//import jpaprojects.foodorderingsystem.dtos.request.ProfileUpdateRequestDTO;
//import jpaprojects.foodorderingsystem.dtos.response.LoginResponseDTO;
//import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
//import jpaprojects.foodorderingsystem.entity.User;
//import jpaprojects.foodorderingsystem.enums.Role;
//import jpaprojects.foodorderingsystem.exception.EmailAlreadyExistsException;
//import jpaprojects.foodorderingsystem.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.verification.VerificationMode;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//import java.util.Properties;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private JwtUtil jwtUtil;
//    @Mock
//    private UserConverter userConverter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void shouldRegisterCustomerSuccessfully() {
//        UserRegisterRequestDTO dto = new UserRegisterRequestDTO("Ali", "Aliyev", "ali@example.com", "1234", "0501234567");
//
//        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
//        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
//        when(userConverter.toEntity(dto, Role.CUSTOMER, "encodedPassword")).thenReturn(
//                User.builder().email(dto.getEmail()).build()
//        );
//        when(userRepository.save(any(User.class))).thenReturn(new User());
//        when(userConverter.toDto(any(User.class))).thenReturn(
//                new UserResponseDTO(1L, "Ali", "Aliyev", "ali@example.com", "0501234567", Role.CUSTOMER)
//        );
//
//        UserResponseDTO response = userService.registerCustomer(dto);
//
//        assertEquals("Ali", response.getFirstName());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void shouldThrowExceptionIfEmailExists() {
//        UserRegisterRequestDTO dto = new UserRegisterRequestDTO("Ali", "Aliyev", "ali@example.com", "1234", "0501234567");
//        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);
//
//        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerCustomer(dto));
//    }
//
//    @Test
//    void shouldLoginSuccessfully() {
//        LoginRequestDTO loginRequest = new LoginRequestDTO();
//        loginRequest.setEmail("ali@example.com");
//        loginRequest.setPassword("1234");
//
//        User user = new User();
//        user.setEmail("ali@example.com");
//        user.setPassword("encodedPassword");
//        user.setRole(Role.CUSTOMER);
//
//        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
//        when(jwtUtil.generateToken(user.getEmail(), user.getRole().name())).thenReturn("fake-jwt-token");
//
//        LoginResponseDTO response = userService.login(loginRequest);
//
//        assertEquals("fake-jwt-token", response.getToken());
//    }
//
//    @Test
//    void shouldUpdateProfileSuccessfully() {
//        ProfileUpdateRequestDTO dto = new ProfileUpdateRequestDTO();
//        dto.setFirstName("NewName");
//        dto.setPhoneNumber("0511111111");
//        dto.setPassword("newpass");
//
//        User currentUser = new User();
//        currentUser.setEmail("user@example.com");
//        currentUser.setFirstName("OldName");
//        currentUser.setPhoneNumber("0500000000");
//        currentUser.setPassword("oldPass");
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("user@example.com");
//
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
//        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewPass");
//        when(userRepository.save(any(User.class))).thenReturn(currentUser);
//
//        String result = userService.updateProfile(dto);
//
//        assertEquals("Profil uğurla yeniləndi!", result);
//        assertEquals("NewName", currentUser.getFirstName());
//        assertEquals("0511111111", currentUser.getPhoneNumber());
//        assertEquals("encodedNewPass", currentUser.getPassword());
//    }
//}