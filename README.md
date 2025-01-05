# README

## Opis skupa podataka

Ovaj skup podataka sadrži informacije o televizijskim serijama i njihovim epizodama. Uključuje detalje o serijama poput naziva, žanra, godine izlaska, ocjene, broja sezona, jezika, autora i mreže, kao i podatke o epizodama, uključujući naziv, sezonu, broj epizode, datum emitiranja, trajanje, ocjenu, scenarista i redatelja. Ovaj skup podataka može biti koristan za analizu serija i njihovih epizoda, omogućujući korisnicima praćenje, ocjenjivanje i usporedbu svojih omiljenih serija i njihovih epizoda.

## Struktura baze podataka

Baza podataka sadrži dvije glavne tablice:

- **serije**: Sadrži informacije o serijama.
- **epizode**: Sadrži informacije o epizodama koje pripadaju serijama.

### Tablica `serije`:

- `id`: Jedinstveni identifikator serije.
- `naslov`: Naziv serije.
- `zanr`: Žanr serije.
- `godina_izlaska`: Godina kada je serija prvi put emitirana.
- `ocjena`: Prosječna ocjena serije (na skali 1-10).
- `broj_sezona`: Ukupan broj sezona serije.
- `jezik`: Jezik na kojem se serija emitira.
- `autor`: Autor serije.
- `mreza`: Mreža na kojoj se serija emitira.

### Tablica `epizode`:

- `id`: Jedinstveni identifikator epizode.
- `serija_id`: Identifikator serije kojoj epizoda pripada (strani ključ prema tablici `serije`).
- `naziv_epizode`: Naziv epizode.
- `sezona`: Sezona u kojoj je epizoda emitirana.
- `broj_epizode`: Redni broj epizode unutar sezone.
- `datum_emitiranja`: Datum kada je epizoda emitirana.
- `trajanje`: Trajanje epizode u minutama.
- `ocjena`: Ocjena epizode (na skali 1-10).
- `scenarist`: Scenarist epizode.
- `redatelj`: Redatelj epizode.

## Metapodaci

- **Opis otvorene licencije**: Ovaj skup podataka je licenciran pod Creative Commons Attribution 1.0 (CC BY 1.0).
- **Naziv autora**: Martin Šainčević
- **Verzija skupa podataka**: 1.0
- **Jezik**: Hrvatski

## Ključne riječi

serije, epizode, televizija, zabava, analiza

## Datum objave

5.1.2025

## Frekvencija ažuriranja

Po potrebi

## Tema

Televizija, zabava

## Kontakt

martin.saincevic@fer.unizg.hr
