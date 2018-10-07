--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

-- Started on 2018-10-07 12:01:42

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
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 202 (class 1259 OID 132007)
-- Name: clinic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clinic (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    medical_center character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    phone_number character varying(255)
);


ALTER TABLE public.clinic OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 132015)
-- Name: clinic_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clinic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clinic_id_seq OWNER TO postgres;

--
-- TOC entry 2868 (class 0 OID 0)
-- Dependencies: 203
-- Name: clinic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clinic_id_seq OWNED BY public.clinic.id;


--
-- TOC entry 197 (class 1259 OID 131978)
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 131973)
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 132029)
-- Name: doctor_clinic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor_clinic (
    id_doctor bigint NOT NULL,
    id_clinic bigint NOT NULL
);


ALTER TABLE public.doctor_clinic OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 131984)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    blocked boolean,
    pesel character varying(255),
    phone_number character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 131996)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 199
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 200 (class 1259 OID 131999)
-- Name: visit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit (
    id bigint NOT NULL,
    start timestamp without time zone NOT NULL,
    end_ timestamp without time zone NOT NULL,
    status character varying(255) NOT NULL,
    id_user bigint,
    id_visit_model bigint NOT NULL
);


ALTER TABLE public.visit OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 132004)
-- Name: visit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visit_id_seq OWNER TO postgres;

--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 201
-- Name: visit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_id_seq OWNED BY public.visit.id;


--
-- TOC entry 204 (class 1259 OID 132018)
-- Name: visit_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit_model (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    start timestamp without time zone NOT NULL,
    end_ timestamp without time zone NOT NULL,
    end_date date NOT NULL,
    day_interval integer NOT NULL,
    minute_interval integer NOT NULL,
    specialization character varying(255) NOT NULL,
    care_type character varying(255) NOT NULL,
    price numeric(19,2) NOT NULL,
    id_clinic bigint NOT NULL,
    id_doctor bigint NOT NULL
);


ALTER TABLE public.visit_model OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 132026)
-- Name: visit_model_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visit_model_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visit_model_id_seq OWNER TO postgres;

--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 205
-- Name: visit_model_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_model_id_seq OWNED BY public.visit_model.id;


--
-- TOC entry 2706 (class 2604 OID 132017)
-- Name: clinic id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic ALTER COLUMN id SET DEFAULT nextval('public.clinic_id_seq'::regclass);


--
-- TOC entry 2704 (class 2604 OID 131998)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2705 (class 2604 OID 132006)
-- Name: visit id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit ALTER COLUMN id SET DEFAULT nextval('public.visit_id_seq'::regclass);


--
-- TOC entry 2707 (class 2604 OID 132028)
-- Name: visit_model id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model ALTER COLUMN id SET DEFAULT nextval('public.visit_model_id_seq'::regclass);


--
-- TOC entry 2855 (class 0 OID 132007)
-- Dependencies: 202
-- Data for Name: clinic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clinic (id, name, medical_center, address, city, phone_number) FROM stdin;
1	clinic1	mediacalCenter1	address1	city1	111222333
2	clinic2	mediacalCenter1	address2	city2	333222111
3	clinic3	mediacalCenter1	address3	city1	222333111
4	clinic4	mediacalCenter2	address4	city2	222111333
5	clinic5	mediacalCenter2	address5	city2	444333222
6	clinic6	mediacalCenter2	address6	city1	222333444
\.


