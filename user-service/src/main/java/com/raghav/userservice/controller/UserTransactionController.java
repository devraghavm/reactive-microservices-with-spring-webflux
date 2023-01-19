package com.raghav.userservice.controller;

import com.raghav.userservice.dto.TransactionRequestDto;
import com.raghav.userservice.dto.TransactionResponseDto;
import com.raghav.userservice.entity.UserTransaction;
import com.raghav.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping
    public Flux<UserTransaction> getUserTransactionsByUserId(@RequestParam int userId) {
        return this.service.getUserTransactionsByUserId(userId);
    }

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(this.service::createTransaction);
    }
}
