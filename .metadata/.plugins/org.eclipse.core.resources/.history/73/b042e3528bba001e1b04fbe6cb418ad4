package com.zavoloka.snakegame.controller;

import com.zavoloka.snakegame.model.Snake;
import com.zavoloka.snakegame.repository.SnakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SnakeController {

    @Autowired
    private SnakeRepository snakeRepository;

    @GetMapping("/snake")
    public String getSnake(Model model) {
        Iterable<Snake> snakes = snakeRepository.findAll();
        model.addAttribute("snakes", snakes);
        return "snake";
    }
}