--
-- TOC entry 2850 (class 0 OID 131978)
-- Dependencies: 197
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1	jstezalski	classpath:db/changelog/schema/create-user-schema.xml	2018-10-07 11:57:28.709744	1	EXECUTED	8:f1f9a348bbc3ce1ca2d8e6ef1f875807	createTable tableName=USERS; addAutoIncrement columnName=ID, tableName=USERS		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/schema/create-visit-schema.xml	2018-10-07 11:57:28.822371	2	EXECUTED	8:357635d3db18f052600b8cb1f20c0129	createTable tableName=VISIT; addAutoIncrement columnName=ID, tableName=VISIT		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/schema/create-clinic-schema.xml	2018-10-07 11:57:29.049287	3	EXECUTED	8:f256f763aef5458d104cbfbea65b80e2	createTable tableName=CLINIC; addAutoIncrement columnName=ID, tableName=CLINIC		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/schema/create-visitModel-schema.xml	2018-10-07 11:57:29.191768	4	EXECUTED	8:7b5cf5ac9394ad65370fa40b6cd0aa2e	createTable tableName=VISIT_MODEL; addAutoIncrement columnName=ID, tableName=VISIT_MODEL		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/schema/create-doctorClinic-schema.xml	2018-10-07 11:57:29.256742	5	EXECUTED	8:89dc9bee18a8120639ac3d35d793f29c	createTable tableName=DOCTOR_CLINIC		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/data/insert-user-data.xml	2018-10-07 11:57:29.278857	6	EXECUTED	8:da577d7bcb9262a9a8d28d5849e635de	insert tableName=users; insert tableName=users; insert tableName=users; insert tableName=users; insert tableName=users; insert tableName=users		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/data/insert-clinic-data.xml	2018-10-07 11:57:29.295735	7	EXECUTED	8:f9d6c31797118709b13d3d5e124ce1c8	insert tableName=CLINIC; insert tableName=CLINIC; insert tableName=CLINIC; insert tableName=CLINIC; insert tableName=CLINIC; insert tableName=CLINIC		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/data/insert-visit-data.xml	2018-10-07 11:57:29.381466	8	EXECUTED	8:9854bc8d07b10da97d980aba02de8d79	sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql; sql		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/data/insert-visitModel-data.xml	2018-10-07 11:57:29.396742	9	EXECUTED	8:3a8e11f56a7214d69191b5953cc5bdc3	insert tableName=VISIT_MODEL; insert tableName=VISIT_MODEL; insert tableName=VISIT_MODEL; insert tableName=VISIT_MODEL; insert tableName=VISIT_MODEL; insert tableName=VISIT_MODEL		\N	3.6.2	default	\N	8906248140
1	jstezalski	classpath:db/changelog/data/insert-doctorClinic-data.xml	2018-10-07 11:57:29.409892	10	EXECUTED	8:6afb27c8f042e57bddefbc4433fa7c35	insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR_CLINIC; insert tableName=DOCTOR...		\N	3.6.2	default	\N	8906248140
\.


--
-- TOC entry 2849 (class 0 OID 131973)
-- Dependencies: 196
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- TOC entry 2859 (class 0 OID 132029)
-- Dependencies: 206
-- Data for Name: doctor_clinic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor_clinic (id_doctor, id_clinic) FROM stdin;
1	1
1	3
1	5
2	2
2	4
2	6
3	3
3	1
3	6
\.


--
-- TOC entry 2851 (class 0 OID 131984)
-- Dependencies: 198
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, first_name, last_name, role, email, blocked, pesel, phone_number) FROM stdin;
1	doctor1	$2a$10$ddAt87QuE/cqw74BAdjWJOHPbAomcsoMhhliMaiwqD5r0hqvQIyMC	doctor1	doctor1	DOCTOR	doctor1@email.com	f	11111111111	111111111
2	doctor2	$2a$10$kxdpA4jh9mpPGeRpYhz/uePwpty4tmfGDlrej76NlbncoGS2Yj9lq	doctor2	doctor2	DOCTOR	doctor2@email.com	f	22222222222	222222222
3	doctor3	$2a$10$quuBY2C2wt41bMX7KP968OgrSpxAjWkzcRNaMF6E.b6gbimUBO3ay	doctor3	doctor3	DOCTOR	doctor3@email.com	f	33333333333	333333333
4	user1	$2a$10$28cqPSFV1V8WQpVZxbxwFuWlFDjvrddKQHbbUY9kLRCQkI1/aD9Oe	user1	user1	USER	user1@email.com	f	44444444444	444444444
5	user2	$2a$10$z.gvGyKRd75KEgiCsTpy1eaakaLun/apsh8MNdiRQtMOwcJDEKvAa	user2	user2	USER	user2@email.com	f	55555555555	555555555
6	user3	$2a$10$0xMl2A6ot2OgxiefuiCkiux3IjwBV6zEp9Rcp5G8Cp6nuvRaOHwbO	user3	user3	USER	user3@email.com	f	66666666666	666666666
\.


