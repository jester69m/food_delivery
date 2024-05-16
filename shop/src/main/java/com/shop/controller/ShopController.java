package com.shop.controller;

import com.shop.dto.ShopDto;
import com.shop.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<?> createShop(@RequestBody ShopDto shopDto) {
        try{
            shopService.createShop(shopDto);
            return ResponseEntity.ok("Shop created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@PathVariable Long shopId, @RequestBody ShopDto shopDto) {
        try{
            System.out.println("shopId: " + shopId);
            System.out.println("shopDto: " + shopDto);
            shopService.updateShop(shopId, shopDto);
            return ResponseEntity.ok("Shop updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@PathVariable Long shopId) {
        try{
            shopService.deleteShop(shopId);
            return ResponseEntity.ok("Shop deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllShops() {
        try{
            return ResponseEntity.ok(shopService.getAllShops());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@PathVariable Long shopId) {
        try{
            return ResponseEntity.ok(shopService.getShopById(shopId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
