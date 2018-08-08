--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

-- Started on 2018-08-08 18:12:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2863 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 49159)
-- Name: clinic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clinic (
    id_clinic bigint NOT NULL,
    address character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    medical_center character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone_number character varying(255)
);


ALTER TABLE public.clinic OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 49157)
-- Name: clinic_id_clinic_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clinic_id_clinic_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clinic_id_clinic_seq OWNER TO postgres;

--
-- TOC entry 2864 (class 0 OID 0)
-- Dependencies: 197
-- Name: clinic_id_clinic_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clinic_id_clinic_seq OWNED BY public.clinic.id_clinic;


--
-- TOC entry 196 (class 1259 OID 32793)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 65536)
-- Name: statement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.statement (
    id_statement bigint NOT NULL,
    content text NOT NULL,
    date timestamp without time zone NOT NULL,
    read boolean NOT NULL,
    id_user bigint
);


ALTER TABLE public.statement OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 49168)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id_user bigint NOT NULL,
    blocked boolean,
    email character varying(255),
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255),
    pesel character varying(255),
    phone_number character varying(255),
    role character varying(255) NOT NULL,
    username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 49178)
-- Name: visit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit (
    id_visit bigint NOT NULL,
    visit_date date NOT NULL,
    id_visit_model bigint
);


ALTER TABLE public.visit OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 49186)
-- Name: visit_hour; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit_hour (
    id_visit_hour bigint NOT NULL,
    hour time without time zone NOT NULL,
    id_user bigint,
    id_visit bigint,
    status character varying(255)
);


ALTER TABLE public.visit_hour OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 49184)
-- Name: visit_hour_id_visit_hour_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visit_hour_id_visit_hour_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visit_hour_id_visit_hour_seq OWNER TO postgres;

--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 202
-- Name: visit_hour_id_visit_hour_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_hour_id_visit_hour_seq OWNED BY public.visit_hour.id_visit_hour;


--
-- TOC entry 200 (class 1259 OID 49176)
-- Name: visit_id_visit_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visit_id_visit_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visit_id_visit_seq OWNER TO postgres;

--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 200
-- Name: visit_id_visit_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_id_visit_seq OWNED BY public.visit.id_visit;


--
-- TOC entry 205 (class 1259 OID 49194)
-- Name: visit_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit_model (
    id_visit_model bigint NOT NULL,
    begin_time time without time zone NOT NULL,
    day_interval integer NOT NULL,
    end_date date NOT NULL,
    end_time time without time zone NOT NULL,
    minute_interval integer NOT NULL,
    specialization character varying(255) NOT NULL,
    start_date date NOT NULL,
    id_clinic bigint,
    id_doctor bigint,
    care_type character varying(255) NOT NULL
);


ALTER TABLE public.visit_model OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 49192)
-- Name: visit_model_id_visit_model_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visit_model_id_visit_model_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visit_model_id_visit_model_seq OWNER TO postgres;

--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 204
-- Name: visit_model_id_visit_model_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_model_id_visit_model_seq OWNED BY public.visit_model.id_visit_model;


--
-- TOC entry 2702 (class 2604 OID 49162)
-- Name: clinic id_clinic; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic ALTER COLUMN id_clinic SET DEFAULT nextval('public.clinic_id_clinic_seq'::regclass);


--
-- TOC entry 2703 (class 2604 OID 49181)
-- Name: visit id_visit; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit ALTER COLUMN id_visit SET DEFAULT nextval('public.visit_id_visit_seq'::regclass);


--
-- TOC entry 2704 (class 2604 OID 49189)
-- Name: visit_hour id_visit_hour; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour ALTER COLUMN id_visit_hour SET DEFAULT nextval('public.visit_hour_id_visit_hour_seq'::regclass);


--
-- TOC entry 2705 (class 2604 OID 49197)
-- Name: visit_model id_visit_model; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model ALTER COLUMN id_visit_model SET DEFAULT nextval('public.visit_model_id_visit_model_seq'::regclass);


--
-- TOC entry 2847 (class 0 OID 49159)
-- Dependencies: 198
-- Data for Name: clinic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clinic (id_clinic, address, city, medical_center, name, phone_number) FROM stdin;
1	Warszawska 17	Kraków	Ars-Medica	Przychodnia Medyczna Kraków	12 423 38 34
\.


--
-- TOC entry 2855 (class 0 OID 65536)
-- Dependencies: 206
-- Data for Name: statement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.statement (id_statement, content, date, read, id_user) FROM stdin;
\.


--
-- TOC entry 2848 (class 0 OID 49168)
-- Dependencies: 199
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id_user, blocked, email, first_name, last_name, password, pesel, phone_number, role, username) FROM stdin;
1	f	patient1@op.pl	patient1	patient1	patient1	1	1	PATIENT	patient1
2	f	doctor1@op.pl	doctor1	doctor1	doctor1	2	2	DOCTOR	doctor1
\.