--
-- TOC entry 2853 (class 0 OID 131999)
-- Dependencies: 200
-- Data for Name: visit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visit (id, start, end_, status, id_user, id_visit_model) FROM stdin;
1	2018-10-02 08:00:00	2018-10-02 09:00:00	08:00	\N	1
2	2018-10-09 08:00:00	2018-10-09 09:00:00	08:00	\N	1
3	2018-10-16 08:00:00	2018-10-16 09:00:00	08:00	\N	1
4	2018-10-23 08:00:00	2018-10-23 09:00:00	08:00	\N	1
5	2018-10-30 08:00:00	2018-10-30 09:00:00	08:00	\N	1
6	2018-10-03 10:15:00	2018-10-03 11:15:00	10:15	\N	2
7	2018-10-10 10:15:00	2018-10-10 11:15:00	10:15	\N	2
8	2018-10-17 10:15:00	2018-10-17 11:15:00	10:15	\N	2
9	2018-10-24 10:15:00	2018-10-24 11:15:00	10:15	\N	2
10	2018-10-31 10:15:00	2018-10-31 11:15:00	10:15	\N	2
11	2018-10-02 15:00:00	2018-10-02 16:00:00	15:00	\N	3
12	2018-10-09 15:00:00	2018-10-09 16:00:00	15:00	\N	3
13	2018-10-16 15:00:00	2018-10-16 16:00:00	15:00	\N	3
14	2018-10-23 15:00:00	2018-10-23 16:00:00	15:00	\N	3
15	2018-10-30 15:00:00	2018-10-30 16:00:00	15:00	\N	3
16	2018-10-05 12:45:00	2018-10-05 13:45:00	12:45	\N	4
17	2018-10-12 12:45:00	2018-10-12 13:45:00	12:45	\N	4
18	2018-10-19 12:45:00	2018-10-19 13:45:00	12:45	\N	4
19	2018-10-26 12:45:00	2018-10-26 13:45:00	12:45	\N	4
20	2018-10-05 09:30:00	2018-10-05 10:30:00	09:30	\N	5
21	2018-10-12 09:30:00	2018-10-12 10:30:00	09:30	\N	5
22	2018-10-19 09:30:00	2018-10-19 10:30:00	09:30	\N	5
23	2018-10-26 09:30:00	2018-10-26 10:30:00	09:30	\N	5
24	2018-10-04 15:30:00	2018-10-04 16:30:00	15:30	\N	6
25	2018-10-11 15:30:00	2018-10-11 16:30:00	15:30	\N	6
26	2018-10-18 15:30:00	2018-10-18 16:30:00	15:30	\N	6
27	2018-10-25 15:30:00	2018-10-25 16:30:00	15:30	\N	6
\.


