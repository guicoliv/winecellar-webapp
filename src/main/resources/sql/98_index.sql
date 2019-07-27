-- bottle
CREATE INDEX idx_bottle_user_wine ON bottle (user_id, wine_id);

-- region_area
CREATE INDEX idx_region_area_region_id ON region_area (region_id);

-- review
CREATE INDEX idx_review_user_wine ON review (user_id, wine_id);

-- tasted
CREATE INDEX idx_tasted_user_wine ON tasted (user_id, wine_id);

-- wishlist
CREATE INDEX idx_wishlist_user_wine ON wishlist (user_id, wine_id);
