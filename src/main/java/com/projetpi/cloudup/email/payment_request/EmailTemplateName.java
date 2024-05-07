package com.projetpi.cloudup.email.payment_request;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    PAYMENT_REQUEST("payment_request");
    private final String name ;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