--
-- TOC entry 2850 (class 0 OID 49178)
-- Dependencies: 201
-- Data for Name: visit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visit (id_visit, visit_date, id_visit_model) FROM stdin;
2034	2018-08-07	10
2035	2018-08-14	10
\.


--
-- TOC entry 2852 (class 0 OID 49186)
-- Dependencies: 203
-- Data for Name: visit_hour; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visit_hour (id_visit_hour, hour, id_user, id_visit, status) FROM stdin;
51	09:00:00	\N	2034	\N
52	09:30:00	\N	2034	\N
53	10:00:00	\N	2034	\N
54	10:30:00	\N	2034	\N
55	09:00:00	\N	2035	\N
56	09:30:00	\N	2035	\N
57	10:00:00	\N	2035	\N
58	10:30:00	\N	2035	\N
\.


--
-- TOC entry 2854 (class 0 OID 49194)
-- Dependencies: 205
-- Data for Name: visit_model; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visit_model (id_visit_model, begin_time, day_interval, end_date, end_time, minute_interval, specialization, start_date, id_clinic, id_doctor, care_type) FROM stdin;
10	09:00:00	7	2018-08-14	11:00:00	30	Dermatologia	2018-08-07	1	2	Prywatna
\.


--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 197
-- Name: clinic_id_clinic_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clinic_id_clinic_seq', 1, true);


--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 196
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 202
-- Name: visit_hour_id_visit_hour_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visit_hour_id_visit_hour_seq', 58, true);


--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 200
-- Name: visit_id_visit_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visit_id_visit_seq', 2035, true);


--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 204
-- Name: visit_model_id_visit_model_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visit_model_id_visit_model_seq', 10, true);


--
-- TOC entry 2707 (class 2606 OID 49167)
-- Name: clinic clinic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic
    ADD CONSTRAINT clinic_pkey PRIMARY KEY (id_clinic);


--
-- TOC entry 2717 (class 2606 OID 65543)
-- Name: statement statement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statement
    ADD CONSTRAINT statement_pkey PRIMARY KEY (id_statement);


--
-- TOC entry 2709 (class 2606 OID 49175)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 2713 (class 2606 OID 49191)
-- Name: visit_hour visit_hour_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour
    ADD CONSTRAINT visit_hour_pkey PRIMARY KEY (id_visit_hour);


--
-- TOC entry 2715 (class 2606 OID 49199)
-- Name: visit_model visit_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT visit_model_pkey PRIMARY KEY (id_visit_model);


--
-- TOC entry 2711 (class 2606 OID 49183)
-- Name: visit visit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT visit_pkey PRIMARY KEY (id_visit);


--
-- TOC entry 2721 (class 2606 OID 49215)
-- Name: visit_model fk1lftc6o0uel16ci23cjppxo1y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT fk1lftc6o0uel16ci23cjppxo1y FOREIGN KEY (id_clinic) REFERENCES public.clinic(id_clinic);


--
-- TOC entry 2722 (class 2606 OID 49220)
-- Name: visit_model fk467byi2fobr0664bmuolfekny; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT fk467byi2fobr0664bmuolfekny FOREIGN KEY (id_doctor) REFERENCES public.users(id_user);


--
-- TOC entry 2723 (class 2606 OID 65544)
-- Name: statement fk54ulvi160alu1s1w250qtnm6r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statement
    ADD CONSTRAINT fk54ulvi160alu1s1w250qtnm6r FOREIGN KEY (id_user) REFERENCES public.users(id_user);


--
-- TOC entry 2720 (class 2606 OID 49210)
-- Name: visit_hour fkejlu5f8ph8ap91x0oi3vdvmbl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour
    ADD CONSTRAINT fkejlu5f8ph8ap91x0oi3vdvmbl FOREIGN KEY (id_visit) REFERENCES public.visit(id_visit);


--
-- TOC entry 2718 (class 2606 OID 49200)
-- Name: visit fkhxtw2c68b46dah8ostoo0tr1v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fkhxtw2c68b46dah8ostoo0tr1v FOREIGN KEY (id_visit_model) REFERENCES public.visit_model(id_visit_model);


--
-- TOC entry 2719 (class 2606 OID 49205)
-- Name: visit_hour fkrx1pqlcxdsg4vax7ws609o7w1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour
    ADD CONSTRAINT fkrx1pqlcxdsg4vax7ws609o7w1 FOREIGN KEY (id_user) REFERENCES public.users(id_user);


-- Completed on 2018-08-08 18:12:09

--
-- PostgreSQL database dump complete
--

