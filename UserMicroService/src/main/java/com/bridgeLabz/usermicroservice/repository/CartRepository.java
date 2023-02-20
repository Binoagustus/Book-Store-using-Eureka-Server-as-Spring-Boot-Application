package com.bridgeLabz.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.usermicroservice.model.CartModel;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Integer>{

}
