package com.otvrac.backend.specification;

import com.otvrac.backend.domain.Serije;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class SerijeSpecification {
    public static Specification<Serije> hasCombinedAttribute(String attribute, String value) {
        return (root, query, criteriaBuilder) -> {
            String lowerValue = "%" + value.toLowerCase() + "%"; // Normaliziraj vrijednost na mala slova

            if (attribute.equals("sve")) {
                return criteriaBuilder.or(
                        // Filtriranje po atributima serija (case-insensitive)
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("naslov")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("zanr")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("jezik")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("autor")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("mreza")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.get("godinaIzlaska"))), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.get("ocjena"))), lowerValue),
                        // Filtriranje po atributima epizoda (case-insensitive)
                        criteriaBuilder.like(criteriaBuilder.lower(root.join("epizode", JoinType.LEFT).get("nazivEpizode")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.join("epizode", JoinType.LEFT).get("scenarist")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.join("epizode", JoinType.LEFT).get("redatelj")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("sezona"))), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("brojEpizode"))), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("datumEmitiranja"))), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("trajanje"))), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.join("epizode", JoinType.LEFT).get("ocjena"))), lowerValue)
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
                        criteriaBuilder.lower(root.join("epizode", JoinType.LEFT).get(epizodeAttribute)), lowerValue
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
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute)), lowerValue);
            }
        };
    }
}