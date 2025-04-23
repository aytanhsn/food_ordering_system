package jpaprojects.foodorderingsystem.ServiceLayersTesting;
import jpaprojects.foodorderingsystem.convertor.RestaurantConverter;
import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.RestaurantUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import jpaprojects.foodorderingsystem.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantConverter restaurantConverter;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: Get all restaurants
    @Test
    void testGetAllRestaurants() {
        Restaurant restaurant1 = new Restaurant();
        Restaurant restaurant2 = new Restaurant();
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(restaurantRepository.findAll()).thenReturn(restaurants);
        when(restaurantConverter.toResponseDTOList(restaurants)).thenReturn(
                Arrays.asList(new RestaurantResponseDTO(), new RestaurantResponseDTO())
        );

        List<RestaurantResponseDTO> response = restaurantService.getAllRestaurants();

        assertEquals(2, response.size());
        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantConverter, times(1)).toResponseDTOList(restaurants);
    }

    // ✅ Test: Add valid restaurant
    @Test
    void testAddRestaurant_Success() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Test Restaurant");
        request.setCategory("ITALIAN");

        Restaurant restaurantEntity = new Restaurant();
        when(restaurantConverter.toEntity(request)).thenReturn(restaurantEntity);

        String result = restaurantService.addRestaurant(request);

        assertEquals("Restoran uğurla əlavə edildi!", result);
        verify(restaurantRepository, times(1)).save(restaurantEntity);
    }

    // ✅ Test: Add restaurant with empty name
    @Test
    void testAddRestaurant_EmptyName() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("");
        request.setCategory("ITALIAN");

        String result = restaurantService.addRestaurant(request);

        assertEquals("Restoran adı boş ola bilməz!", result);
        verify(restaurantRepository, never()).save(any());
    }

    // ✅ Test: Add restaurant with invalid category
    @Test
    void testAddRestaurant_InvalidCategory() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Some name");
        request.setCategory("UNKNOWN_CATEGORY");

        String result = restaurantService.addRestaurant(request);

        assertEquals("Yanlış kateqoriya dəyəri!", result);
        verify(restaurantRepository, never()).save(any());
    }

    // ✅ Test: Update existing restaurant
    @Test
    void testUpdateRestaurant_Success() {
        Long restaurantId = 1L;
        Restaurant existing = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existing));

        RestaurantUpdateRequestDTO updateDTO = new RestaurantUpdateRequestDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setAddress("New Address");
        updateDTO.setPhoneNumber("12345678");
        updateDTO.setRating(4.5);

        String result = restaurantService.updateRestaurant(restaurantId, updateDTO);

        assertEquals("Restoran uğurla yeniləndi!", result);
        assertEquals("Updated Name", existing.getName());
        assertEquals("New Address", existing.getAddress());
        assertEquals("12345678", existing.getPhoneNumber());
        assertEquals(4.5, existing.getRating());
        verify(restaurantRepository, times(1)).save(existing);
    }

    // ✅ Test: Update non-existing restaurant
    @Test
    void testUpdateRestaurant_NotFound() {
        Long restaurantId = 100L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        RestaurantUpdateRequestDTO updateDTO = new RestaurantUpdateRequestDTO();
        updateDTO.setName("Updated");

        String result = restaurantService.updateRestaurant(restaurantId, updateDTO);

        assertEquals("Restoran tapılmadı!", result);
        verify(restaurantRepository, never()).save(any());
    }
}
