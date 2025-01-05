--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: epizode; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.epizode (
    id integer NOT NULL,
    serija_id integer,
    naziv_epizode character varying(255) NOT NULL,
    sezona integer,
    broj_epizode integer,
    datum_emitiranja date,
    trajanje integer,
    ocjena double precision,
    scenarist character varying(255),
    redatelj character varying(255)
);


ALTER TABLE public.epizode OWNER TO postgres;

--
-- Name: epizode_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.epizode_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.epizode_id_seq OWNER TO postgres;

--
-- Name: epizode_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.epizode_id_seq OWNED BY public.epizode.id;


--
-- Name: serije; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.serije (
    id integer NOT NULL,
    naslov character varying(255) NOT NULL,
    zanr character varying(255),
    godina_izlaska integer,
    ocjena double precision,
    broj_sezona integer,
    jezik character varying(100),
    autor character varying(255),
    mreza character varying(100)
);


ALTER TABLE public.serije OWNER TO postgres;

--
-- Name: serije_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.serije_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.serije_id_seq OWNER TO postgres;

--
-- Name: serije_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.serije_id_seq OWNED BY public.serije.id;


--
-- Name: epizode id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epizode ALTER COLUMN id SET DEFAULT nextval('public.epizode_id_seq'::regclass);


--
-- Name: serije id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.serije ALTER COLUMN id SET DEFAULT nextval('public.serije_id_seq'::regclass);


--
-- Data for Name: epizode; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.epizode (id, serija_id, naziv_epizode, sezona, broj_epizode, datum_emitiranja, trajanje, ocjena, scenarist, redatelj) FROM stdin;
1	1	Pilot	1	1	2008-01-20	58	9	Vince Gilligan	Adam Bernstein
2	1	Cat in the Bag...	1	2	2008-01-27	48	8.5	Vince Gilligan	Adam Bernstein
3	2	Winter Is Coming	1	1	2011-04-17	62	9.5	David Benioff	Tim Van Patten
4	2	The Kingsroad	1	2	2011-04-24	56	9	David Benioff	Tim Van Patten
5	3	The Vanishing of Will Byers	1	1	2016-07-15	47	8.8	The Duffer Brothers	The Duffer Brothers
6	3	The Weirdo on Maple Street	1	2	2016-07-15	55	8.7	The Duffer Brothers	The Duffer Brothers
7	4	The Ends Beginning	1	1	2019-12-20	61	8	Lauren Schmidt Hissrich	Alik Sakharov
8	4	Four Marks	1	2	2019-12-20	60	8.5	Lauren Schmidt Hissrich	Alik Sakharov
9	5	The Pilot	1	1	1994-09-22	22	9	David Crane	James Burrows
10	5	The One with the Sonogram at the End	1	2	1994-09-29	22	8.7	David Crane	James Burrows
11	6	A Study in Pink	1	1	2010-07-25	88	9.2	Mark Gatiss	Paul McGuigan
12	6	The Blind Banker	1	2	2010-08-01	88	8.9	Mark Gatiss	Paul McGuigan
13	7	Chapter 1: The Mandalorian	1	1	2019-11-12	39	8.7	Jon Favreau	Dave Filoni
14	7	Chapter 2: The Child	1	2	2019-11-15	32	9.1	Jon Favreau	Dave Filoni
15	8	Pilot	1	1	2005-03-24	22	9	Greg Daniels	Greg Daniels
16	8	Diversity Day	1	2	2005-03-29	22	8.5	Greg Daniels	Greg Daniels
17	9	Chapter 1	1	1	2013-02-01	50	8.8	Beau Willimon	Beau Willimon
18	9	Chapter 2	1	2	2013-02-08	50	8.6	Beau Willimon	Beau Willimon
19	10	The National Anthem	1	1	2011-12-04	43	8.4	Charlie Brooker	Paul McGuigan
20	10	Fifteen Million Merits	1	2	2011-12-11	45	9.1	Charlie Brooker	Euros Lyn
\.


--
-- Data for Name: serije; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.serije (id, naslov, zanr, godina_izlaska, ocjena, broj_sezona, jezik, autor, mreza) FROM stdin;
1	Breaking Bad	Krimi	2008	9.5	5	Engleski	Vince Gilligan	AMC
2	Game of Thrones	Akcija	2011	9.3	8	Engleski	David Benioff	HBO
3	Stranger Things	Fantazija	2016	8.7	4	Engleski	The Duffer Brothers	Netflix
4	The Witcher	Akcija	2019	8.2	2	Engleski	Lauren Schmidt Hissrich	Netflix
5	Friends	Komedija	1994	8.9	10	Engleski	David Crane	NBC
6	Sherlock	Krimi	2010	9.1	4	Engleski	Mark Gatiss	BBC
7	The Mandalorian	Akcija	2019	8.8	2	Engleski	Jon Favreau	Disney+
8	The Office	Komedija	2005	8.9	9	Engleski	Greg Daniels	NBC
9	House of Cards	Drama	2013	8.7	6	Engleski	Beau Willimon	Netflix
10	Black Mirror	Drama	2011	8.8	5	Engleski	Charlie Brooker	Netflix
\.


--
-- Name: epizode_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.epizode_id_seq', 1, false);


--
-- Name: serije_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.serije_id_seq', 1, false);


--
-- Name: epizode epizode_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epizode
    ADD CONSTRAINT epizode_pkey PRIMARY KEY (id);


--
-- Name: serije serije_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.serije
    ADD CONSTRAINT serije_pkey PRIMARY KEY (id);


--
-- Name: epizode epizode_serija_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epizode
    ADD CONSTRAINT epizode_serija_id_fkey FOREIGN KEY (serija_id) REFERENCES public.serije(id);


--
-- PostgreSQL database dump complete
--

