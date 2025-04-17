package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.dtos.request.ReviewRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.ReviewResponseDTO;
import jpaprojects.foodorderingsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReviewResponseDTO> submitReview(@RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.submitReview(dto));
    }

    @GetMapping("/{targetType}/{targetId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForTarget(@PathVariable String targetType,
                                                                       @PathVariable Long targetId) {
        return ResponseEntity.ok(reviewService.getReviewsForTarget(targetType, targetId));
    }
}
