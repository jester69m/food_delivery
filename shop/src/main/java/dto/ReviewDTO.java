package dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {

    private Long authorId;
    private Long shopId;
    private String content;
    private double rating;
    private LocalDateTime createdAt;
}
