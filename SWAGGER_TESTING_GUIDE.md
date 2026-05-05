# Swagger UI Testing Guide

## Starting the Application

```bash
cd d:\ecommerce\e-com
.\gradlew bootRun
```

Application will start on: **http://localhost:10011**

---

## Accessing Swagger UI

### URL
```
http://localhost:10011/swagger-ui.html
```

### What You'll See
- **Left sidebar**: All available API endpoints grouped by tags
- **Main panel**: Endpoint details, parameters, request/response examples
- **Try it out button**: Test endpoints directly

---

## Testing the Search Endpoint

### 1. Navigate to Products Section
- Expand **Products** tag in the left sidebar
- Locate **POST /api/products/search**
- Click on it to expand

### 2. Click "Try it out"
- A text area will appear with sample request body
- Replace with your search criteria

### 3. Example Search Requests

#### Search 1: Search by Name
```json
{
  "name": "shirt",
  "page": 0,
  "pageSize": 10
}
```

#### Search 2: Search by Price Range
```json
{
  "minPrice": 1000,
  "maxPrice": 3000,
  "sortBy": "price",
  "sortDirection": "ASC",
  "page": 0,
  "pageSize": 10
}
```

#### Search 3: Complete Search
```json
{
  "name": "cotton",
  "description": "shirt",
  "productType": "Shirt",
  "minPrice": 500,
  "maxPrice": 5000,
  "minStock": 5,
  "page": 0,
  "pageSize": 10,
  "sortBy": "price",
  "sortDirection": "DESC"
}
```

### 4. Click "Execute"
- Swagger will send the request
- Response will appear below with:
  - Response code (200, 404, etc.)
  - Response body (JSON)
  - Response headers

---

## Testing Other Endpoints

### Create Product (POST /api/products)
**Note**: Requires ADMIN role token

```json
{
  "name": "Premium Cotton T-Shirt",
  "price": 1500.00,
  "description": "High quality comfortable cotton t-shirt",
  "quantity": 50,
  "productType": "Shirt",
  "merchantId": 1,
  "primaryImageUrl": "tshirt-front.jpg",
  "imageUrls": [
    "tshirt-front.jpg",
    "tshirt-back.jpg"
  ],
  "details": {
    "size": "M",
    "color": "Red",
    "material": "100% Cotton",
    "fabricType": "Jersey",
    "careInstructions": "Machine wash cold"
  }
}
```

### Get Product (GET /api/products/{id})
- Enter product ID in the path parameter field
- Click Execute
- Returns single product with all images and details

### List Products (GET /api/products)
- Optionally enter filter parameters:
  - `page`: Page number (default: 0)
  - `size`: Items per page (default: 10)
  - `sortBy`: Sort field (default: id)
  - `name`: Filter by name
  - `type`: Filter by product type

### Place Order (POST /api/orders)
```json
{
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
}
```

---

## Swagger Features in This API

### 1. **Endpoint Documentation**
- Each endpoint shows:
  - Description
  - Parameters (path, query, body)
  - Required vs optional fields
  - Example values
  - Data types and constraints

### 2. **Schema Documentation**
- Click "Model" section at bottom
- See full structure of DTOs:
  - `ProductDto`
  - `ProductSearchRequest`
  - `ProductImageDto`
  - `OrderRequestDto`

### 3. **Authorization**
- For endpoints marked with lock 🔒 (Admin only):
  - Click "Authorize" button (top right)
  - Enter Bearer token
  - All subsequent requests will include the token

### 4. **Response Examples**
- Success responses (200)
- Error responses (404, 400, 500)
- Full response body with data types

---

## Viewing API Definitions

### JSON Format
```
http://localhost:10011/v3/api-docs
```

### YAML Format
```
http://localhost:10011/v3/api-docs.yaml
```

---

## Search Query Parameter Definitions

| Parameter | Type | Required | Description | Example |
|-----------|------|----------|-------------|---------|
| name | String | No | Product name (partial match) | "shirt" |
| description | String | No | Description (partial match) | "cotton" |
| productType | String | No | Category | "Shirt" |
| minPrice | Double | No | Minimum price in BDT | 1000.0 |
| maxPrice | Double | No | Maximum price in BDT | 5000.0 |
| minStock | Integer | No | Minimum stock level | 5 |
| page | Integer | No | Page number (0-indexed) | 0 |
| pageSize | Integer | No | Items per page | 10 |
| sortBy | String | No | Sort field | "price" |
| sortDirection | String | No | ASC or DESC | "ASC" |

---

## Response Pagination

All list/search responses include:
```json
{
  "content": [ /* array of items */ ],
  "totalElements": 25,        // Total matching items
  "totalPages": 3,            // Total pages
  "size": 10,                 // Items per page
  "number": 0,                // Current page (0-indexed)
  "empty": false              // Is this page empty?
}
```

---

## Common HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | Success - Request succeeded |
| 201 | Created - Resource created |
| 204 | No Content - Success, no response body |
| 400 | Bad Request - Invalid parameters |
| 401 | Unauthorized - Missing/invalid token |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource not found |
| 500 | Server Error - Internal server error |

---

## Troubleshooting Swagger

### Issue 1: Swagger UI Won't Load
- ✅ Verify server is running: `.\gradlew bootRun`
- ✅ Check URL: `http://localhost:10011/swagger-ui.html`
- ✅ Check logs for errors

### Issue 2: "No api definition found" Error
- ✅ Verify springdoc dependency is in build.gradle
- ✅ Check application.properties for:
  ```properties
  springdoc.api-docs.enabled=true
  springdoc.swagger-ui.enabled=true
  ```

### Issue 3: Endpoints Not Showing
- ✅ Verify controllers have @RestController annotation
- ✅ Verify @RequestMapping is correct
- ✅ Add @Operation and @ApiResponse annotations for better docs

### Issue 4: Cannot Execute Requests
- ✅ For Admin endpoints, authorize with Bearer token
- ✅ Verify authentication is working
- ✅ Check CORS configuration (set to "*" in this project)

---

## Tips for API Testing

1. **Start Simple**: Test with minimal parameters first
2. **Use Examples**: Copy-paste example requests from this guide
3. **Check Responses**: Look at HTTP status codes and error messages
4. **Monitor Logs**: Check application logs for detailed error information
5. **Test Pagination**: Try `page=0,size=5` then `page=1,size=5`
6. **Test Sorting**: Try different values for `sortBy` and `sortDirection`
7. **Test Filtering**: Test each filter individually, then combined

---

## Next: Advanced Testing

After testing in Swagger, you can:

1. **Use Postman**: Import this Swagger definition into Postman
2. **Use curl**: For scripting and batch testing
3. **Write Unit Tests**: Create automated test suites
4. **Load Testing**: Test performance with multiple concurrent requests

---

**Last Updated**: May 5, 2026  
**Version**: 1.0.0
