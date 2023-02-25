package com.raghav.productserviceredis.util;

import com.raghav.productserviceredis.dto.ProductDto;
import com.raghav.productserviceredis.entity.Product;

public class EntityDtoUtil {
    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setQuantityAvailable(product.getQtyAvailable());
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setQtyAvailable(dto.getQuantityAvailable());
        return product;
    }
}
