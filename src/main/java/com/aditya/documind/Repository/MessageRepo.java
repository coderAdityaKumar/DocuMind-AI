package com.aditya.documind.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aditya.documind.Entity.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {

    
} 
