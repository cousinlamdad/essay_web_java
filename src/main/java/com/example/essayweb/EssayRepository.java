package com.example.essayweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EssayRepository extends JpaRepository<Essay, Integer> {

    List<Essay> findAllByOrderByCreatedAtDesc();
}
