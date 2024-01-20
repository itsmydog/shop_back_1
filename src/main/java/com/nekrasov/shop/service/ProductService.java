package com.nekrasov.shop.service;

import com.nekrasov.shop.domain.Bucket;
import com.nekrasov.shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String username);
}
