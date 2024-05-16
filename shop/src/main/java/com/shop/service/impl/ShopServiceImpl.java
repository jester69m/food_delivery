package com.shop.service.impl;

import com.shop.dto.ShopDto;
import com.shop.entity.Shop;
import com.shop.repository.ShopRepository;
import com.shop.service.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void createShop(ShopDto shopDto) {
        Shop shop = new Shop();
        if (shopDto.getName() == null || shopDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());
        if (shopDto.getAddress() == null || shopDto.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        shop.setAddress(shopDto.getAddress());
        shop.setMenu(shopDto.getMenu());
        shopRepository.save(shop);
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getShopById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }

    public void updateShop(Long id, ShopDto shopDto) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found"));
        if (shopDto.getName() != null) {
            shop.setName(shopDto.getName());
        }
        if (shopDto.getDescription() != null) {
            shop.setDescription(shopDto.getDescription());
        }
        if (shopDto.getAddress() != null) {
            shop.setAddress(shopDto.getAddress());
        }
        if (shopDto.getMenu() != null) {
            shop.setMenu(shopDto.getMenu());
        }
        shopRepository.save(shop);
    }

    public void deleteShop(Long id) {
        if(!shopRepository.existsById(id))
            throw new IllegalArgumentException("Nothing to delete.Try again");
        shopRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return shopRepository.existsById(id);
    }
}
