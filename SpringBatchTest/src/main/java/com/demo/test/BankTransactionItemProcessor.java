package com.demo.test;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.demo.test.dao.BankTransaction;

@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction,BankTransaction>{
    private SimpleDateFormat dateFormat =new SimpleDateFormat("dd/mm/yyyy-HH:mm:ss");
    @Override
    public BankTransaction process(BankTransaction item) throws Exception {
        item.setTransactionDate(dateFormat.parse(item.getTransactionDateString()));
        return item;
    }

    
}