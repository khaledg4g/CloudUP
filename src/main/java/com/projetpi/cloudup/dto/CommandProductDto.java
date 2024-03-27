package com.projetpi.cloudup.dto;

import com.projetpi.cloudup.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandProductDto {
    private Long id ;
    private Integer quantity;
    private Product product;
}
