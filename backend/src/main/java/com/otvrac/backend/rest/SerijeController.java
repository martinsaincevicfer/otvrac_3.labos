package com.otvrac.backend.rest;

import com.otvrac.backend.domain.Serije;
import com.otvrac.backend.service.SerijeService;
import com.otvrac.backend.specification.SerijeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/serije")
public class SerijeController {
    @Autowired
    private final SerijeService serijeService;

    public SerijeController(SerijeService serijeService) {
        this.serijeService = serijeService;
    }

    /*
    @GetMapping("/all")
    public List<Serije> getSerije(@RequestParam(required = false) String attribute,
                                  @RequestParam(required = false) String value) {
        if (attribute != null && value != null) {
            Specification<Serije> spec = SerijeSpecification.hasAttribute(attribute, value);
            return serijeService.getFilteredSerije(spec);
        } else {
            return serijeService.getAllSerije();
        }
    }*/

    @GetMapping("/combined-filter")
    public List<Serije> getSerijeWithCombinedFilter(@RequestParam(required = false) String attribute,
                                                    @RequestParam(required = false) String value) {
        if (attribute != null && value != null) {
            return serijeService.getSerijeWithFilteredAttributes(attribute, value);
        } else {
            return serijeService.getAllSerije();
        }
    }
}
