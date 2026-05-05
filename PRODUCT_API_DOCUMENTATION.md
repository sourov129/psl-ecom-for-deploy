# E-Commerce Platform - Product & Order Management API Documentation

## Project Overview
This is a complete e-commerce platform for selling clothing items with comprehensive product management, image handling, stock management, and order processing.

---

## Database Schema

### Product Entity Structure
The system is built with the following main entities:

#### 1. **Product** Table
Stores core product information:
- `id` - Primary key (Long)
- `name` - Product name (String)
- `adminId` - Merchant/Admin ID who created the product (Long)
- `price` - Product price in BDT (Double)
- `primaryImageUrl` - Main image displayed first (String)
- `count` - Stock quantity available (Integer)
- `description` - Detailed product description (String)
- `productType` - Category (Shirt, Pants, Dress, Jacket, Shoes, Accessories)
- `saleCount` - Number of units sold (Integer)
- `status` - Product status (ACTIVE, DELETED)
- **One-to-Many Relationship**: Multiple ProductImages

#### 2. **ProductImage** Table
Stores all images related to a product:
- `id` - Primary key (Long)
- `product_id` - Foreign key to Product (Long)
- `url` - Image URL/path (String)
- `primaryImage` - Boolean flag indicating if this is the primary image (Boolean)

#### 3. **ProductDetails** Table
Stores clothing-specific details:
- `id` - Primary key (Long)
- `productId` - Foreign key to Product (Long)
- `size` - Size (S, M, L, XL, etc.) (String)
- `color` - Color of the item (String)
- `material` - Material composition (Cotton, Polyester, etc.) (String)
- `fabricType` - Type of fabric (String)
- `careInstructions` - Washing/care instructions (String)

#### 4. **Order** Table
Stores order information:
- `id` - Primary key (Long)
- `userId` - Foreign key to User (Long)
- `totalAmount` - Total order amount in BDT (Double)
- `orderStatus` - Status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- `createdAt` - Timestamp (LocalDateTime)

#### 5. **OrderItem** Table
Stores individual items in an order:
- `id` - Primary key (Long)
- `orderId` - Foreign key to Order (Long)
- `productId` - Foreign key to Product (Long)
- `quantity` - Number of items ordered (Integer)
- `unitPrice` - Price per item at time of order (Double)
- `subTotal` - Quantity × Unit Price (Double)

---

## API Endpoints

### Product Management APIs

#### 1. Create Product (Admin Only)
```
POST /api/products
Authorization: Bearer {token}

Request Body:
{
  "name": "Cotton T-Shirt",
  "price": 1500.00,
  "description": "Comfortable cotton t-shirt perfect for casual wear",
  "quantity": 25,
  "productType": "Shirt",
  "merchantId": 1,
  "primaryImageUrl": "/uploads/products/primary.jpg",
  "imageUrls": [
    "/uploads/products/image1.jpg",
    "/uploads/products/image2.jpg",
    "/uploads/products/image3.jpg"
  ],
  "details": {
    "size": "M",
    "color": "Red",
    "material": "100% Cotton",
    "fabricType": "Jersey",
    "careInstructions": "Wash in cold water, hang dry"
  }
}

Response:
{
  "id": 1,
  "name": "Cotton T-Shirt",
  "price": 1500.00,
  "description": "Comfortable cotton t-shirt perfect for casual wear",
  "count": 25,
  "available": true,
  "productType": "Shirt",
  "merchantId": 1,
  "primaryImageUrl": "/uploads/products/primary.jpg",
  "imageUrls": [
    "/uploads/products/primary.jpg",
    "/uploads/products/image1.jpg",
    "/uploads/products/image2.jpg",
    "/uploads/products/image3.jpg"
  ],
  "saleCount": 0,
  "details": {
    "size": "M",
    "color": "Red",
    "material": "100% Cotton",
    "fabricType": "Jersey",
    "careInstructions": "Wash in cold water, hang dry"
  }
}
```

