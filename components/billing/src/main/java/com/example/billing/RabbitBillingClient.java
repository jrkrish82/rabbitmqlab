package com.example.billing;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class RabbitBillingClient implements BillingClient {

    private final RabbitTemplate template;

    private String queueName;

    @Autowired
    public RabbitBillingClient(RabbitTemplate template, String queueName) {
        this.template = template;
        this.queueName=queueName;
    }



    public void billUser(String userId, int amount) {

        BillingMessage billingMessage = new BillingMessage(userId,amount);

        template.convertAndSend(this.queueName,billingMessage);
        //restTemplate.postForEntity(fetchBillingServiceUrl() + "/reocurringPayment", amount, String.class);

    }

    /*@Bean
    public Queue queue() {
        return new Queue("billing", false);
    }*/
}
