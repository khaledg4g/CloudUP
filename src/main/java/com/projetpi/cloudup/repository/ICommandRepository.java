package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommandRepository extends JpaRepository<Command,Long> {
}
