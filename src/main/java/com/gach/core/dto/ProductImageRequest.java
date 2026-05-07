package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Payload for a product image upload")
public class ProductImageRequest {

    @Schema(description = "Image file name or storage path", example = "products/12345-front.jpg", required = true)
    private String fileName;

    @Schema(description = "Image MIME type", example = "image/jpeg", required = true)
    private String mimeType;

    @Schema(description = "File size in bytes", example = "102400")
    private Long fileSize;

    @Schema(description = "Image binary data encoded as Base64", example = "<base64 string>", required = true)
    private String imageData;

    @Schema(description = "Whether this image is the primary image for the product")
    private Boolean primaryImage = false;
}
