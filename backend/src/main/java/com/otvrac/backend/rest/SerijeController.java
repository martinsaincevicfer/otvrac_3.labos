package com.otvrac.backend.rest;

import com.otvrac.backend.domain.Serije;
import com.otvrac.backend.service.SerijeService;
import com.otvrac.backend.wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serije")
public class SerijeController {

    @Autowired
    private final SerijeService serijeService;

    public SerijeController(SerijeService serijeService) {
        this.serijeService = serijeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSerije() {
        List<Serije> serije = serijeService.getAllSerije();
        return ResponseEntity.ok(new ResponseWrapper("success", serije));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSerijaById(@PathVariable Integer id) {
        return serijeService.getSerijaById(id)
                .map(serija -> ResponseEntity.ok(new ResponseWrapper("success", serija)))
                .orElse(ResponseEntity.status(404).body(new ResponseWrapper("error", "Serija nije pronađena")));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSerijeWithCombinedFilter(
            @RequestParam(required = false) String attribute,
            @RequestParam(required = false) String filter) {
        List<Serije> serije = attribute != null && filter != null
                ? serijeService.getSerijeWithFilteredAttributes(attribute, filter)
                : serijeService.getAllSerije();
        return ResponseEntity.ok(new ResponseWrapper("success", serije));
    }

    @PostMapping
    public ResponseEntity<?> createSerija(@RequestBody Serije serija) {
        Serije novaSerija = serijeService.createSerija(serija);
        return ResponseEntity.ok(new ResponseWrapper("success", novaSerija));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSerija(@PathVariable Integer id, @RequestBody Serije updatedSerija) {
        Serije serija = serijeService.updateSerija(id, updatedSerija);
        return ResponseEntity.ok(new ResponseWrapper("success", serija));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSerija(@PathVariable Integer id) {
        serijeService.deleteSerija(id);
        return ResponseEntity.ok(new ResponseWrapper("success", "Serija obrisana"));
    }
}
