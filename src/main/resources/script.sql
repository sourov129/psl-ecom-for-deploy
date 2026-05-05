-- ============================================================================
-- DATABASE SETUP QUERIES
-- Run these queries after your Spring Boot app creates the tables
-- ============================================================================

-- First, let's see the current table structure (run this to verify tables exist)
-- SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';

-- ============================================================================
-- 1. SAMPLE MERCHANTS DATA
-- ============================================================================

-- Clear existing data (if needed)
-- DELETE FROM product;
-- DELETE FROM admin;

-- Insert sample merchants (matching your Flutter mock data)
INSERT INTO admin (id, name, email, phone, password, location, status, rating, created_at, updated_at) VALUES
(1, 'Green Garden Nursery', 'contact@greengarden.com', '+8801712345678', '$2a$10$DummyHashedPassword1', 'Dhaka, Bangladesh', 'ACTIVE', 4.5, NOW() - INTERVAL '30 days', NOW() - INTERVAL '5 days'),
(2, 'City Garden Center', 'info@citygarden.com', '+8801812345679', '$2a$10$DummyHashedPassword2', 'Chittagong, Bangladesh', 'ACTIVE', 4.2, NOW() - INTERVAL '25 days', NOW() - INTERVAL '3 days'),
(3, 'Plant Paradise', 'hello@plantparadise.com', '+8801912345680', '$2a$10$DummyHashedPassword3', 'Sylhet, Bangladesh', 'ACTIVE', 4.7, NOW() - INTERVAL '20 days', NOW() - INTERVAL '2 days'),
(4, 'Tropical Plants BD', 'support@tropicalplants.com', '+8801612345681', '$2a$10$DummyHashedPassword4', 'Rajshahi, Bangladesh', 'ACTIVE', 4.3, NOW() - INTERVAL '15 days', NOW() - INTERVAL '1 day'),
(5, 'Urban Jungle', 'team@urbanjungle.com', '+8801512345682', '$2a$10$DummyHashedPassword5', 'Khulna, Bangladesh', 'ACTIVE', 4.6, NOW() - INTERVAL '10 days', NOW()),
(6, 'Flower Valley', 'care@flowervalley.com', '+8801412345683', '$2a$10$DummyHashedPassword6', 'Barishal, Bangladesh', 'ACTIVE', 4.4, NOW() - INTERVAL '8 days', NOW()),
(7, 'Rose Garden Nursery', 'info@rosegarden.com', '+8801312345684', '$2a$10$DummyHashedPassword7', 'Dhaka, Bangladesh', 'ACTIVE', 4.1, NOW() - INTERVAL '5 days', NOW()),
(8, 'Eco Plants Hub', 'support@ecoplants.com', '+8801212345685', '$2a$10$DummyHashedPassword8', 'Chittagong, Bangladesh', 'ACTIVE', 4.8, NOW() - INTERVAL '3 days', NOW());

-- ============================================================================
-- 2. SAMPLE PRODUCTS DATA (10 products per admin = 80 total)
-- ============================================================================

