package com.raxrot.courseservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseRequest {
    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 50, message = "Course name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 6, fraction = 2, message = "Price must be a valid amount (max 6 digits and 2 decimals)")
    private BigDecimal price;
}
