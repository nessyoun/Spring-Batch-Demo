package com.demo.test;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.test.dao.BankTransaction;
import com.demo.test.dao.BankTransactionRep;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
    @Autowired
    private BankTransactionRep bankTransactionRep;

    @Override
    public void write(List<? extends BankTransaction> items) throws Exception {
        bankTransactionRep.saveAll(items);
    }

}