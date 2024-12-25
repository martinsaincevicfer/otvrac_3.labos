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
                String epizodeAttribute = attribute.substring(8);
                if (epizodeAttribute.equals("ocjena") || epizodeAttribute.equals("sezona") || epizodeAttribute.equals("brojEpizode") || epizodeAttribute.equals("trajanje")) {
                    try {
                        Double numericValue = Double.parseDouble(value);
                        return criteriaBuilder.equal(root.join("epizode", JoinType.LEFT).get(epizodeAttribute), numericValue);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid numeric format for attribute: " + epizodeAttribute);
                    }
                }
                return criteriaBuilder.like(
                        root.join("epizode", JoinType.LEFT).get(epizodeAttribute), "%" + value + "%"
                );
            } else {
                if (attribute.equals("ocjena") || attribute.equals("godinaIzlaska")) {
                    try {
                        Double numericValue = Double.parseDouble(value);
                        return criteriaBuilder.equal(root.get(attribute), numericValue);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid numeric format for attribute: " + attribute);
                    }
                }
                return criteriaBuilder.like(root.get(attribute), "%" + value + "%");
            }
        };
    }
}
