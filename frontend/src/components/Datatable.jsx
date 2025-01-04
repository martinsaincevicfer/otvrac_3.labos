import { useEffect, useState } from "react";
import axios from "axios";

const downloadCSV = (data) => {
    const headers = [
        "Naslov", "Žanr", "Godina Izlaska", "Ocjena", "Broj Sezona", "Jezik", "Autor", "Mreža",
        "Naziv Epizode", "Sezona", "Broj Epizode", "Datum Emitiranja", "Trajanje", "Ocjena Epizode",
        "Scenarist", "Redatelj"
    ];

    const rows = data.flatMap(serija =>
        serija.epizode.map(epizoda => [
            serija.naslov,
            serija.zanr,
            serija.godinaIzlaska,
            serija.ocjena,
            serija.brojSezona,
            serija.jezik,
            serija.autor,
            serija.mreza,
            epizoda.nazivEpizode,
            epizoda.sezona,
            epizoda.brojEpizode,
            epizoda.datumEmitiranja,
            epizoda.trajanje,
            epizoda.ocjena,
            epizoda.scenarist,
            epizoda.redatelj
        ])
    );

    const csvContent = [headers, ...rows].map(row => row.join(",")).join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "serije.csv";
    link.click();
};

const downloadJSON = (data) => {
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "serije.json";
    link.click();
};

const Datatable = () => {
    const params = new URLSearchParams(window.location.search);
    const initialAttribute = params.get('attribute') || 'sve';
    const initialFilter = params.get('filter') || '';

    const [serije, setSerije] = useState([]);
    const [attribute, setAttribute] = useState(initialAttribute);
    const [filter, setFilter] = useState(initialFilter);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/serije/search`, {
                    params: { attribute, filter }
                });
                setSerije(response.data.response);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, [attribute, filter]);

    return (
        <div>
            <h1>Prikaz serija</h1>
            <form onSubmit={(e) => e.preventDefault()}>
                <label htmlFor="pretragaPolje">Polje za pretragu:</label>
                <input
                    type="text"
                    value={filter}
                    onChange={(e) => setFilter(e.target.value)}
                    placeholder="Pretraži..."
                />

                <label>Pretraži po:</label>
                <select value={attribute} onChange={(e) => setAttribute(e.target.value)}>
                    <option value="sve">Sve</option>
                    <option value="naslov">Naslov</option>
                    <option value="zanr">Žanr</option>
                    <option value="godinaIzlaska">Godina izlaska</option>
                    <option value="ocjena">Ocjena</option>
                    <option value="brojSezona">Broj sezona</option>
                    <option value="jezik">Jezik</option>
                    <option value="autor">Autor</option>
                    <option value="mreza">Mreža</option>
                    <option value="epizode.nazivEpizode">Naziv epizode</option>
                    <option value="epizode.sezona">Sezona</option>
                    <option value="epizode.brojEpizode">Broj epizode</option>
                    <option value="epizode.datumEmitiranja">Datum emitiranja</option>
                    <option value="epizode.trajanje">Trajanje</option>
                    <option value="epizode.ocjena">Ocjena epizode</option>
                    {/* Dodata opcija */}
                    <option value="epizode.scenarist">Scenarist</option>
                    <option value="epizode.redatelj">Redatelj</option>
                </select>

                <button type="submit">Pretraži</button>
            </form>

            <table>
                <thead>
                <tr>
                    <th>Naslov</th>
                    <th>Žanr</th>
                    <th>Godina Izlaska</th>
                    <th>Ocjena</th>
                    <th>Broj Sezona</th>
                    <th>Jezik</th>
                    <th>Autor</th>
                    <th>Mreža</th>
                    <th>Naziv Epizode</th>
                    <th>Sezona</th>
                    <th>Broj Epizode</th>
                    <th>Datum Emitiranja</th>
                    <th>Trajanje</th>
                    <th>Ocjena Epizode</th>
                    <th>Scenarist</th>
                    <th>Redatelj</th>
                </tr>
                </thead>
                <tbody>
                {Array.isArray(serije) && serije.map(serija =>
                    serija.epizode.map(epizoda => (
                        <tr key={`${serija.id}-${epizoda.id}`}>
                            <td>{serija.naslov}</td>
                            <td>{serija.zanr}</td>
                            <td>{serija.godinaIzlaska}</td>
                            <td>{serija.ocjena}</td>
                            <td>{serija.brojSezona}</td>
                            <td>{serija.jezik}</td>
                            <td>{serija.autor}</td>
                            <td>{serija.mreza}</td>
                            <td>{epizoda.nazivEpizode}</td>
                            <td>{epizoda.sezona}</td>
                            <td>{epizoda.brojEpizode}</td>
                            <td>{epizoda.datumEmitiranja}</td>
                            <td>{epizoda.trajanje}</td>
                            <td>{epizoda.ocjena}</td>
                            <td>{epizoda.scenarist}</td>
                            <td>{epizoda.redatelj}</td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>

            <div>
                <button onClick={() => downloadCSV(serije)}>Preuzmi CSV</button>
                <button onClick={() => downloadJSON(serije)}>Preuzmi JSON</button>
            </div>
        </div>
    );
};

export default Datatable;
