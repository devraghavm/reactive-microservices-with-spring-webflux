package com.raghav.productserviceredis.service;

import com.raghav.productserviceredis.dto.ProductDto;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDto> getProduct(Integer id);

    Mono<Void> updateProduct(Integer id, Mono<ProductDto> productDto);
}
