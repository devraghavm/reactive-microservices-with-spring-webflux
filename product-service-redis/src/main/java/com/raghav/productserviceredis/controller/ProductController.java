package com.raghav.productserviceredis.controller;

import com.raghav.productserviceredis.dto.ProductDto;
import com.raghav.productserviceredis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public Mono<ProductDto> getProduct(@PathVariable Integer id) {
        return this.productService.getProduct(id);
    }

    @PatchMapping("{id}")
    public Mono<Void> updateProduct(@PathVariable Integer id, @RequestBody Mono<ProductDto> mono) {
        return this.productService.updateProduct(id, mono);
    }
}
