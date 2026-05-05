# API Usage Examples - Quick Reference

## Authentication
All Admin endpoints require a Bearer token in the Authorization header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 1. CREATE PRODUCT (Admin Only)

### Example 1: Create a Red Cotton T-Shirt
```bash
curl -X POST http://localhost:10011/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "name": "Premium Red Cotton T-Shirt",
    "price": 1500.00,
    "description": "High quality cotton t-shirt comfortable for daily wear",
    "quantity": 50,
    "productType": "Shirt",
    "merchantId": 1,
    "primaryImageUrl": "/images/red-tshirt-front.jpg",
    "imageUrls": [
      "/images/red-tshirt-front.jpg",
      "/images/red-tshirt-back.jpg",
      "/images/red-tshirt-detail.jpg"
    ],
    "details": {
      "size": "M",
      "color": "Red",
      "material": "100% Cotton",
      "fabricType": "Jersey",
      "careInstructions": "Machine wash cold, tumble dry low, do not bleach"
    }
  }'
```

**Response Status**: 200 OK
```json
{
  "id": 1,
  "name": "Premium Red Cotton T-Shirt",
  "price": 1500.00,
  "description": "High quality cotton t-shirt comfortable for daily wear",
  "count": 50,
  "available": true,
  "productType": "Shirt",
  "merchantId": 1,
  "primaryImageUrl": "/images/red-tshirt-front.jpg",
  "imageUrls": [
    "/images/red-tshirt-front.jpg",
    "/images/red-tshirt-back.jpg",
    "/images/red-tshirt-detail.jpg"
  ],
  "saleCount": 0,
  "details": {
    "size": "M",
    "color": "Red",
    "material": "100% Cotton",
    "fabricType": "Jersey",
    "careInstructions": "Machine wash cold, tumble dry low, do not bleach"
  }
}
```

### Example 2: Create Blue Jeans
```bash
curl -X POST http://localhost:10011/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "name": "Classic Blue Denim Jeans",
    "price": 2500.00,
    "description": "Timeless blue denim jeans for all occasions",
    "quantity": 30,
    "productType": "Pants",
    "merchantId": 1,
    "primaryImageUrl": "/images/jeans-front.jpg",
    "imageUrls": [
      "/images/jeans-front.jpg",
      "/images/jeans-back.jpg",
      "/images/jeans-waist.jpg"
    ],
    "details": {
      "size": "32",
      "color": "Dark Blue",
      "material": "100% Cotton Denim",
      "fabricType": "Denim",
      "careInstructions": "Wash inside out in cold water, air dry"
    }
  }'
```

---

## 2. GET SINGLE PRODUCT

### Get Product by ID
```bash
curl -X GET http://localhost:10011/api/products/1 \
  -H "Content-Type: application/json"
```

**Response Status**: 200 OK
```json
{
  "id": 1,
  "name": "Premium Red Cotton T-Shirt",
  "price": 1500.00,
  "count": 48,
  "available": true,
  "primaryImageUrl": "/images/red-tshirt-front.jpg",
  "imageUrls": [
    "/images/red-tshirt-front.jpg",
    "/images/red-tshirt-back.jpg",
    "/images/red-tshirt-detail.jpg"
  ],
  "saleCount": 2,
  "details": {
    "size": "M",
    "color": "Red",
    "material": "100% Cotton",
    "fabricType": "Jersey",
    "careInstructions": "Machine wash cold, tumble dry low, do not bleach"
  }
}
```

---

## 3. LIST PRODUCTS

### List All Products (Paginated)
```bash
curl -X GET "http://localhost:10011/api/products?page=0&size=10" \
  -H "Content-Type: application/json"
```

### List with Filtering
```bash
curl -X GET "http://localhost:10011/api/products?page=0&size=10&name=shirt&type=Shirt" \
  -H "Content-Type: application/json"
```

### List with Sorting
```bash
curl -X GET "http://localhost:10011/api/products?page=0&size=10&sortBy=price" \
  -H "Content-Type: application/json"
```

