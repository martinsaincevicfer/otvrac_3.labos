package com.otvrac.backend.service;

import com.otvrac.backend.dao.EpizodeRepository;
import com.otvrac.backend.dao.SerijeRepository;
import com.otvrac.backend.domain.Epizode;
import com.otvrac.backend.domain.Serije;
import com.otvrac.backend.specification.SerijeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerijeService {

    @Autowired
    private final SerijeRepository serijeRepository;

    @Autowired
    private final EpizodeRepository epizodeRepository;

    public SerijeService(SerijeRepository serijeRepository, EpizodeRepository epizodeRepository) {
        this.serijeRepository = serijeRepository;
        this.epizodeRepository = epizodeRepository;
    }

    public List<Serije> getAllSerije() {
        return serijeRepository.findAll();
    }

    public Optional<Serije> getSerijaById(Integer id) {
        return serijeRepository.findById(id);
    }

    public Serije createSerija(Serije serija) {
        return serijeRepository.save(serija);
    }

    public Serije updateSerija(Integer id, Serije updatedSerija) {
        Serije existingSerija = serijeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serija nije pronađena"));
        existingSerija.setNaslov(updatedSerija.getNaslov());
        existingSerija.setZanr(updatedSerija.getZanr());
        existingSerija.setGodinaIzlaska(updatedSerija.getGodinaIzlaska());
        existingSerija.setOcjena(updatedSerija.getOcjena());
        existingSerija.setBrojSezona(updatedSerija.getBrojSezona());
        existingSerija.setJezik(updatedSerija.getJezik());
        existingSerija.setAutor(updatedSerija.getAutor());
        existingSerija.setMreza(updatedSerija.getMreza());
        return serijeRepository.save(existingSerija);
    }

    public void deleteSerija(Integer id) {
        serijeRepository.deleteById(id);
    }

    public List<Serije> getFilteredSerije(Specification<Serije> spec) {
        return serijeRepository.findAll(spec);
    }

    public List<Serije> getSerijeWithFilteredAttributes(String attribute, String filter) {
        Specification<Serije> spec = SerijeSpecification.hasCombinedAttribute(attribute, filter);

        return serijeRepository.findAll(spec).stream()
                .peek(serija -> {
                    if (attribute.equals("sve")) {
                        boolean matchesSerija = serijaMatchesFilterForSve(serija, filter);
                        if (!matchesSerija) {
                            List<Epizode> filteredEpizode = serija.getEpizode().stream()
                                    .filter(epizoda -> epizodaMatchesFilterForSve(epizoda, filter))
                                    .toList();
                            serija.setEpizode(filteredEpizode);
                        }
                    } else if (attribute.startsWith("epizode.")) {
                        List<Epizode> filteredEpizode = serija.getEpizode().stream()
                                .filter(epizoda -> epizodaMatchesFilter(epizoda, attribute, filter))
                                .toList();
                        serija.setEpizode(filteredEpizode);
                    }
                })
                .filter(serija -> !serija.getEpizode().isEmpty() || attribute.equals("sve") || !attribute.startsWith("epizode."))
                .toList();
    }

    private boolean serijaMatchesFilterForSve(Serije serija, String filter) {
        return serija.getNaslov().contains(filter) ||
                (serija.getZanr() != null && serija.getZanr().contains(filter)) ||
                (serija.getAutor() != null && serija.getAutor().contains(filter)) ||
                (serija.getMreza() != null && serija.getMreza().contains(filter)) ||
                (serija.getJezik() != null && serija.getJezik().contains(filter)) ||
                (serija.getGodinaIzlaska() != null && serija.getGodinaIzlaska().toString().contains(filter)) ||
                (serija.getOcjena() != null && serija.getOcjena().toString().contains(filter)) ||
                (serija.getBrojSezona() != null && serija.getBrojSezona().toString().contains(filter));
    }

    private boolean epizodaMatchesFilterForSve(Epizode epizoda, String filter) {
        return epizoda.getNazivEpizode().contains(filter) ||
                (epizoda.getScenarist() != null && epizoda.getScenarist().contains(filter)) ||
                (epizoda.getRedatelj() != null && epizoda.getRedatelj().contains(filter)) ||
                (epizoda.getSezona() != null && epizoda.getSezona().toString().contains(filter)) ||
                (epizoda.getBrojEpizode() != null && epizoda.getBrojEpizode().toString().contains(filter)) ||
                (epizoda.getDatumEmitiranja() != null && epizoda.getDatumEmitiranja().toString().contains(filter)) ||
                (epizoda.getTrajanje() != null && epizoda.getTrajanje().toString().contains(filter)) ||
                (epizoda.getOcjena() != null && epizoda.getOcjena().toString().contains(filter));
    }

    private boolean epizodaMatchesFilter(Epizode epizoda, String attribute, String value) {
        return switch (attribute) {
            case "epizode.nazivEpizode" -> epizoda.getNazivEpizode().contains(value);
            case "epizode.scenarist" -> epizoda.getScenarist().contains(value);
            case "epizode.redatelj" -> epizoda.getRedatelj().contains(value);
            case "epizode.sezona" -> epizoda.getSezona() != null && epizoda.getSezona().toString().contains(value);
            case "epizode.brojEpizode" -> epizoda.getBrojEpizode() != null && epizoda.getBrojEpizode().toString().contains(value);
            case "epizode.datumEmitiranja" -> epizoda.getDatumEmitiranja() != null && epizoda.getDatumEmitiranja().toString().contains(value);
            case "epizode.trajanje" -> epizoda.getTrajanje() != null && epizoda.getTrajanje().toString().contains(value);
            case "epizode.ocjena" -> epizoda.getOcjena() != null && epizoda.getOcjena().toString().contains(value);
            default -> false;
        };
    }
}
