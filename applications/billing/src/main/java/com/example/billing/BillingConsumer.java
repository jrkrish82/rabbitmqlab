package com.example.billing;

import com.example.payments.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class BillingConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Gateway paymentGateway;

    public BillingConsumer(Gateway paymentGateway) {

        this.paymentGateway = paymentGateway;
    }

    @RabbitListener(queues = "billing")
    public void process(BillingMessage message) {
        logger.info("incoming message : {} , {}",message.getUserId(),message.getAmount());
        boolean result = paymentGateway.createReocurringPayment(message.getAmount());
    }

    @Bean
    public Queue queue(){
        return new Queue("billing", false);
    }
}
