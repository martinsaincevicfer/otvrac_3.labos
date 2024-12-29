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
        String lowerFilter = filter.toLowerCase(); // Pretvori filter u mala slova
        return serija.getNaslov().toLowerCase().contains(lowerFilter) ||
                (serija.getZanr() != null && serija.getZanr().toLowerCase().contains(lowerFilter)) ||
                (serija.getAutor() != null && serija.getAutor().toLowerCase().contains(lowerFilter)) ||
                (serija.getMreza() != null && serija.getMreza().toLowerCase().contains(lowerFilter)) ||
                (serija.getJezik() != null && serija.getJezik().toLowerCase().contains(lowerFilter)) ||
                (serija.getGodinaIzlaska() != null && serija.getGodinaIzlaska().toString().toLowerCase().contains(lowerFilter)) ||
                (serija.getOcjena() != null && serija.getOcjena().toString().toLowerCase().contains(lowerFilter)) ||
                (serija.getBrojSezona() != null && serija.getBrojSezona().toString().toLowerCase().contains(lowerFilter));
    }

    private boolean epizodaMatchesFilterForSve(Epizode epizoda, String filter) {
        String lowerFilter = filter.toLowerCase(); // Pretvori filter u mala slova
        return epizoda.getNazivEpizode().toLowerCase().contains(lowerFilter) ||
                (epizoda.getScenarist() != null && epizoda.getScenarist().toLowerCase().contains(lowerFilter)) ||
                (epizoda.getRedatelj() != null && epizoda.getRedatelj().toLowerCase().contains(lowerFilter)) ||
                (epizoda.getSezona() != null && epizoda.getSezona().toString().toLowerCase().contains(lowerFilter)) ||
                (epizoda.getBrojEpizode() != null && epizoda.getBrojEpizode().toString().toLowerCase().contains(lowerFilter)) ||
                (epizoda.getDatumEmitiranja() != null && epizoda.getDatumEmitiranja().toString().toLowerCase().contains(lowerFilter)) ||
                (epizoda.getTrajanje() != null && epizoda.getTrajanje().toString().toLowerCase().contains(lowerFilter)) ||
                (epizoda.getOcjena() != null && epizoda.getOcjena().toString().toLowerCase().contains(lowerFilter));
    }

    private boolean epizodaMatchesFilter(Epizode epizoda, String attribute, String value) {
        String lowerValue = value.toLowerCase(); // Pretvori vrijednost u mala slova
        return switch (attribute) {
            case "epizode.nazivEpizode" -> epizoda.getNazivEpizode().toLowerCase().contains(lowerValue);
            case "epizode.scenarist" -> epizoda.getScenarist().toLowerCase().contains(lowerValue);
            case "epizode.redatelj" -> epizoda.getRedatelj().toLowerCase().contains(lowerValue);
            case "epizode.sezona" -> epizoda.getSezona() != null && epizoda.getSezona().toString().toLowerCase().contains(lowerValue);
            case "epizode.brojEpizode" -> epizoda.getBrojEpizode() != null && epizoda.getBrojEpizode().toString().toLowerCase().contains(lowerValue);
            case "epizode.datumEmitiranja" -> epizoda.getDatumEmitiranja() != null && epizoda.getDatumEmitiranja().toString().toLowerCase().contains(lowerValue);
            case "epizode.trajanje" -> epizoda.getTrajanje() != null && epizoda.getTrajanje().toString().toLowerCase().contains(lowerValue);
            case "epizode.ocjena" -> epizoda.getOcjena() != null && epizoda.getOcjena().toString().toLowerCase().contains(lowerValue);
            default -> false;
        };
    }

    public List<Serije> search(String filter, String attribute) {
        if (attribute != null && filter != null) {
            return getSerijeWithFilteredAttributes(attribute, filter);
        }
        return getAllSerije();
    }
}