**Response Status**: 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "name": "Premium Red Cotton T-Shirt",
      "price": 1500.00,
      "count": 48,
      "available": true,
      "productType": "Shirt",
      "primaryImageUrl": "/images/red-tshirt-front.jpg",
      "imageUrls": [
        "/images/red-tshirt-front.jpg",
        "/images/red-tshirt-back.jpg",
        "/images/red-tshirt-detail.jpg"
      ],
      "saleCount": 2
    },
    {
      "id": 2,
      "name": "Classic Blue Denim Jeans",
      "price": 2500.00,
      "count": 30,
      "available": true,
      "productType": "Pants",
      "primaryImageUrl": "/images/jeans-front.jpg",
      "imageUrls": [
        "/images/jeans-front.jpg",
        "/images/jeans-back.jpg",
        "/images/jeans-waist.jpg"
      ],
      "saleCount": 0
    }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "empty": false
}
```

---

## 4. UPDATE PRODUCT (Admin Only)

### Update Product
```bash
curl -X PUT http://localhost:10011/api/products/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "name": "Premium Red Cotton T-Shirt V2",
    "price": 1800.00,
    "description": "Updated high quality cotton t-shirt",
    "quantity": 40,
    "productType": "Shirt",
    "merchantId": 1,
    "primaryImageUrl": "/images/red-tshirt-new-front.jpg",
    "imageUrls": [
      "/images/red-tshirt-new-front.jpg",
      "/images/red-tshirt-new-back.jpg"
    ],
    "details": {
      "size": "L",
      "color": "Red",
      "material": "100% Organic Cotton",
      "fabricType": "Premium Jersey",
      "careInstructions": "Hand wash recommended, air dry"
    }
  }'
```

**Response Status**: 200 OK (Returns updated product)

---

## 5. DELETE PRODUCT (Admin Only)

### Soft Delete Product
```bash
curl -X DELETE http://localhost:10011/api/products/1 \
  -H "Authorization: Bearer {token}"
```

**Response Status**: 204 No Content

---

## 6. PLACE ORDER

### Order Example: Buy Multiple Products
```bash
curl -X POST http://localhost:10011/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userMail": "customer@example.com",
    "items": [
      {
        "productId": 1,
        "quantity": 2
      },
      {
        "productId": 2,
        "quantity": 1
      }
    ]
  }'
```

**Response Status**: 200 OK
```json
{
  "id": 101,
  "userId": 5,
  "totalAmount": 5300.00,
  "orderStatus": "PENDING",
  "createdAt": "2025-05-05T14:30:00"
}
```

### Stock Update After Order
After the order above:
- Product 1 (T-Shirt): count goes from 50 → 48 (2 units ordered)
- Product 2 (Jeans): count goes from 30 → 29 (1 unit ordered)

---

## 7. ERROR SCENARIOS

### Product Not Found
```bash
curl -X GET http://localhost:10011/api/products/999
```

**Response Status**: 404 Not Found
```json
null
```

### Insufficient Stock
```bash
curl -X POST http://localhost:10011/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userMail": "customer@example.com",
    "items": [
      {
        "productId": 1,
        "quantity": 1000
      }
    ]
  }'
```

**Response Status**: 500 Internal Server Error
```json
{
  "error": "Product is out of stock: Premium Red Cotton T-Shirt"
}
```

### Invalid Quantity
```bash
curl -X POST http://localhost:10011/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userMail": "customer@example.com",
    "items": [
      {
        "productId": 1,
        "quantity": 0
      }
    ]
  }'
```

**Response Status**: 500 Internal Server Error
```json
{
  "error": "Invalid quantity for product: Premium Red Cotton T-Shirt"
}
```

### User Not Found
```bash
curl -X POST http://localhost:10011/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userMail": "nonexistent@example.com",
    "items": [
      {
        "productId": 1,
        "quantity": 1
      }
    ]
  }'
```

**Response Status**: 500 Internal Server Error
```json
{
  "error": "User not found"
}
```

---

## Sample Data for Testing

### Products to Add
1. Red Cotton T-Shirt (50 units, 1500 BDT)
2. Classic Blue Denim Jeans (30 units, 2500 BDT)
3. Women's Black Formal Dress (20 units, 4500 BDT)
4. Winter Leather Jacket (15 units, 7500 BDT)
5. Casual Sneaker Shoes (40 units, 3000 BDT)

### Test Orders
1. Order 2 T-shirts (should reduce count from 50 to 48)
2. Order 1 Jeans (should reduce count from 30 to 29)
3. Order 5 Shoes (should reduce count from 40 to 35)

---

## Postman Collection

### Import into Postman
Create a new collection with these requests:

**Base URL**: `http://localhost:10011`

**Endpoints**:
- POST /api/products (Admin)
- GET /api/products/{id}
- GET /api/products
- PUT /api/products/{id} (Admin)
- DELETE /api/products/{id} (Admin)
- POST /api/orders

**Headers** (for Admin endpoints):
```
Authorization: Bearer {your_jwt_token}
Content-Type: application/json
```

---

## Swagger UI
Access the interactive API documentation at:
```
http://localhost:10011/swagger-ui.html
```

All endpoints are documented with request/response examples.

---

**Last Updated**: May 5, 2026
