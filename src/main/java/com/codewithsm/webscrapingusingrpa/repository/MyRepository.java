package com.codewithsm.webscrapingusingrpa.repository;

import com.codewithsm.webscrapingusingrpa.model.ImdbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository extends JpaRepository<ImdbModel,Integer> {
}
