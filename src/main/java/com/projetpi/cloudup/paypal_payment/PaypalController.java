package com.projetpi.cloudup.paypal_payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class PaypalController {
    private final PaypalService paypalService;

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @PostMapping("/payment/create")
    public RedirectView createPayment( @RequestParam("method") String method,
                                       @RequestParam("amount") String amount,
                                       @RequestParam("currency") String currency,
                                       @RequestParam("description") String description) {
        try {
            String cancelUrl = "http://localhost:4200/Home/students/booking-success";
            String successUrl = "http://localhost:4200/Home/students/booking-success";
            Payment payment = paypalService.createPayment(
                    Double.valueOf(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new RedirectView(link.getHref());
                }
            }

        } catch (PayPalRESTException e) {
            log.error("error occured", e);
        }
        return new RedirectView("/payment/error");

    }


    @GetMapping("/Home/students/booking-success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("payerId") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("error occured", e);
        }
        return "paymentSuccess";
    }

    @GetMapping("/Home/students/booking-cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/Home/students/booking-error")
    public String paymentError() {
        return "paymentError";
    }



}
