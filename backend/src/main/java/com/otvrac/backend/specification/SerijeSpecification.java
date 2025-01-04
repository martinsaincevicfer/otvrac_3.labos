package com.otvrac.backend.specification;

import com.otvrac.backend.domain.Serije;
import org.springframework.data.jpa.domain.Specification;

public class SerijeSpecification {
    public static Specification<Serije> hasCombinedAttribute(String attribute, String value) {
        return (root, query, criteriaBuilder) -> {
            String lowerValue = "%" + (value == null ? "" : value.toLowerCase()) + "%";

            if (attribute.equals("sve")) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("naslov")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("zanr")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("jezik")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("autor")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("mreza")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.toString(root.get("godinaIzlaska")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.toString(root.get("ocjena")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.toString(root.get("brojSezona")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.join("epizode").get("nazivEpizode")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode").get("sezona")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode").get("brojEpizode")), lowerValue),
                        criteriaBuilder.like(
                                criteriaBuilder.function("to_char", String.class, root.join("epizode").get("datumEmitiranja"),
                                        criteriaBuilder.literal("YYYY-MM-DD")
                                ),
                                lowerValue
                        ),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode").get("trajanje")), lowerValue),
                        criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode").get("ocjena")), lowerValue)
                );
            } else if (attribute.startsWith("epizode.")) {
                String epizodeAttribute = attribute.substring(8);
                if (epizodeAttribute.equals("sezona") || epizodeAttribute.equals("brojEpizode") || epizodeAttribute.equals("trajanje")) {
                    return criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode").get(epizodeAttribute)), "%" + value + "%");
                } else if (epizodeAttribute.equals("datumEmitiranja")) {
                    return criteriaBuilder.like(
                            criteriaBuilder.function("to_char", String.class, root.join("epizode").get(epizodeAttribute),
                                    criteriaBuilder.literal("YYYY-MM-DD")
                            ),
                            "%" + value + "%"
                    );
                } else if (epizodeAttribute.equals("ocjena")) {
                    return criteriaBuilder.like(criteriaBuilder.toString(root.join("epizode").get(epizodeAttribute)), "%" + value + "%");
                } else {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.join("epizode").get(epizodeAttribute)), lowerValue);
                }
            } else {
                if (attribute.equals("godinaIzlaska") || attribute.equals("ocjena") || attribute.equals("brojSezona")) {
                    if (value == null || value.trim().isEmpty()) {
                        return criteriaBuilder.conjunction(); // Neutralni uslov
                    }
                    return criteriaBuilder.like(criteriaBuilder.toString(root.get(attribute)), "%" + value + "%");
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute)), lowerValue);
            }
        };
    }
}
