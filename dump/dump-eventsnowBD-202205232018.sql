--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6
-- Dumped by pg_dump version 13.6

-- Started on 2022-05-23 21:18:03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3072 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 24692)
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events (
    event_id integer NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(500),
    creation_date date NOT NULL,
    date_of_event timestamp without time zone NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    number_of_places integer,
    poster character varying(500) DEFAULT 'null.png'::character varying
);


ALTER TABLE public.events OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 24690)
-- Name: events_event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.events_event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_event_id_seq OWNER TO postgres;

--
-- TOC entry 3073 (class 0 OID 0)
-- Dependencies: 202
-- Name: events_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.events_event_id_seq OWNED BY public.events.event_id;


--
-- TOC entry 211 (class 1259 OID 24814)
-- Name: eventtypes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eventtypes (
    type_id integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE public.eventtypes OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24812)
-- Name: eventtypes_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.eventtypes_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.eventtypes_type_id_seq OWNER TO postgres;

--
-- TOC entry 3074 (class 0 OID 0)
-- Dependencies: 210
-- Name: eventtypes_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.eventtypes_type_id_seq OWNED BY public.eventtypes.type_id;


--
-- TOC entry 205 (class 1259 OID 24703)
-- Name: organizers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizers (
    organizer_id integer NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(500),
    email character varying(500),
    is_active integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.organizers OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 24726)
-- Name: organizers_events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organizers_events (
    organizer_id integer NOT NULL,
    event_id integer NOT NULL
);


ALTER TABLE public.organizers_events OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 24701)
-- Name: organizers_organizer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.organizers_organizer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organizers_organizer_id_seq OWNER TO postgres;

--
-- TOC entry 3075 (class 0 OID 0)
-- Dependencies: 204
-- Name: organizers_organizer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.organizers_organizer_id_seq OWNED BY public.organizers.organizer_id;


--
-- TOC entry 209 (class 1259 OID 24765)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    role_id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 24763)
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_role_id_seq OWNER TO postgres;

--
-- TOC entry 3076 (class 0 OID 0)
-- Dependencies: 208
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_role_id_seq OWNED BY public.roles.role_id;


--
-- TOC entry 212 (class 1259 OID 24820)
-- Name: types_events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.types_events (
    type_id integer,
    event_id integer
);


ALTER TABLE public.types_events OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 24823)
-- Name: types_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.types_users (
    type_id integer,
    user_id integer
);


ALTER TABLE public.types_users OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 24684)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    user_login character varying(40) NOT NULL,
    user_password character varying(400) NOT NULL,
    user_role integer DEFAULT 1 NOT NULL,
    is_active integer DEFAULT 1 NOT NULL,
    organizer_id integer
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 24723)
-- Name: users_events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_events (
    user_id integer NOT NULL,
    event_id integer NOT NULL
);


ALTER TABLE public.users_events OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 24682)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3077 (class 0 OID 0)
-- Dependencies: 200
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 2896 (class 2604 OID 24695)
-- Name: events event_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events ALTER COLUMN event_id SET DEFAULT nextval('public.events_event_id_seq'::regclass);


--
-- TOC entry 2902 (class 2604 OID 24817)
-- Name: eventtypes type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eventtypes ALTER COLUMN type_id SET DEFAULT nextval('public.eventtypes_type_id_seq'::regclass);


--
-- TOC entry 2899 (class 2604 OID 24706)
-- Name: organizers organizer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizers ALTER COLUMN organizer_id SET DEFAULT nextval('public.organizers_organizer_id_seq'::regclass);


--
-- TOC entry 2901 (class 2604 OID 24768)
-- Name: roles role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN role_id SET DEFAULT nextval('public.roles_role_id_seq'::regclass);


