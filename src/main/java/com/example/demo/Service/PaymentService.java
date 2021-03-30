package com.example.demo.Service;


import com.example.demo.Model.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;

import javax.naming.AuthenticationException;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException, com.stripe.exception.AuthenticationException;
}

