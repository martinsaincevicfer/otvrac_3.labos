package com.otvrac.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otvrac.backend.domain.Epizode;
import com.otvrac.backend.domain.Serije;
import com.otvrac.backend.service.SerijeService;
import com.otvrac.backend.wrapper.Link;
import com.otvrac.backend.wrapper.ResponseWrapper;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/serije")
public class SerijeController {

    @Autowired
    private final SerijeService serijeService;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    public SerijeController(SerijeService serijeService) {
        this.serijeService = serijeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSerije() {
        List<Serije> serije = serijeService.getAllSerije();

        List<Object> serijeWithLinks = Collections.singletonList(serije.stream()
                .map(serija -> {
                    List<Link> links = List.of(
                            new Link("/api/serije/" + serija.getId(), "self", "GET"),
                            new Link("/api/serije/" + serija.getId() + "/epizode", "epizode", "GET")
                    );
                    ResponseWrapper response = new ResponseWrapper("OK", "Fetched serija", serija);
                    response.setLinks(links);
                    return response;
                })
                .toList());

        return ResponseEntity.ok(new ResponseWrapper("OK", "Fetched whole database", serijeWithLinks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSerijaById(@PathVariable Integer id) {
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ResponseWrapper("Bad Request", "Invalid ID: ID must be a positive integer", null));
        }

        Serije serija = serijeService.getSerijaById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serija with ID " + id + " not found"));

        List<Link> links = List.of(
                new Link("/api/serije/" + id, "self", "GET"),
                new Link("/api/serije/" + id + "/epizode", "epizode", "GET"),
                new Link("/api/serije/" + id, "update", "PUT"),
                new Link("/api/serije/" + id, "delete", "DELETE")
        );

        ResponseWrapper response = new ResponseWrapper("OK", "Fetched serija with ID: " + id, serija);
        response.setLinks(links);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/epizode")
    public ResponseEntity<?> getEpizodeBySerijaId(@PathVariable Integer id) {
        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ResponseWrapper("Bad Request", "Invalid ID: ID must be a positive integer", null));
        }

        Serije serija = serijeService.getSerijaById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serija with ID " + id + " not found"));

        List<Epizode> epizode = serija.getEpizode();

        return ResponseEntity.ok(new ResponseWrapper("OK", "Fetched epizode for serija ID: " + id, epizode));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSerija(@RequestBody Serije serija) {
        Serije novaSerija = serijeService.createSerija(serija);

        List<Link> links = List.of(
                new Link("/api/serije/" + novaSerija.getId(), "self", "GET"),
                new Link("/api/serije/" + novaSerija.getId(), "update", "PUT"),
                new Link("/api/serije/" + novaSerija.getId(), "delete", "DELETE")
        );

        ResponseWrapper response = new ResponseWrapper("Created", "New serija created successfully", novaSerija);
        response.setLinks(links);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSerija(@PathVariable Integer id, @RequestBody Serije updatedSerija) {
        Serije serija = serijeService.updateSerija(id, updatedSerija);

        List<Link> links = List.of(
                new Link("/api/serije/" + id, "self", "GET"),
                new Link("/api/serije/" + id, "delete", "DELETE")
        );

        ResponseWrapper response = new ResponseWrapper("OK", "Serija updated successfully", serija);
        response.setLinks(links);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSerija(@PathVariable Integer id) {
        serijeService.deleteSerija(id);
        return ResponseEntity.ok(new ResponseWrapper("OK", "Serija deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSerijeWithCombinedFilter(
            @RequestParam(required = false, defaultValue = "sve") String attribute,
            @RequestParam(required = false, defaultValue = "") String filter) {

        List<Serije> serije = filter == null || filter.trim().isEmpty()
                ? serijeService.getAllSerije()
                : serijeService.getSerijeWithFilteredAttributes(attribute, filter);

        return ResponseEntity.ok(new ResponseWrapper("OK", "Fetched filtered results", serije));
    }

    @GetMapping("/download/json")
    public ResponseEntity<InputStreamResource> downloadJson(
            @RequestParam(required = false, defaultValue = "sve") String attribute,
            @RequestParam(required = false, defaultValue = "") String filter) throws IOException {

        List<Serije> serijeList = serijeService.search(filter, attribute);

        String json = jacksonObjectMapper.writeValueAsString(serijeList);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(json.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"serije.json\"")
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/download/csv")
    public ResponseEntity<InputStreamResource> downloadCsv(
            @RequestParam(required = false, defaultValue = "sve") String attribute,
            @RequestParam(required = false, defaultValue = "") String filter) throws IOException {

        List<Serije> serijeList = serijeService.search(filter, attribute);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(byteArrayOutputStream);

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Id", "Naslov", "Zanr", "Godina Izlaska", "Ocjena", "Broj Sezona", "Jezik", "Autor", "Mreza", "Naziv Epizode", "Sezona", "Broj Epizode", "Datum Emitiranja", "Trajanje", "Ocjena Epizode", "Scenarist", "Redatelj"));
        for (Serije serija : serijeList) {
            for (Epizode epizoda : serija.getEpizode()) {
                csvPrinter.printRecord(
                        serija.getId(),
                        serija.getNaslov(),
                        serija.getZanr(),
                        serija.getGodinaIzlaska(),
                        serija.getOcjena(),
                        serija.getBrojSezona(),
                        serija.getJezik(),
                        serija.getAutor(),
                        serija.getMreza(),
                        epizoda.getNazivEpizode(),
                        epizoda.getSezona(),
                        epizoda.getBrojEpizode(),
                        epizoda.getDatumEmitiranja(),
                        epizoda.getTrajanje(),
                        epizoda.getOcjena(),
                        epizoda.getScenarist(),
                        epizoda.getRedatelj()
                );
            }
        }
        csvPrinter.flush();
        csvPrinter.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"serije.csv\"")
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
