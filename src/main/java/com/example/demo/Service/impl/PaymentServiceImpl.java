package com.example.demo.Service.impl;

import com.example.demo.Model.ChargeRequest;
import com.example.demo.Service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${STRIPE_SECRET_KEY}")
    private String API_SECRET_KEY;


    @PostConstruct
    public void init() {

        Stripe.apiKey = API_SECRET_KEY;

    }

    @Override
    public Charge pay(ChargeRequest chargeRequest) throws CardException, APIException, InvalidRequestException, APIConnectionException, com.stripe.exception.AuthenticationException {
        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", chargeRequest.getAmount());
        chargeMap.put("currency", "MKD");
        chargeMap.put("source", chargeRequest.getStripeToken());
//        chargeMap.put("description", chargeRequest.getDescription());
        System.out.println("Dali stiga do pay metodot");
        return Charge.create(chargeMap);
    }
}