#### 2. Get Single Product
```
GET /api/products/{id}

Response:
{
  "id": 1,
  "name": "Cotton T-Shirt",
  "price": 1500.00,
  "description": "Comfortable cotton t-shirt perfect for casual wear",
  "count": 24,  // Updated after order
  "available": true,
  "productType": "Shirt",
  "primaryImageUrl": "/uploads/products/primary.jpg",
  "imageUrls": [
    "/uploads/products/primary.jpg",
    "/uploads/products/image1.jpg",
    "/uploads/products/image2.jpg"
  ],
  "saleCount": 1,
  "details": {
    "size": "M",
    "color": "Red",
    "material": "100% Cotton",
    "fabricType": "Jersey",
    "careInstructions": "Wash in cold water, hang dry"
  }
}
```

#### 3. List Products (with Pagination & Filtering)
```
GET /api/products?page=0&size=10&sortBy=id&name=shirt&type=Shirt

Parameters:
- page: Page number (0-indexed, default: 0)
- size: Number of items per page (default: 10)
- sortBy: Sort field (default: id)
- name: Filter by product name (optional)
- type: Filter by product type (optional)

Response:
{
  "content": [
    {
      "id": 1,
      "name": "Cotton T-Shirt",
      "price": 1500.00,
      ...
    }
  ],
  "totalElements": 5,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "empty": false
}
```

#### 4. Update Product (Admin Only)
```
PUT /api/products/{id}
Authorization: Bearer {token}

Request Body:
{
  "name": "Premium Cotton T-Shirt",
  "price": 1800.00,
  "description": "Premium quality cotton t-shirt",
  "quantity": 15,
  "productType": "Shirt",
  "merchantId": 1,
  "primaryImageUrl": "/uploads/products/new-primary.jpg",
  "imageUrls": [
    "/uploads/products/new-primary.jpg",
    "/uploads/products/new-image1.jpg"
  ],
  "details": {
    "size": "L",
    "color": "Blue",
    "material": "100% Organic Cotton",
    "fabricType": "Premium Jersey",
    "careInstructions": "Wash in cold water, air dry"
  }
}
```

#### 5. Delete Product (Admin Only - Soft Delete)
```
DELETE /api/products/{id}
Authorization: Bearer {token}

Response: 204 No Content
Note: This performs a soft delete, marking the product as DELETED but not removing it from the database.
```

---

### Order Management APIs

#### 1. Place Order
```
POST /api/orders

Request Body:
{
  "userMail": "customer@example.com",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}

Response:
{
  "id": 101,
  "userId": 5,
  "totalAmount": 3800.00,
  "orderStatus": "PENDING",
  "createdAt": "2025-05-05T14:30:00"
}
```

---

## Stock Management & Availability Checking

### Automatic Stock Management
When an order is placed:

1. **Validation**: System checks if each product exists and is ACTIVE
2. **Stock Check**: Verifies that requested quantity is available
3. **Stock Deduction**: Automatically reduces the product's `count` by the ordered quantity
4. **Error Handling**: If stock is insufficient, order is rejected with error message

### Example Stock Flow
```
Product: Cotton T-Shirt
Initial Count: 25

Order 1: Buy 3 items → Count becomes 22
Order 2: Buy 5 items → Count becomes 17
Order 3: Buy 18 items → ERROR: Insufficient stock (only 17 available)
```

---

## Image Management

### Multiple Images Support
Each product can have multiple images:

1. **Primary Image**: First image shown in product listing/preview
2. **Additional Images**: Available when viewing product details
3. **Image Storage**: Images are stored with URL paths (can be local or CDN)

### Image Structure Example
```
Product ID: 1 (Cotton T-Shirt)
├── Primary Image: /uploads/products/tshirt-primary.jpg
├── Image 1: /uploads/products/tshirt-detail1.jpg
├── Image 2: /uploads/products/tshirt-detail2.jpg
└── Image 3: /uploads/products/tshirt-detail3.jpg
```

