package com.gach.core.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Utility class for handling file uploads.
 * Supports image validation and local file system storage.
 */
@Component
public class FileUploadUtil {

    private static final String UPLOAD_DIR = "uploads";
    private static final String PRODUCT_DIR = "products";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_MIME_TYPES = {
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/jpg"
    };
    private static final String[] ALLOWED_EXTENSIONS = {
            ".jpg", ".jpeg", ".png", ".webp"
    };

    /**
     * Upload a product image and return the relative file path.
     * @param file The multipart file to upload
     * @param productId The product ID for organization
     * @return The relative file path (e.g., /uploads/products/uuid.jpg)
     * @throws IllegalArgumentException if file is invalid
     * @throws IOException if file write fails
     */
    public String uploadProductImage(MultipartFile file, Long productId) throws IOException {
        // Validation
        validateFile(file);

        // Create directory structure
        Path uploadPath = Paths.get(UPLOAD_DIR, PRODUCT_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String savedFilename = UUID.randomUUID().toString() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(savedFilename);
        Files.write(filePath, file.getBytes());

        // Return relative path for accessing via HTTP
        return "/uploads/products/" + savedFilename;
    }

    /**
     * Delete an uploaded image file.
     * @param relativePath The relative path returned from uploadProductImage
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteProductImage(String relativePath) {
        if (relativePath == null || !relativePath.startsWith("/uploads/products/")) {
            return false;
        }

        try {
            // Remove leading slash and construct full path
            String filename = relativePath.substring(1); // Remove leading /
            Path filePath = Paths.get(filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Validate uploaded file.
     * @param file The file to validate
     * @throws IllegalArgumentException if file is invalid
     */
    private void validateFile(MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    String.format("File size exceeds maximum allowed size of %dMB",
                            MAX_FILE_SIZE / (1024 * 1024)));
        }

        // Check MIME type
        String mimeType = file.getContentType();
        boolean isValidMimeType = false;
        for (String allowedType : ALLOWED_MIME_TYPES) {
            if (mimeType != null && mimeType.equalsIgnoreCase(allowedType)) {
                isValidMimeType = true;
                break;
            }
        }

        if (!isValidMimeType) {
            throw new IllegalArgumentException(
                    "Invalid file type. Allowed types: JPEG, PNG, WebP");
        }

        // Check file extension
        String filename = file.getOriginalFilename();
        String fileExtension = getFileExtension(filename).toLowerCase();
        boolean isValidExtension = false;
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (fileExtension.equalsIgnoreCase(allowedExt)) {
                isValidExtension = true;
                break;
            }
        }

        if (!isValidExtension) {
            throw new IllegalArgumentException(
                    "Invalid file extension. Allowed extensions: .jpg, .jpeg, .png, .webp");
        }
    }

    /**
     * Extract file extension from filename.
     * @param filename The filename
     * @return The file extension including dot (e.g., ".jpg")
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Invalid filename: " + filename);
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * Get the full file path for a relative upload path.
     * @param relativePath The relative path (e.g., /uploads/products/uuid.jpg)
     * @return The full file path
     */
    public Path getFullFilePath(String relativePath) {
        // Remove leading slash
        String path = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
        return Paths.get(path);
    }

    /**
     * Check if a file exists.
     * @param relativePath The relative upload path
     * @return true if file exists, false otherwise
     */
    public boolean fileExists(String relativePath) {
        try {
            return Files.exists(getFullFilePath(relativePath));
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Static helper method: Save uploaded file without product ID.
     * Used for generic file uploads (e.g., product images without pre-existing product).
     * @param file The multipart file to upload
     * @return The relative file path (e.g., /uploads/products/uuid.jpg)
     * @throws IOException if file write fails
     */
    public static String saveUploadedFile(MultipartFile file) throws IOException {
        FileUploadUtil util = new FileUploadUtil();
        return util.uploadProductImage(file, null);
    }
}
