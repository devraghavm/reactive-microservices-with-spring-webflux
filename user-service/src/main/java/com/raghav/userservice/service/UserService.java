package com.raghav.userservice.service;

import com.raghav.userservice.dto.UserDto;
import com.raghav.userservice.repository.UserRepository;
import com.raghav.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Flux<UserDto> all() {
        return this.repository.findAll()
                              .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(final int id) {
        return this.repository.findById(id)
                              .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono.map(EntityDtoUtil::toEntity)
                          .flatMap(this.repository::save)
                          .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(final int id, Mono<UserDto> userDtoMono) {
        return this.repository.findById(id)
                              .flatMap(p -> userDtoMono.map(EntityDtoUtil::toEntity)
                                                       .doOnNext(e -> e.setId(id)))
                              .flatMap(this.repository::save)
                              .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(final int id) {
        return this.repository.deleteById(id);
    }
}
