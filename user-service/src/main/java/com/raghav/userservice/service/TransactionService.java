package com.raghav.userservice.service;

import com.raghav.userservice.dto.TransactionRequestDto;
import com.raghav.userservice.dto.TransactionResponseDto;
import com.raghav.userservice.dto.TransactionStatus;
import com.raghav.userservice.entity.UserTransaction;
import com.raghav.userservice.repository.UserRepository;
import com.raghav.userservice.repository.UserTransactionRepository;
import com.raghav.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                                  .filter(Boolean::booleanValue)
                                  .map(b -> EntityDtoUtil.toEntity(requestDto))
                                  .flatMap(this.transactionRepository::save)
                                  .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                                  .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getUserTransactionsByUserId(final int userId) {
        return this.transactionRepository.findByUserId(userId);
    }
}
