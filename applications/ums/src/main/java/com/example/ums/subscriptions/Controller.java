package com.example.ums.subscriptions;

import com.example.billing.BillingHttpClient;
import com.example.billing.RabbitBillingClient;
import com.example.email.SendEmail;
import com.example.subscriptions.CreateSubscription;
import com.example.subscriptions.Subscription;
import com.example.subscriptions.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/subscriptions")
public class Controller {

    @Autowired
    SubscriptionRepository subscriptions;

    @Autowired
    RabbitBillingClient rabbitBillingClient;

    public Controller(RabbitBillingClient rabbitBillingClient) {
        this.rabbitBillingClient = rabbitBillingClient;
    }



    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Subscription> index() {
        return subscriptions.all();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Map<String, String> params) {



        SendEmail emailSender = new SendEmail();

        new CreateSubscription(rabbitBillingClient, emailSender, subscriptions)
                .run(params.get("userId"), params.get("packageId"));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