-- Products for Admin 1 (Green Garden Nursery)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(1, 'Monstera Deliciosa', 1, 1500.00, 'product_1', 25, 'Perfect for home decoration and air purification. Easy to care for and maintain.', 'Indoor Plants', 15, 'ACTIVE', '2-3 feet', '6 months', 'Dark Green with White Variegation', 'Well-draining potting mix', 'Once a week', 'Bright, indirect light'),
(2, 'Peace Lily', 1, 800.00, 'product_2', 30, 'Beautiful flowering plant that thrives in low light conditions.', 'Indoor Plants', 22, 'ACTIVE', '1-2 feet', '4 months', 'Dark Green', 'Standard potting soil', 'Twice a week', 'Low to medium light'),
(3, 'Snake Plant (Sansevieria)', 1, 600.00, 'product_3', 20, 'Low-maintenance plant that requires minimal watering. Perfect for beginners.', 'Indoor Plants', 18, 'ACTIVE', '2-4 feet', '1 year', 'Green with Yellow Edges', 'Cactus/succulent mix', 'Every 2-3 weeks', 'Any light condition'),
(4, 'Red Rose Bush', 1, 1200.00, 'product_4', 15, 'Classic fragrant roses perfect for garden borders and bouquets.', 'Outdoor Plants', 8, 'ACTIVE', '3-5 feet', '1 year', 'Green', 'Rich, well-drained soil', 'Every other day', 'Full sun (6+ hours)'),
(5, 'Aloe Vera Plant', 1, 400.00, 'product_5', 40, 'Medicinal plant with healing properties, perfect for beginners.', 'Succulents', 35, 'ACTIVE', '1-2 feet', '8 months', 'Green with Gel-filled leaves', 'Sandy, well-draining soil', 'Every 2-3 weeks', 'Bright, indirect light'),
(6, 'Plastic Watering Can', 1, 250.00, 'product_6', 50, 'Lightweight watering can perfect for indoor and outdoor plants.', 'Garden Tools', 45, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(7, 'Tomato Seeds Pack', 1, 150.00, 'product_7', 100, 'Fresh, high-germination tomato seeds for home gardening.', 'Seeds', 60, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(8, 'Golden Pothos', 1, 500.00, 'product_8', 35, 'Fast-growing vine that purifies air and adds green beauty to any space.', 'Indoor Plants', 28, 'ACTIVE', 'Trailing 6+ feet', '3 months', 'Green with Golden Variegation', 'Standard potting soil', 'Once a week', 'Low to bright indirect light'),
(9, 'Pink Hibiscus', 1, 900.00, 'product_9', 18, 'Vibrant tropical flowering shrub that blooms year-round.', 'Outdoor Plants', 12, 'ACTIVE', '4-6 feet', '1.5 years', 'Green', 'Rich, moist soil', 'Daily in summer', 'Full sun'),
(10, 'Jade Plant', 1, 350.00, 'product_10', 25, 'Lucky plant that symbolizes prosperity and good fortune.', 'Succulents', 20, 'ACTIVE', '1-3 feet', '2 years', 'Thick, fleshy green', 'Cactus/succulent mix', 'Every 2-3 weeks', 'Bright light');

-- Products for Admin 2 (City Garden Center)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(11, 'Rubber Plant (Ficus)', 2, 1200.00, 'product_11', 20, 'Elegant plant with glossy leaves, perfect for modern home decor.', 'Indoor Plants', 10, 'ACTIVE', '6-8 feet', '1 year', 'Glossy Dark Green', 'Well-draining potting mix', 'Once a week', 'Bright, indirect light'),
(12, 'Fiddle Leaf Fig', 2, 2500.00, 'product_12', 12, 'Statement plant with large, violin-shaped leaves for sophisticated spaces.', 'Indoor Plants', 5, 'ACTIVE', '6-10 feet', '2 years', 'Large, Green Fiddle-shaped', 'Well-draining, rich soil', 'Once a week', 'Bright, indirect light'),
(13, 'Purple Bougainvillea', 2, 1100.00, 'product_13', 15, 'Colorful climbing plant ideal for walls and fences.', 'Outdoor Plants', 8, 'ACTIVE', '10-20 feet', '1.5 years', 'Green with Purple Bracts', 'Well-drained soil', 'Moderate watering', 'Full sun'),
(14, 'White Jasmine', 2, 700.00, 'product_14', 25, 'Fragrant flowering vine perfect for evening gardens.', 'Outdoor Plants', 15, 'ACTIVE', '8-15 feet', '1 year', 'Green', 'Rich, well-drained soil', 'Regular watering', 'Full to partial sun'),
(15, 'Blue Echeveria', 2, 300.00, 'product_15', 30, 'Rosette-shaped succulent with blue-green coloration.', 'Succulents', 25, 'ACTIVE', '6-8 inches', '6 months', 'Blue-green Rosette', 'Sandy, well-draining soil', 'Every 2 weeks', 'Bright light'),
(16, 'Steel Garden Spade', 2, 800.00, 'product_16', 40, 'Durable steel spade for digging and transplanting plants.', 'Garden Tools', 30, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(17, 'Green Chili Seeds', 2, 120.00, 'product_17', 80, 'Spicy chili seeds perfect for container or garden growing.', 'Seeds', 55, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(18, 'Spider Plant', 2, 400.00, 'product_18', 28, 'Easy-care plant that produces baby plants, great for propagation.', 'Indoor Plants', 20, 'ACTIVE', '1-2 feet', '5 months', 'Green with White Stripes', 'Standard potting soil', 'Once a week', 'Bright, indirect light'),
(19, 'Orange Marigold', 2, 200.00, 'product_19', 50, 'Bright annual flowers that bloom throughout the growing season.', 'Outdoor Plants', 40, 'ACTIVE', '1-3 feet', '3 months', 'Green', 'Any well-drained soil', 'Regular watering', 'Full sun'),
(20, 'Mini Cactus Collection', 2, 450.00, 'product_20', 22, 'Variety pack of small cacti perfect for desk decoration.', 'Succulents', 18, 'ACTIVE', '2-6 inches', '1 year', 'Various Green Shades', 'Cactus mix', 'Every 3-4 weeks', 'Bright light');

-- Products for Admin 3 (Plant Paradise)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(21, 'ZZ Plant (Zamioculcas)', 3, 900.00, 'product_21', 18, 'Nearly indestructible plant that tolerates neglect and low light.', 'Indoor Plants', 12, 'ACTIVE', '2-3 feet', '1 year', 'Glossy Dark Green', 'Well-draining potting mix', 'Every 2-3 weeks', 'Low to bright light'),
(22, 'Boston Fern', 3, 650.00, 'product_22', 25, 'Classic fern that adds lush, tropical feel to any indoor space.', 'Indoor Plants', 18, 'ACTIVE', '2-3 feet', '8 months', 'Bright Green Fronds', 'Moist, well-draining soil', '2-3 times a week', 'Bright, indirect light'),
(23, 'Yellow Sunflower', 3, 300.00, 'product_23', 35, 'Tall, cheerful flowers that attract bees and birds to your garden.', 'Outdoor Plants', 25, 'ACTIVE', '6-12 feet', '4 months', 'Large Green Leaves', 'Rich, well-drained soil', 'Daily watering', 'Full sun'),
(24, 'Lavender Plant', 3, 550.00, 'product_24', 20, 'Aromatic herb plant with beautiful purple flowers.', 'Outdoor Plants', 15, 'ACTIVE', '2-3 feet', '1 year', 'Gray-green', 'Well-drained, alkaline soil', 'Moderate watering', 'Full sun'),
(25, 'String of Pearls', 3, 380.00, 'product_25', 15, 'Trailing succulent with bead-like leaves, great for hanging baskets.', 'Succulents', 10, 'ACTIVE', 'Trailing 2-3 feet', '1 year', 'Green Bead-like', 'Well-draining succulent soil', 'Every 2-3 weeks', 'Bright, indirect light'),
(26, 'Pruning Shears', 3, 600.00, 'product_26', 30, 'Sharp, precise shears for pruning and maintaining plant health.', 'Garden Tools', 22, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(27, 'Mixed Flower Seeds', 3, 180.00, 'product_27', 70, 'Colorful mix of annual flower seeds for beautiful gardens.', 'Seeds', 45, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(28, 'Chinese Evergreen', 3, 750.00, 'product_28', 22, 'Colorful foliage plant that brightens up dark corners.', 'Indoor Plants', 16, 'ACTIVE', '1-3 feet', '10 months', 'Green with Red/Pink patterns', 'Well-draining potting soil', 'Once a week', 'Low to medium light'),
(29, 'Mint Plant', 3, 250.00, 'product_29', 40, 'Fresh culinary herb perfect for teas and cooking.', 'Outdoor Plants', 30, 'ACTIVE', '1-2 feet', '6 months', 'Bright Green', 'Moist, rich soil', 'Keep soil moist', 'Partial sun'),
(30, 'Haworthia Zebra', 3, 320.00, 'product_30', 18, 'Striking striped succulent that forms attractive clusters.', 'Succulents', 12, 'ACTIVE', '4-6 inches', '1.5 years', 'Green with White Stripes', 'Sandy, well-draining soil', 'Every 2-3 weeks', 'Bright, indirect light');

-- Continue with remaining merchants (4-8) - 50 more products
-- Products for Admin 4 (Tropical Plants BD)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(31, 'Tulsi Plant', 4, 200.00, 'product_31', 45, 'Sacred plant known for its medicinal and spiritual properties.', 'Outdoor Plants', 35, 'ACTIVE', '1-2 feet', '4 months', 'Green', 'Well-drained soil', 'Daily watering', 'Full to partial sun'),
(32, 'Bamboo Plant', 4, 800.00, 'product_32', 12, 'Fast-growing ornamental grass for privacy and decoration.', 'Outdoor Plants', 8, 'ACTIVE', '10-20 feet', '2 years', 'Green', 'Moist, well-drained soil', 'Regular watering', 'Partial to full sun'),
(33, 'Golden Sedum', 4, 280.00, 'product_33', 25, 'Ground-covering succulent with star-shaped yellow flowers.', 'Succulents', 20, 'ACTIVE', '6-12 inches', '8 months', 'Green with Golden tinge', 'Well-draining soil', 'Every 2 weeks', 'Full sun to partial shade'),
(34, 'Ceramic Plant Pot Set', 4, 1200.00, 'product_34', 20, 'Set of decorative pots in various sizes for different plants.', 'Garden Tools', 15, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(35, 'Herb Seeds Collection', 4, 300.00, 'product_35', 60, 'Collection of popular culinary herbs for kitchen gardens.', 'Seeds', 40, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(36, 'Pink Kalanchoe', 4, 350.00, 'product_36', 30, 'Blooming succulent with colorful flowers in various shades.', 'Succulents', 22, 'ACTIVE', '6-12 inches', '1 year', 'Thick Green with Pink Flowers', 'Well-draining succulent soil', 'Every 1-2 weeks', 'Bright light'),
(37, 'Desert Rose', 4, 1500.00, 'product_37', 8, 'Exotic succulent with beautiful pink and red flowers.', 'Succulents', 5, 'ACTIVE', '1-3 feet', '3 years', 'Green with Thick Trunk', 'Sandy, well-draining soil', 'Every 2-3 weeks', 'Full sun'),
(38, 'Garden Gloves Pair', 4, 300.00, 'product_38', 50, 'Protective gloves for safe and comfortable gardening.', 'Garden Tools', 35, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(39, 'Vegetable Seeds Mix', 4, 250.00, 'product_39', 80, 'Variety pack of easy-to-grow vegetables for beginners.', 'Seeds', 55, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(40, 'Christmas Cactus', 4, 600.00, 'product_40', 15, 'Holiday cactus that blooms with colorful flowers in winter.', 'Succulents', 10, 'ACTIVE', '1-2 feet', '2 years', 'Green Segmented', 'Well-draining potting mix', 'Weekly in growing season', 'Bright, indirect light');

-- Products for Admin 5 (Urban Jungle)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(41, 'Monstera Adansonii', 5, 800.00, 'product_41', 20, 'Swiss cheese plant with unique perforated leaves.', 'Indoor Plants', 15, 'ACTIVE', 'Trailing 3-8 feet', '8 months', 'Green with Natural Holes', 'Well-draining potting mix', 'Once a week', 'Bright, indirect light'),
(42, 'Bird of Paradise', 5, 2200.00, 'product_42', 6, 'Dramatic tropical plant with large paddle-shaped leaves.', 'Indoor Plants', 3, 'ACTIVE', '6-8 feet', '2 years', 'Large Green Paddle-shaped', 'Rich, well-draining soil', 'Twice a week', 'Bright light'),
(43, 'Passion Flower Vine', 5, 900.00, 'product_43', 12, 'Exotic flowering vine with intricate, colorful blooms.', 'Outdoor Plants', 7, 'ACTIVE', '15-30 feet', '1 year', 'Green Lobed', 'Rich, moist soil', 'Regular watering', 'Full sun to partial shade'),
(44, 'Lemon Tree Sapling', 5, 1800.00, 'product_44', 8, 'Young lemon tree perfect for container growing.', 'Outdoor Plants', 4, 'ACTIVE', '6-12 feet', '1 year', 'Glossy Green', 'Well-drained, slightly acidic soil', 'Regular watering', 'Full sun'),
(45, 'Lithops (Living Stones)', 5, 500.00, 'product_45', 15, 'Unique succulent that resembles stones, fascinating conversation piece.', 'Succulents', 8, 'ACTIVE', '1-2 inches', '3 years', 'Stone-like patterns', 'Very well-draining soil', 'Monthly or less', 'Bright light'),
(46, 'Premium Soil Mix', 5, 400.00, 'product_46', 40, 'High-quality potting mix for optimal plant nutrition.', 'Garden Tools', 25, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(47, 'Grass Seeds Pack', 5, 200.00, 'product_47', 100, 'Hardy grass seeds for lawn establishment and repair.', 'Seeds', 70, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(48, 'Calathea Medallion', 5, 1200.00, 'product_48', 10, 'Prayer plant with stunning patterned leaves that fold at night.', 'Indoor Plants', 6, 'ACTIVE', '1-2 feet', '1 year', 'Green with Silver/Purple patterns', 'Well-draining, moist soil', '2-3 times a week', 'Medium, indirect light'),
(49, 'Plumeria (Frangipani)', 5, 1500.00, 'product_49', 5, 'Tropical flowering tree with fragrant, colorful blooms.', 'Outdoor Plants', 2, 'ACTIVE', '15-25 feet', '2 years', 'Green Oval', 'Well-drained soil', 'Deep, infrequent watering', 'Full sun'),
(50, 'Wooden Plant Stand', 5, 1500.00, 'product_50', 15, 'Elegant wooden stand to display your favorite plants.', 'Garden Tools', 8, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL);

-- Products for Admin 6 (Flower Valley)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(51, 'Anthurium Red', 6, 1100.00, 'product_51', 12, 'Exotic flowering plant with glossy red heart-shaped blooms.', 'Indoor Plants', 8, 'ACTIVE', '1-2 feet', '1 year', 'Glossy Green Heart-shaped', 'Well-draining potting mix', 'Twice a week', 'Bright, indirect light'),
(52, 'Dracaena Marginata', 6, 950.00, 'product_52', 15, 'Madagascar dragon tree with spiky red-edged leaves.', 'Indoor Plants', 10, 'ACTIVE', '6-8 feet', '1.5 years', 'Green with Red Edges', 'Well-draining soil', 'Once a week', 'Bright, indirect light'),
(53, 'Geranium Pink', 6, 300.00, 'product_53', 40, 'Classic flowering plant perfect for containers and gardens.', 'Outdoor Plants', 30, 'ACTIVE', '1-2 feet', '6 months', 'Green Round', 'Well-drained soil', 'Regular watering', 'Full sun to partial shade'),
(54, 'Camellia Bush', 6, 2000.00, 'product_54', 6, 'Elegant flowering shrub with waxy, rose-like blooms.', 'Outdoor Plants', 3, 'ACTIVE', '6-12 feet', '2 years', 'Glossy Dark Green', 'Acidic, well-drained soil', 'Regular watering', 'Partial shade'),
(55, 'Hens and Chicks', 6, 250.00, 'product_55', 35, 'Hardy succulent that produces many small offset plants.', 'Succulents', 25, 'ACTIVE', '4-6 inches', '1 year', 'Green/Red-tipped Rosettes', 'Well-draining soil', 'Every 2-3 weeks', 'Full sun to partial shade'),
(56, 'Garden Fork', 6, 700.00, 'product_56', 25, 'Multi-purpose fork for soil cultivation and weeding.', 'Garden Tools', 18, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(57, 'Fruit Seeds Variety', 6, 350.00, 'product_57', 50, 'Exciting mix of fruit seeds for experimental gardening.', 'Seeds', 30, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(58, 'Philodendron Brasil', 6, 600.00, 'product_58', 20, 'Variegated heart-leaf philodendron with yellow and green leaves.', 'Indoor Plants', 14, 'ACTIVE', 'Trailing 4-8 feet', '6 months', 'Green with Yellow Variegation', 'Well-draining potting soil', 'Once a week', 'Bright, indirect light'),
(59, 'Azalea Bush', 6, 1300.00, 'product_59', 8, 'Spring-flowering shrub with masses of colorful blooms.', 'Outdoor Plants', 5, 'ACTIVE', '4-6 feet', '2 years', 'Green Oval', 'Acidic, moist soil', 'Regular watering', 'Partial shade'),
(60, 'Plant Mister Spray', 6, 200.00, 'product_60', 60, 'Fine mist sprayer for delicate plants and humidity control.', 'Garden Tools', 40, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL);

-- Products for Admin 7 (Rose Garden Nursery)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(61, 'Pilea Peperomioides', 7, 450.00, 'product_61', 25, 'Chinese money plant with unique round, coin-shaped leaves.', 'Indoor Plants', 18, 'ACTIVE', '8-12 inches', '8 months', 'Round, Green Coin-shaped', 'Well-draining potting mix', 'Once a week', 'Bright, indirect light'),
(62, 'Alocasia Polly', 7, 1400.00, 'product_62', 8, 'African mask plant with dramatic arrow-shaped leaves.', 'Indoor Plants', 5, 'ACTIVE', '2-3 feet', '1 year', 'Dark Green with White Veins', 'Well-draining, humid soil', '2-3 times a week', 'Bright, indirect light'),
(63, 'Climbing Rose Red', 7, 1500.00, 'product_63', 10, 'Classic climbing rose perfect for trellises and walls.', 'Outdoor Plants', 6, 'ACTIVE', '8-20 feet', '1 year', 'Green', 'Rich, well-drained soil', 'Deep watering 2-3 times/week', 'Full sun (6+ hours)'),
(64, 'Peony Bush', 7, 1800.00, 'product_64', 5, 'Perennial flowering plant with large, fragrant blooms.', 'Outdoor Plants', 2, 'ACTIVE', '2-4 feet', '2 years', 'Green Compound', 'Rich, well-drained soil', 'Regular watering', 'Full sun to partial shade'),
(65, 'Burros Tail', 7, 400.00, 'product_65', 15, 'Trailing succulent with thick, bead-like blue-green leaves.', 'Succulents', 10, 'ACTIVE', 'Trailing 1-4 feet', '1.5 years', 'Blue-green Bead-like', 'Well-draining succulent soil', 'Every 2-3 weeks', 'Bright light'),
(66, 'Organic Fertilizer', 7, 500.00, 'product_66', 80, 'Nutrient-rich fertilizer to promote healthy plant growth.', 'Garden Tools', 50, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(67, 'Bean Seeds Pack', 7, 180.00, 'product_67', 90, 'Protein-rich bean seeds perfect for organic gardening.', 'Seeds', 65, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL),
(68, 'Monstera Thai Constellation', 7, 8500.00, 'product_68', 2, 'Rare variegated monstera with cream and green marbling.', 'Indoor Plants', 1, 'ACTIVE', '3-6 feet', '1.5 years', 'Green with Cream Variegation', 'Well-draining, chunky mix', 'Once a week', 'Bright, indirect light'),
(69, 'Hydrangea Blue', 7, 1600.00, 'product_69', 6, 'Flowering shrub with large clusters of blue blooms.', 'Outdoor Plants', 3, 'ACTIVE', '3-6 feet', '1.5 years', 'Green Serrated', 'Moist, well-drained soil', 'Regular watering', 'Partial shade'),
(70, 'Crown of Thorns', 7, 550.00, 'product_70', 12, 'Succulent shrub with colorful bracts and thorny stems.', 'Succulents', 8, 'ACTIVE', '1-3 feet', '2 years', 'Green Oval with Thorns', 'Well-draining soil', 'Every 1-2 weeks', 'Full sun');

-- Products for Admin 8 (Eco Plants Hub)
INSERT INTO product (id, name, merchant_id, price, photo_id, count, description, product_type, sale_count, status, height, age, leaf_color, soil_type, watering_frequency, sunlight_requirement) VALUES
(71, 'Fiddle Leaf Fig Bush', 8, 1800.00, 'product_71', 8, 'Bushy variety of the popular fiddle leaf fig tree.', 'Indoor Plants', 5, 'ACTIVE', '3-5 feet', '1 year', 'Large Green Fiddle-shaped', 'Well-draining, rich soil', 'Once a week', 'Bright, indirect light');

commit;