--
-- TOC entry 2893 (class 2604 OID 24687)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3056 (class 0 OID 24692)
-- Dependencies: 203
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.events VALUES (3, 'Night of Horrors', 'Horror movie marathon at night', '2022-05-12', '2022-05-14 23:30:00', 0, 20, 'null.png');
INSERT INTO public.events VALUES (5, 'High school graduation', 'Celebrate your graduation again', '2022-05-16', '2022-05-30 00:00:00', 0, 30, 'null.png');
INSERT INTO public.events VALUES (1, 'Concert', 'Harvest Day Concert ', '2022-05-16', '2022-03-26 00:00:00', 0, 35, 'null.png');
INSERT INTO public.events VALUES (4, 'Movie night ', 'Charity movie night ', '2022-05-14', '2022-05-17 18:31:00', 0, 8, 'mjk.png');
INSERT INTO public.events VALUES (6, 'Circus', 'premiere', '2022-05-16', '2022-05-13 23:00:00', 0, 100, 'imgtablesmeanno.jpg');
INSERT INTO public.events VALUES (7, 'The Batman', 'Charity movie night ', '2022-05-19', '2022-05-21 00:00:00', 0, 100, 'null.png');
INSERT INTO public.events VALUES (9, 'Lord of the Rings 2', 'Charity movie night ', '2022-05-20', '2022-05-20 04:00:00', 0, 100, 'null.png');
INSERT INTO public.events VALUES (8, 'Lord of the Rings', 'Charity movie night ', '2022-05-20', '2022-05-20 02:30:00', 0, 100, NULL);
INSERT INTO public.events VALUES (11, 'Lord of The Ring 3', 'Charity movie night ', '2022-05-21', '2022-05-21 06:30:00', 0, 100, '');


--
-- TOC entry 3064 (class 0 OID 24814)
-- Dependencies: 211
-- Data for Name: eventtypes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.eventtypes VALUES (1, 'movies');
INSERT INTO public.eventtypes VALUES (2, 'party');
INSERT INTO public.eventtypes VALUES (3, 'theater');
INSERT INTO public.eventtypes VALUES (4, 'concert');


--
-- TOC entry 3058 (class 0 OID 24703)
-- Dependencies: 205
-- Data for Name: organizers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.organizers VALUES (1, 'Happy Holidays', 'Your favorite holiday agency', 'happyholidays@mail.com', 1);
INSERT INTO public.organizers VALUES (2, 'Party', 'We will organize the best party for you', 'party@mail.com', 1);
INSERT INTO public.organizers VALUES (0, 'null', 'null', 'null', 1);


--
-- TOC entry 3060 (class 0 OID 24726)
-- Dependencies: 207
-- Data for Name: organizers_events; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.organizers_events VALUES (1, 4);
INSERT INTO public.organizers_events VALUES (2, 3);
INSERT INTO public.organizers_events VALUES (1, 5);
INSERT INTO public.organizers_events VALUES (1, 6);
INSERT INTO public.organizers_events VALUES (1, 7);
INSERT INTO public.organizers_events VALUES (1, 8);
INSERT INTO public.organizers_events VALUES (1, 9);
INSERT INTO public.organizers_events VALUES (1, 11);


--
-- TOC entry 3062 (class 0 OID 24765)
-- Dependencies: 209
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles VALUES (1, 'user');
INSERT INTO public.roles VALUES (2, 'organizer');
INSERT INTO public.roles VALUES (3, 'admin');


--
-- TOC entry 3065 (class 0 OID 24820)
-- Dependencies: 212
-- Data for Name: types_events; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.types_events VALUES (1, 7);
INSERT INTO public.types_events VALUES (1, 9);
INSERT INTO public.types_events VALUES (1, 8);
INSERT INTO public.types_events VALUES (1, 11);


--
-- TOC entry 3066 (class 0 OID 24823)
-- Dependencies: 213
-- Data for Name: types_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.types_users VALUES (1, 2);
INSERT INTO public.types_users VALUES (1, 9);
INSERT INTO public.types_users VALUES (2, 9);
INSERT INTO public.types_users VALUES (3, 10);
INSERT INTO public.types_users VALUES (4, 10);


--
-- TOC entry 3054 (class 0 OID 24684)
-- Dependencies: 201
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (11, 'admin', 'admin', 'admin', '$2a$10$Ncu3seosrOdEDrCVLExfM.pGggOMq.xhrIki5pBrkd/TpmqXR2CX2', 3, 1, 0);
INSERT INTO public.users VALUES (8, 'Vasya', 'Ivanov', 'vasya1234', '$2a$10$tuIMV8mufZNk4j/zf5U9LO1SLK6jfPcom5Dn5XdTlvX42TfbplQbW', 1, 1, 0);
INSERT INTO public.users VALUES (2, 'Alena', 'Veremeenko', 'alena123', '$2a$10$1Vyqlrtogh10JQe5/T1o3e.s7rUzeVKV/WCSRK5pTGU8nfYktyo.K', 1, 1, 0);
INSERT INTO public.users VALUES (10, 'Julia', 'Smirnova', 'julia123', '$2a$10$2b45MDuo/8M68SLmUTAyyeXVW74Y4lwhu2wOp1h0qmLtPrbVfAGNC', 1, 1, 0);
INSERT INTO public.users VALUES (4, 'Anton', 'Popov', 'anton1234', '$2a$10$rgcAxjGIrzKL/B/fzM5cYOuDG818MCBvK6DJ.N89hpueGpfgwPoIC', 2, 0, 1);
INSERT INTO public.users VALUES (9, 'Kate', 'Sidorova', 'kate123', '$2a$10$CBWh1aaVOhh3cVOIDHIeAeANT77OMyBZeUph3UyXyF7QLouqxllpG', 2, 1, 1);


