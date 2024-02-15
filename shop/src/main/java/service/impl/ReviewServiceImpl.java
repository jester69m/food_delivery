package service.impl;

import dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ReviewRepository;
import service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    public void createReview(ReviewDTO reviewRequest) {

    }
}
