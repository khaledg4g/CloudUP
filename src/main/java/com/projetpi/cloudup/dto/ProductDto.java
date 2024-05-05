package com.projetpi.cloudup.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDto {
    private Long id ;
    private String name ;
    private String description ;
    private double price ;
    private Integer rating ;
    private Integer ratingsUsersNumber ;
    private boolean available ;
    private String imageUrl ;
}
