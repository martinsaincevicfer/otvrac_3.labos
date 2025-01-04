import FileDownloadButton from "./FileDownloadButton.jsx";

const Index = () => {
    return (
        <>
            <a href="http://localhost:5173/datatable">Datatable</a>
            <FileDownloadButton fileName={"serije.csv"} />
            <FileDownloadButton fileName={"serije.json"} />

            <h1>README</h1>

            <h2>Opis skupa podataka</h2>
            <p>
                Ovaj skup podataka sadrži informacije o televizijskim serijama i njihovim epizodama.
                Uključuje detalje o serijama poput naziva, žanra, godine izlaska, ocjene, broja sezona,
                jezika, autora i mreže, kao i podatke o epizodama, uključujući naziv, sezonu, broj epizode,
                datum emitiranja, trajanje, ocjenu, scenarista i redatelja. Ovaj skup podataka može biti koristan
                za analizu serija i njihovih epizoda, omogućujući korisnicima praćenje, ocjenjivanje i usporedbu
                svojih omiljenih serija i njihovih epizoda.
            </p>

            <h2>Struktura baze podataka</h2>
            <p>Baza podataka sadrži dvije glavne tablice:</p>
            <ul>
                <li><strong>serije</strong>: Sadrži informacije o serijama.</li>
                <li><strong>epizode</strong>: Sadrži informacije o epizodama koje pripadaju serijama.</li>
            </ul>

            <h3>Tablica <code>serije</code>:</h3>
            <ul>
                <li><strong>id</strong>: Jedinstveni identifikator serije.</li>
                <li><strong>naslov</strong>: Naziv serije.</li>
                <li><strong>zanr</strong>: Žanr serije.</li>
                <li><strong>godina_izlaska</strong>: Godina kada je serija prvi put emitirana.</li>
                <li><strong>ocjena</strong>: Prosječna ocjena serije (na skali 1-10).</li>
                <li><strong>broj_sezona</strong>: Ukupan broj sezona serije.</li>
                <li><strong>jezik</strong>: Jezik na kojem se serija emitira.</li>
                <li><strong>autor</strong>: Autor serije.</li>
                <li><strong>mreza</strong>: Mreža na kojoj se serija emitira.</li>
            </ul>

            <h3>Tablica <code>epizode</code>:</h3>
            <ul>
                <li><strong>id</strong>: Jedinstveni identifikator epizode.</li>
                <li><strong>serija_id</strong>: Identifikator serije kojoj epizoda pripada (strani ključ prema tablici <code>serije</code>).</li>
                <li><strong>naziv_epizode</strong>: Naziv epizode.</li>
                <li><strong>sezona</strong>: Sezona u kojoj je epizoda emitirana.</li>
                <li><strong>broj_epizode</strong>: Redni broj epizode unutar sezone.</li>
                <li><strong>datum_emitiranja</strong>: Datum kada je epizoda emitirana.</li>
                <li><strong>trajanje</strong>: Trajanje epizode u minutama.</li>
                <li><strong>ocjena</strong>: Ocjena epizode (na skali 1-10).</li>
                <li><strong>scenarist</strong>: Scenarist epizode.</li>
                <li><strong>redatelj</strong>: Redatelj epizode.</li>
            </ul>

            <h2>Metapodaci</h2>
            <ul>
                <li><strong>Opis otvorene licencije</strong>: Ovaj skup podataka je licenciran pod Creative Commons Attribution 1.0 (CC BY 1.0).</li>
                <li><strong>Naziv autora</strong>: Martin Šainčević</li>
                <li><strong>Verzija skupa podataka</strong>: 1.0</li>
                <li><strong>Jezik</strong>: Hrvatski</li>
            </ul>

            <h2>Ključne riječi</h2>
            <p>serije, epizode, televizija, zabava, analiza</p>

            <h2>Datum objave</h2>
            <p>11.11.2024</p>

            <h2>Frekvencija ažuriranja</h2>
            <p>Po potrebi</p>

            <h2>Tema</h2>
            <p>Televizija, zabava</p>

            <h2>Kontakt</h2>
            <p>martin.saincevic@fer.unizg.hr</p>
        </>
    );
};

export default Index;