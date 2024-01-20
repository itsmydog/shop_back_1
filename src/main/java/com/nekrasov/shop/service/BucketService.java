package com.nekrasov.shop.service;

import com.nekrasov.shop.domain.Bucket;
import com.nekrasov.shop.domain.User;
import com.nekrasov.shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
    void addProducts(Bucket bucket,List<Long> productIds);

    BucketDTO getBucketByUser(String name);
}
