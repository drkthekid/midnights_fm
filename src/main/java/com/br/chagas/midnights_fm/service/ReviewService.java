package com.br.chagas.midnights_fm.service;

import com.br.chagas.midnights_fm.database.entities.AlbumEntity;
import com.br.chagas.midnights_fm.database.entities.ReviewEntity;
import com.br.chagas.midnights_fm.database.entities.UserEntity;
import com.br.chagas.midnights_fm.database.repository.AlbumRepository;
import com.br.chagas.midnights_fm.database.repository.ReviewRepository;
import com.br.chagas.midnights_fm.database.repository.UserRepository;
import com.br.chagas.midnights_fm.dto.request.ReviewRequestDTO;
import com.br.chagas.midnights_fm.dto.response.ReviewResponseDTO;
import com.br.chagas.midnights_fm.exception.NotFoundException;
import com.br.chagas.midnights_fm.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public Page<ReviewResponseDTO> findAllReviews(Integer page, Integer size) {
        Page<ReviewEntity> reviews = reviewRepository.findAll(PageRequest.of(page, size));

        return reviews.map(r -> ReviewResponseDTO.builder()
                .id(r.getId())
                .commentary(r.getCommentary())
                .assessment(r.getAssessment())
                .albumId(r.getAlbum().getId())
                .userId(r.getUser().getId())
                .build()
        );
    }

    public ReviewResponseDTO createReview(Integer id, ReviewRequestDTO reviewRequestDTO) {

        // authenticate validation
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // inside get user request and apply as creator review
        if (authentication == null || !((authentication.getPrincipal()) instanceof UserEntity user)) {
            throw new UnauthorizedException("User not authenticated or invalid session");
        }

        // finding album or return error
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album not found"));

        ReviewEntity review = ReviewEntity.builder()
                .commentary(reviewRequestDTO.getCommentary())
                .assessment(reviewRequestDTO.getAssessment())
                .album(album)
                .user(user) // authenticated
                .build();

        // store to the database
        reviewRepository.save(review);

        // return dto
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .commentary(review.getCommentary())
                .assessment(review.getAssessment())
                .albumId(review.getAlbum().getId())
                .userId(review.getUser().getId())
                .build();
    }

}