--
-- TOC entry 2857 (class 0 OID 132018)
-- Dependencies: 204
-- Data for Name: visit_model; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visit_model (id, name, start, end_, end_date, day_interval, minute_interval, specialization, care_type, price, id_clinic, id_doctor) FROM stdin;
1	wizyta1	2018-10-02 08:00:00	2018-10-02 09:00:00	2018-10-31	7	60	Kardiolog	Publiczna	0.00	1	1
2	wizyta2	2018-10-03 10:15:00	2018-10-03 11:15:00	2018-10-31	7	60	Stomatolog	Prywatna	50.00	2	2
3	wizyta3	2018-10-02 15:00:00	2018-10-02 16:00:00	2018-10-31	7	60	Urolog	Publiczna	0.00	3	3
4	wizyta4	2018-10-05 12:45:00	2018-10-05 13:45:00	2018-10-31	7	60	Dermatolog	Publiczna	0.00	4	1
5	wizyta5	2018-10-05 09:30:00	2018-10-05 10:30:00	2018-10-31	7	60	Dermatolog	Prywatna	70.00	3	1
6	wizyta6	2018-10-04 15:30:00	2018-10-04 16:30:00	2018-10-31	7	60	Stomatolog	Prywatna	90.00	1	3
\.


--
-- TOC entry 2872 (class 0 OID 0)
-- Dependencies: 203
-- Name: clinic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clinic_id_seq', 6, true);


--
-- TOC entry 2873 (class 0 OID 0)
-- Dependencies: 199
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- TOC entry 2874 (class 0 OID 0)
-- Dependencies: 201
-- Name: visit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visit_id_seq', 27, true);


--
-- TOC entry 2875 (class 0 OID 0)
-- Dependencies: 205
-- Name: visit_model_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visit_model_id_seq', 6, true);


--
-- TOC entry 2719 (class 2606 OID 132014)
-- Name: clinic clinic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic
    ADD CONSTRAINT clinic_pkey PRIMARY KEY (id);


--
-- TOC entry 2709 (class 2606 OID 131977)
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- TOC entry 2711 (class 2606 OID 131995)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 2713 (class 2606 OID 131991)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2715 (class 2606 OID 131993)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- TOC entry 2721 (class 2606 OID 132025)
-- Name: visit_model visit_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT visit_model_pkey PRIMARY KEY (id);


--
-- TOC entry 2717 (class 2606 OID 132003)
-- Name: visit visit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT visit_pkey PRIMARY KEY (id);


--
-- TOC entry 2724 (class 2606 OID 132070)
-- Name: visit_model fk1lftc6o0uel16ci23cjppxo1y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT fk1lftc6o0uel16ci23cjppxo1y FOREIGN KEY (id_clinic) REFERENCES public.clinic(id);


--
-- TOC entry 2726 (class 2606 OID 132040)
-- Name: doctor_clinic fk2yyx0x72qlp1wi6fxmu348qg6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_clinic
    ADD CONSTRAINT fk2yyx0x72qlp1wi6fxmu348qg6 FOREIGN KEY (id_doctor) REFERENCES public.users(id);


--
-- TOC entry 2725 (class 2606 OID 132075)
-- Name: visit_model fk467byi2fobr0664bmuolfekny; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT fk467byi2fobr0664bmuolfekny FOREIGN KEY (id_doctor) REFERENCES public.users(id);


--
-- TOC entry 2722 (class 2606 OID 132050)
-- Name: visit fk4je45mt3el9uqgv4dxca5ttpt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fk4je45mt3el9uqgv4dxca5ttpt FOREIGN KEY (id_user) REFERENCES public.users(id);


--
-- TOC entry 2723 (class 2606 OID 132055)
-- Name: visit fkhxtw2c68b46dah8ostoo0tr1v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fkhxtw2c68b46dah8ostoo0tr1v FOREIGN KEY (id_visit_model) REFERENCES public.visit_model(id);


--
-- TOC entry 2727 (class 2606 OID 132045)
-- Name: doctor_clinic fko83tac4xqtaoh55k4oajkh7ky; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor_clinic
    ADD CONSTRAINT fko83tac4xqtaoh55k4oajkh7ky FOREIGN KEY (id_clinic) REFERENCES public.clinic(id);


-- Completed on 2018-10-07 12:01:44

--
-- PostgreSQL database dump complete
--

