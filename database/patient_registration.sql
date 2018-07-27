--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

-- Started on 2018-07-27 18:41:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
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
-- TOC entry 2843 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 32797)
-- Name: clinic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clinic (
    id_clinic bigint NOT NULL,
    address character varying(255) NOT NULL,
    medical_center character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone_number character varying(255)
);


ALTER TABLE public.clinic OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 32795)
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
-- TOC entry 2844 (class 0 OID 0)
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
-- TOC entry 199 (class 1259 OID 32806)
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
-- TOC entry 201 (class 1259 OID 32816)
-- Name: visit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit (
    id_visit bigint NOT NULL,
    visit_date timestamp without time zone NOT NULL,
    id_visit_model bigint
);


ALTER TABLE public.visit OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 32824)
-- Name: visit_hour; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit_hour (
    id_visit_hour bigint NOT NULL,
    hour time without time zone NOT NULL,
    id_user bigint,
    id_visit bigint
);


ALTER TABLE public.visit_hour OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 32822)
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
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 202
-- Name: visit_hour_id_visit_hour_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_hour_id_visit_hour_seq OWNED BY public.visit_hour.id_visit_hour;


--
-- TOC entry 200 (class 1259 OID 32814)
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
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 200
-- Name: visit_id_visit_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_id_visit_seq OWNED BY public.visit.id_visit;


--
-- TOC entry 205 (class 1259 OID 32832)
-- Name: visit_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit_model (
    id_visit_model bigint NOT NULL,
    begin_time timestamp without time zone NOT NULL,
    day_interval integer NOT NULL,
    end_date timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL,
    minute_interval integer NOT NULL,
    start_date timestamp without time zone NOT NULL,
    id_clinic bigint,
    id_user bigint
);


ALTER TABLE public.visit_model OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 32830)
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
-- TOC entry 2847 (class 0 OID 0)
-- Dependencies: 204
-- Name: visit_model_id_visit_model_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_model_id_visit_model_seq OWNED BY public.visit_model.id_visit_model;


--
-- TOC entry 2696 (class 2604 OID 32800)
-- Name: clinic id_clinic; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic ALTER COLUMN id_clinic SET DEFAULT nextval('public.clinic_id_clinic_seq'::regclass);


--
-- TOC entry 2697 (class 2604 OID 32819)
-- Name: visit id_visit; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit ALTER COLUMN id_visit SET DEFAULT nextval('public.visit_id_visit_seq'::regclass);


--
-- TOC entry 2698 (class 2604 OID 32827)
-- Name: visit_hour id_visit_hour; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour ALTER COLUMN id_visit_hour SET DEFAULT nextval('public.visit_hour_id_visit_hour_seq'::regclass);


--
-- TOC entry 2699 (class 2604 OID 32835)
-- Name: visit_model id_visit_model; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model ALTER COLUMN id_visit_model SET DEFAULT nextval('public.visit_model_id_visit_model_seq'::regclass);


--
-- TOC entry 2701 (class 2606 OID 32805)
-- Name: clinic clinic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic
    ADD CONSTRAINT clinic_pkey PRIMARY KEY (id_clinic);


--
-- TOC entry 2703 (class 2606 OID 32813)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 2707 (class 2606 OID 32829)
-- Name: visit_hour visit_hour_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour
    ADD CONSTRAINT visit_hour_pkey PRIMARY KEY (id_visit_hour);


--
-- TOC entry 2709 (class 2606 OID 32837)
-- Name: visit_model visit_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT visit_model_pkey PRIMARY KEY (id_visit_model);


--
-- TOC entry 2705 (class 2606 OID 32821)
-- Name: visit visit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT visit_pkey PRIMARY KEY (id_visit);


--
-- TOC entry 2713 (class 2606 OID 32853)
-- Name: visit_model fk1lftc6o0uel16ci23cjppxo1y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT fk1lftc6o0uel16ci23cjppxo1y FOREIGN KEY (id_clinic) REFERENCES public.clinic(id_clinic);


--
-- TOC entry 2712 (class 2606 OID 32848)
-- Name: visit_hour fkejlu5f8ph8ap91x0oi3vdvmbl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour
    ADD CONSTRAINT fkejlu5f8ph8ap91x0oi3vdvmbl FOREIGN KEY (id_visit) REFERENCES public.visit(id_visit);


--
-- TOC entry 2710 (class 2606 OID 32838)
-- Name: visit fkhxtw2c68b46dah8ostoo0tr1v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fkhxtw2c68b46dah8ostoo0tr1v FOREIGN KEY (id_visit_model) REFERENCES public.visit_model(id_visit_model);


--
-- TOC entry 2714 (class 2606 OID 32858)
-- Name: visit_model fkko2n334hec1gmami2uqgs0kmj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_model
    ADD CONSTRAINT fkko2n334hec1gmami2uqgs0kmj FOREIGN KEY (id_user) REFERENCES public.users(id_user);


--
-- TOC entry 2711 (class 2606 OID 32843)
-- Name: visit_hour fkrx1pqlcxdsg4vax7ws609o7w1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit_hour
    ADD CONSTRAINT fkrx1pqlcxdsg4vax7ws609o7w1 FOREIGN KEY (id_user) REFERENCES public.users(id_user);


-- Completed on 2018-07-27 18:41:41

--
-- PostgreSQL database dump complete
--

