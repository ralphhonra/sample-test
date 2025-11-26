package com.practice.shopping.domain.repository;

import com.practice.shopping.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
