package com.projetpi.cloudup.paypal_payment;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalService {
    private final APIContext apiContext;

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException
    {
        if (apiContext == null) {
            log.error("APIContext is not initialized.");
            throw new IllegalStateException("APIContext is not initialized.");
        }
        else {
            Amount amount = new Amount();
            amount.setCurrency(currency);
            amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));//"%.2f", total
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(description);
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod(method);

            Payment payment = new Payment();
            payment.setIntent(intent);
            payment.setPayer(payer);
            payment.setTransactions(transactions);

            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);

            payment.setRedirectUrls(redirectUrls);


        return payment.create(apiContext);}
    }

    public Payment executePayment(String paymentId
            , String payerId)
            throws PayPalRESTException {

        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        return payment.execute(apiContext, execution);
    }


}
