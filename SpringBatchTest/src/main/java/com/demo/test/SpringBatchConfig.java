package com.demo.test;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.demo.test.dao.BankTransaction;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory builderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReader<BankTransaction> itemReaderBank;
    @Autowired
    private ItemWriter<BankTransaction> itemWriter;
    @Autowired
    private ItemProcessor<BankTransaction, BankTransaction> itemProcessor;

    @Bean
    public Job myJob() {
        Step myStep = stepBuilderFactory.get("myStepBank")
                .<BankTransaction, BankTransaction>chunk(100)
                .reader(itemReaderBank)
                .writer(itemWriter)
                .processor(itemProcessor)
                .build();
        return builderFactory.get("myFirstJob")
                .incrementer(new RunIdIncrementer())
                .start(myStep)
                .build();
    }

    @Bean
    public FlatFileItemReader<BankTransaction> fileItemReader(
            @Value("${inputFile}") Resource inputFile) {
        FlatFileItemReader<BankTransaction> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setName("CSV-READER");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setResource(inputFile);
        fileItemReader.setLineMapper(lineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> defaultLineMapperBank = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "accountId", "transactionDateString", "trscType", "amount");
        defaultLineMapperBank.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper<BankTransaction> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(BankTransaction.class);
        defaultLineMapperBank.setFieldSetMapper(beanWrapperFieldSetMapper);
        return defaultLineMapperBank;
    }

   

}
