package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Product image information with binary data")
public class ProductImageDto {

    @Schema(description = "Image ID", example = "1")
    private Long id;

    @Schema(description = "Image binary data (base64 encoded or byte array)")
    private byte[] imageData;

    @Schema(description = "Original file name", example = "tshirt-front.jpg")
    private String fileName;

    @Schema(description = "MIME type of the image", example = "image/jpeg")
    private String mimeType;

    @Schema(description = "File size in bytes", example = "102400")
    private Long fileSize;

    @Schema(description = "Whether this is the primary/main image for the product")
    private Boolean primaryImage;
}
