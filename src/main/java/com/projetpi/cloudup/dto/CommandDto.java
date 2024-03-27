package com.projetpi.cloudup.dto;

import com.projetpi.cloudup.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class CommandDto {
    private Long id ;
    private Set<CommandProductDto> commandProducts;
    private User user;
}
