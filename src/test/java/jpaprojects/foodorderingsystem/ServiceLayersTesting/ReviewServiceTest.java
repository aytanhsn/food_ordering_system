package jpaprojects.foodorderingsystem.ServiceLayersTesting;

import jpaprojects.foodorderingsystem.dtos.request.ReviewRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.ReviewResponseDTO;
import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.entity.Review;
import jpaprojects.foodorderingsystem.entity.User;
import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import jpaprojects.foodorderingsystem.repository.OrderRepository;
import jpaprojects.foodorderingsystem.repository.PaymentRepository;
import jpaprojects.foodorderingsystem.repository.ReviewRepository;
import jpaprojects.foodorderingsystem.repository.UserRepository;
import jpaprojects.foodorderingsystem.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set SecurityContext mock
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void submitReview_success() {
        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .comment("Very good")
                .rating(5)
                .targetType(ReviewTargetType.RESTAURANT)
                .targetId(1L)
                .build();

        User mockUser = User.builder().id(1L).email("user@example.com").build();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));

        Order order = Order.builder().id(1L).customer(mockUser).build();
        when(orderRepository.checkAccessReview(mockUser.getId(), 1L)).thenReturn(List.of(order));

        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        ReviewResponseDTO response = reviewService.submitReview(dto);

        assertEquals("Very good", response.getComment());
        assertEquals(5, response.getRating());
        assertEquals(ReviewTargetType.RESTAURANT, response.getTargetType());
    }

    @Test
    void submitReview_fails_whenNoOrders() {
        ReviewRequestDTO dto = ReviewRequestDTO.builder()
                .comment("Nice")
                .rating(4)
                .targetType(ReviewTargetType.RESTAURANT)
                .targetId(99L)
                .build();

        User user = User.builder().id(1L).email("user@example.com").build();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.checkAccessReview(user.getId(), 99L)).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.submitReview(dto);
        });

        assertEquals("Rəy yalnız çatdırılmış və ödənişi tamamlanmış sifarişlərə yazıla bilər.", exception.getMessage());
    }

    @Test
    void getReviewsForTarget_returnsReviews() {
        Review review1 = Review.builder()
                .id(1L)
                .comment("Great!")
                .rating(5)
                .targetType(ReviewTargetType.COURIER)
                .targetId(10L)
                .customer(User.builder().id(1L).email("user@example.com").build())
                .createdAt(LocalDateTime.now())
                .build();

        when(reviewRepository.findByTargetTypeAndTargetId(ReviewTargetType.COURIER, 10L))
                .thenReturn(List.of(review1));

        List<ReviewResponseDTO> results = reviewService.getReviewsForTarget("courier", 10L);

        assertEquals(1, results.size());
        assertEquals("Great!", results.get(0).getComment());
    }
}










