package com.br.chagas.midnights_fm.controller;

import com.br.chagas.midnights_fm.dto.request.ReviewRequestDTO;
import com.br.chagas.midnights_fm.dto.response.ReviewResponseDTO;
import com.br.chagas.midnights_fm.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReviewResponseDTO> findAllReviews(@PathVariable Integer page, @PathVariable Integer size) {
        return reviewService.findAllReviews(page, size);
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO findReviewById(@PathVariable Integer id) {
        return reviewService.findMyReviewByid(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDTO createReview(@PathVariable Integer id,
                                          @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewService.createReview(id, reviewRequestDTO);
    }
}