---

## Product Details (Clothing Information)

Each product can have detailed information about the clothing:

- **Size**: S, M, L, XL, XXL, etc.
- **Color**: Various colors (Red, Blue, Green, etc.)
- **Material**: Cotton, Polyester, Silk, Wool, Blended, etc.
- **Fabric Type**: Jersey, Denim, Linen, Satin, etc.
- **Care Instructions**: Washing instructions, drying methods, ironing guidelines

This information appears in the product details view and helps customers make informed decisions.

---

## Product Status & Availability

Products can have two statuses:

### ACTIVE
- Product is visible to customers
- Can be ordered if stock is available
- Default status for new products

### DELETED
- Product is hidden from listings
- Cannot be ordered
- Achieved through soft delete (data remains in database)

---

## Error Handling

### Common Error Scenarios

#### Product Not Found
```
Status: 404
{
  "error": "Product not found"
}
```

#### Insufficient Stock
```
Status: 400
{
  "error": "Product is out of stock: Cotton T-Shirt"
}
```

#### Invalid Order Quantity
```
Status: 400
{
  "error": "Invalid quantity for product: Cotton T-Shirt"
}
```

#### User Not Found
```
Status: 404
{
  "error": "User not found"
}
```

---

## Database Configuration

The application uses **PostgreSQL**:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gach
spring.datasource.username=kawsar
spring.datasource.password=12345
spring.jpa.hibernate.ddl-auto=create
```

**Database**: `gach`
**Tables**: 
- product
- product_image
- product_details
- order
- order_item
- user
- admin
- order_status_enum
- product_status_enum

---

## Running the Application

### Build the Project
```bash
cd d:\ecommerce\e-com
.\gradlew build -x test
```

### Run the Application
```bash
.\gradlew bootRun
```

### Server Port
The application runs on: `http://localhost:10011`

### Swagger Documentation
API documentation available at: `http://localhost:10011/swagger-ui.html`

---

## Example Workflows

### Workflow 1: Add a New Product with Multiple Images
1. Admin logs in
2. Calls `POST /api/products` with:
   - Product name, price, description
   - Stock quantity (count)
   - Primary image and additional images
   - Clothing details (size, color, material, etc.)
3. Product is created with status ACTIVE
4. Images are stored in product_image table
5. Product details are stored in product_details table

### Workflow 2: Customer Orders Products
1. Customer searches for products: `GET /api/products?name=shirt`
2. Views product details: `GET /api/products/1` (includes all images)
3. Places order: `POST /api/orders`
4. System validates:
   - Product exists and is ACTIVE
   - Sufficient stock available
5. Stock count is automatically reduced
6. Order is created with PENDING status
7. Order items are recorded

### Workflow 3: Check Product Availability
1. Admin can view `count` field in product details
2. When `count > 0`, product is `available: true`
3. When `count = 0`, product is `available: false`
4. Customer sees "Out of Stock" if `available: false`

---

## Security

- **Authentication**: JWT token-based
- **Authorization**: Role-based access control (ADMIN, USER)
- **Protected Endpoints**: 
  - POST /api/products (ADMIN only)
  - PUT /api/products/{id} (ADMIN only)
  - DELETE /api/products/{id} (ADMIN only)

---

## Project Status

✅ **Complete Setup**
- Product entity with image relationships
- Multiple image support with primary image designation
- Stock management and availability checking
- Order validation with stock verification
- Product details for clothing information
- Full API endpoints
- Database schema with proper relationships
- Error handling and validation
- Swagger documentation

---

## Next Steps (Optional Enhancements)

1. Add image upload functionality
2. Implement product reviews and ratings
3. Add cart functionality
4. Implement payment gateway integration
5. Add shipping information
6. Email notifications for orders
7. Advanced search and filtering
8. Product recommendations
9. Wishlist functionality
10. Admin dashboard for order tracking

---

**Project Created**: May 5, 2026
**Status**: Ready for Development
