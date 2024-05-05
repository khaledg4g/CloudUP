package com.projetpi.cloudup.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATIE_ACCOUNT("activate_account");
    private final String name ;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