--
-- TOC entry 3059 (class 0 OID 24723)
-- Dependencies: 206
-- Data for Name: users_events; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users_events VALUES (4, 5);
INSERT INTO public.users_events VALUES (2, 6);
INSERT INTO public.users_events VALUES (2, 5);
INSERT INTO public.users_events VALUES (2, 3);


--
-- TOC entry 3078 (class 0 OID 0)
-- Dependencies: 202
-- Name: events_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.events_event_id_seq', 11, true);


--
-- TOC entry 3079 (class 0 OID 0)
-- Dependencies: 210
-- Name: eventtypes_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.eventtypes_type_id_seq', 4, true);


--
-- TOC entry 3080 (class 0 OID 0)
-- Dependencies: 204
-- Name: organizers_organizer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.organizers_organizer_id_seq', 2, true);


--
-- TOC entry 3081 (class 0 OID 0)
-- Dependencies: 208
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_role_id_seq', 1, true);


--
-- TOC entry 3082 (class 0 OID 0)
-- Dependencies: 200
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 14, true);


--
-- TOC entry 2906 (class 2606 OID 24700)
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);


--
-- TOC entry 2912 (class 2606 OID 24819)
-- Name: eventtypes eventtypes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eventtypes
    ADD CONSTRAINT eventtypes_pkey PRIMARY KEY (type_id);


--
-- TOC entry 2908 (class 2606 OID 24711)
-- Name: organizers organizers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizers
    ADD CONSTRAINT organizers_pkey PRIMARY KEY (organizer_id);


--
-- TOC entry 2910 (class 2606 OID 24770)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- TOC entry 2904 (class 2606 OID 24689)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2917 (class 2606 OID 24729)
-- Name: organizers_events organizers_events_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizers_events
    ADD CONSTRAINT organizers_events_fk FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- TOC entry 2918 (class 2606 OID 24734)
-- Name: organizers_events organizers_events_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.organizers_events
    ADD CONSTRAINT organizers_events_fk_1 FOREIGN KEY (organizer_id) REFERENCES public.organizers(organizer_id);


--
-- TOC entry 2919 (class 2606 OID 24827)
-- Name: types_events types_events_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types_events
    ADD CONSTRAINT types_events_fk FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- TOC entry 2920 (class 2606 OID 24832)
-- Name: types_events types_events_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types_events
    ADD CONSTRAINT types_events_fk_1 FOREIGN KEY (type_id) REFERENCES public.eventtypes(type_id);


--
-- TOC entry 2921 (class 2606 OID 24838)
-- Name: types_users types_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types_users
    ADD CONSTRAINT types_users_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2922 (class 2606 OID 24843)
-- Name: types_users types_users_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types_users
    ADD CONSTRAINT types_users_fk_1 FOREIGN KEY (type_id) REFERENCES public.eventtypes(type_id);


--
-- TOC entry 2915 (class 2606 OID 24739)
-- Name: users_events users_events_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_events
    ADD CONSTRAINT users_events_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2916 (class 2606 OID 24744)
-- Name: users_events users_events_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_events
    ADD CONSTRAINT users_events_fk_1 FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- TOC entry 2913 (class 2606 OID 24776)
-- Name: users users_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_fk FOREIGN KEY (user_role) REFERENCES public.roles(role_id);


--
-- TOC entry 2914 (class 2606 OID 24791)
-- Name: users users_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_fk1 FOREIGN KEY (organizer_id) REFERENCES public.organizers(organizer_id);


-- Completed on 2022-05-23 21:18:09

--
-- PostgreSQL database dump complete
--

