package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.CommandProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommandProductRepository extends JpaRepository<CommandProduct, Long> {
}
