package com.otvrac.backend.specification;

import com.otvrac.backend.domain.Serije;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class SerijeSpecification {

    public static Specification<Serije> hasCombinedAttribute(String attribute, String value) {
        return (root, query, criteriaBuilder) -> {
            if (attribute.equals("sve")) {
                return criteriaBuilder.or(
                        // Filtriranje po atributima serija
                        criteriaBuilder.like(root.get("naslov"), "%" + value + "%"),
                        criteriaBuilder.like(root.get("zanr"), "%" + value + "%"),
                        criteriaBuilder.like(root.get("jezik"), "%" + value + "%"),
                        criteriaBuilder.like(root.get("autor"), "%" + value + "%"),
                        criteriaBuilder.like(root.get("mreza"), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.get("godinaIzlaska")), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.get("ocjena")), "%" + value + "%"),
                        // Filtriranje po atributima epizoda
                        criteriaBuilder.like(root.join("epizode", JoinType.LEFT).get("nazivEpizode"), "%" + value + "%"),
                        criteriaBuilder.like(root.join("epizode", JoinType.LEFT).get("scenarist"), "%" + value + "%"),
                        criteriaBuilder.like(root.join("epizode", JoinType.LEFT).get("redatelj"), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("sezona")), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("brojEpizode")), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("datumEmitiranja")), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("trajanje")), "%" + value + "%"),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("ocjena")), "%" + value + "%")
                );
            } else if (attribute.startsWith("epizode.")) {
                return criteriaBuilder.like(
                        root.join("epizode", JoinType.LEFT).get(attribute.substring(8)), "%" + value + "%"
                );
            } else {
                return criteriaBuilder.like(root.get(attribute), "%" + value + "%");
            }
        };
    }
}
