package com.shop.service;

import com.shop.dto.ShopDto;
import com.shop.entity.Product;
import com.shop.entity.Shop;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ShopService {

    void createShop(ShopDto shopRequest);
    void updateShop(Long shopId, ShopDto shopRequest);
    void deleteShop(Long shopId);
    Optional<Shop> getShopById(Long shopId);
    List<Shop> getAllShops();

    boolean existsById(Long shopId);
}
