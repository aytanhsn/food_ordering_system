//package jpaprojects.foodorderingsystem.controller;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jpaprojects.foodorderingsystem.dto.request.UserRegisterRequestDTO;
//import jpaprojects.foodorderingsystem.dtos.request.LoginRequestDTO;
//import jpaprojects.foodorderingsystem.dtos.request.ProfileUpdateRequestDTO;
//import jpaprojects.foodorderingsystem.dtos.response.LoginResponseDTO;
//import jpaprojects.foodorderingsystem.dtos.response.UserResponseDTO;
//import jpaprojects.foodorderingsystem.enums.Role;
//import jpaprojects.foodorderingsystem.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private UserRegisterRequestDTO registerRequest;
//    private UserResponseDTO userResponse;
//    private LoginRequestDTO loginRequest;
//    private LoginResponseDTO loginResponse;
//    private ProfileUpdateRequestDTO updateRequest;
//
//    @BeforeEach
//    void setUp() {
//        registerRequest = new UserRegisterRequestDTO("John", "Doe", "john@example.com", "password", "0501234567");
//
//        userResponse = UserResponseDTO.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Doe")
//                .email("john@example.com")
//                .phoneNumber("0501234567")
//                .role(Role.CUSTOMER)
//                .build();
//
//        loginRequest = new LoginRequestDTO();
//        loginRequest.setEmail("john@example.com");
//        loginRequest.setPassword("password");
//
//        loginResponse = new LoginResponseDTO("token123", "Müştəri uğurla daxil oldu!");
//
//        updateRequest = new ProfileUpdateRequestDTO();
//        updateRequest.setFirstName("Johnny");
//        updateRequest.setLastName("Doe");
//        updateRequest.setPhoneNumber("0507654321");
//        updateRequest.setPassword("newpass");
//    }
//
//    @Test
//    void registerCustomer_shouldReturnUserResponse() throws Exception {
//        Mockito.when(userService.registerCustomer(any(UserRegisterRequestDTO.class)))
//                .thenReturn(userResponse);
//
//        mockMvc.perform(post("/api/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("john@example.com"))
//                .andExpect(jsonPath("$.role").value("CUSTOMER"));
//    }
//
//    @Test
//    void registerAdmin_shouldReturnUserResponse() throws Exception {
//        Mockito.when(userService.registerAdmin(any(UserRegisterRequestDTO.class)))
//                .thenReturn(userResponse);
//
//        mockMvc.perform(post("/api/users/admin/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("john@example.com"));
//    }
//
//    @Test
//    void registerCourier_shouldReturnUserResponse() throws Exception {
//        Mockito.when(userService.registerCourier(any(UserRegisterRequestDTO.class)))
//                .thenReturn(userResponse);
//
//        mockMvc.perform(post("/api/users/courier/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("john@example.com"));
//    }
//
//    @Test
//    void login_shouldReturnToken() throws Exception {
//        Mockito.when(userService.login(any(LoginRequestDTO.class)))
//                .thenReturn(loginResponse);
//
//        mockMvc.perform(post("/api/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("token123"))
//                .andExpect(jsonPath("$.message").value("Müştəri uğurla daxil oldu!"));
//    }
//
//    @Test
//    void updateProfile_shouldReturnSuccessMessage() throws Exception {
//        Mockito.when(userService.updateProfile(any(ProfileUpdateRequestDTO.class)))
//                .thenReturn("Profil uğurla yeniləndi!");
//
//        mockMvc.perform(put("/api/users/profile")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Profil uğurla yeniləndi!"));
//    }
//}
