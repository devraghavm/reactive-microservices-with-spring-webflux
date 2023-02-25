package com.raghav.productserviceredis.service;

import com.raghav.productserviceredis.dto.ProductDto;
import com.raghav.productserviceredis.repository.ProductRepository;
import com.raghav.productserviceredis.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class ProductServiceWithNoCache implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<ProductDto> getProduct(Integer id) {
        return this.productRepository.findById(id)
                                     .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> updateProduct(Integer id, Mono<ProductDto> productDto) {
        return this.productRepository.findById(id)
                                     .zipWith(productDto)
                                     .doOnNext(t -> t.getT1().setQtyAvailable(t.getT2().getQuantityAvailable()))
                                     .map(Tuple2::getT1)
                                     .flatMap(this.productRepository::save)
                                     .then();
    }
}
