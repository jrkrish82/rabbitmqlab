package com.example.subscriptions;

import com.example.billing.RabbitBillingClient;
import com.example.email.SendEmail;

public class CreateSubscription {

    private final RabbitBillingClient rabbitBillingClient;
    private final SendEmail emailSender;
    private final SubscriptionRepository subscriptions;

    public CreateSubscription(
            RabbitBillingClient rabbitBillingClient,
            SendEmail emailSender, SubscriptionRepository subscriptions) {
        this.rabbitBillingClient = rabbitBillingClient;
        this.emailSender = emailSender;
        this.subscriptions = subscriptions;
    }

    public void run(String userId, String packageId) {
        subscriptions.create(new Subscription(userId, packageId));
        //chargeUser.run(userId, 100);
        rabbitBillingClient.billUser("abc123",100);
        emailSender.run("me@example.com", "Subscription Created", "Some email body");
    }
}
