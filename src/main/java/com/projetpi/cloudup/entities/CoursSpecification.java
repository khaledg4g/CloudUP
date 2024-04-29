package com.projetpi.cloudup.entities;

import org.springframework.data.jpa.domain.Specification;

public class CoursSpecification {
    public static Specification<CoursParticuliers> withOwnerId(Long ownerId)
    {
        return (root,query,criteriaBuilder) -> criteriaBuilder.equal(root.get("professeur").get("idUser"), ownerId);
    }
}
