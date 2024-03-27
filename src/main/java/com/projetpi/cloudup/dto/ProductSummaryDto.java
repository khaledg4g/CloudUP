package com.projetpi.cloudup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSummaryDto {
    private String name ;
    private String description ;
    private double price ;
    private String imageUrl ;
}
