package com.otvrac.backend.rest;

import com.otvrac.backend.domain.Epizode;
import com.otvrac.backend.service.EpizodeService;
import com.otvrac.backend.wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/epizode")
public class EpizodeController {

    @Autowired
    private final EpizodeService epizodeService;

    public EpizodeController(EpizodeService epizodeService) {
        this.epizodeService = epizodeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEpizode() {
        List<Epizode> epizode = epizodeService.getAllEpizode();
        return ResponseEntity.ok(new ResponseWrapper("success", epizode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEpizodaById(@PathVariable Integer id) {
        return epizodeService.getEpizodaById(id)
                .map(epizoda -> ResponseEntity.ok(new ResponseWrapper("success", epizoda)))
                .orElse(ResponseEntity.status(404).body(new ResponseWrapper("error", "Epizoda nije pronađena")));
    }

    @GetMapping("/serija/{serijaId}")
    public ResponseEntity<?> getEpizodeBySerijaId(@PathVariable Integer serijaId) {
        List<Epizode> epizode = epizodeService.getEpizodeBySerijaId(serijaId);
        return ResponseEntity.ok(new ResponseWrapper("success", epizode));
    }
}
