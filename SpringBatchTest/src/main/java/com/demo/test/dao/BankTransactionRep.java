package com.demo.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRep extends JpaRepository<BankTransaction,Long>{
    
}
