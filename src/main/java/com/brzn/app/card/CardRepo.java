package com.brzn.app.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {
    @Query(value = "SELECT * FROM cards where upper(name) like %upper(?1)% ")
    List<Card> findAllByPartialName(String name);
}
