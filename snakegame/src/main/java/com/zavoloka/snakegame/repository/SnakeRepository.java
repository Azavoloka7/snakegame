package com.zavoloka.snakegame.repository;

import com.zavoloka.snakegame.model.Snake;
import org.springframework.data.repository.CrudRepository;

public interface SnakeRepository extends CrudRepository<Snake, Long> {
    // Add custom queries if needed
}
