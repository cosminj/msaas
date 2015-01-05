--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.screen DROP CONSTRAINT screen_operator_id_fkey;
ALTER TABLE ONLY public.screen_cameras DROP CONSTRAINT screen_cameras_screen_id_fkey;
ALTER TABLE ONLY public.screen_cameras DROP CONSTRAINT screen_cameras_camera_id_fkey;
ALTER TABLE ONLY public.event DROP CONSTRAINT event_screen_id_fkey;
ALTER TABLE ONLY public.event DROP CONSTRAINT event_camera_id_fkey;
ALTER TABLE ONLY public.camera DROP CONSTRAINT camera_customer_id_fkey;
DROP INDEX public.unique_observer_name;
DROP INDEX public.unique_name;
DROP INDEX public.screen_id_camera_id_index;
DROP INDEX public.customer_id_name_index;
ALTER TABLE ONLY public.screen DROP CONSTRAINT screen_pkey;
ALTER TABLE ONLY public.screen_cameras DROP CONSTRAINT screen_cameras_pkey;
ALTER TABLE ONLY public.observer DROP CONSTRAINT operator_pkey;
ALTER TABLE ONLY public.event DROP CONSTRAINT event_pkey;
ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
ALTER TABLE ONLY public.camera DROP CONSTRAINT camera_pkey;
ALTER TABLE public.screen_cameras ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.screen ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.observer ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.event ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.customer ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.camera ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.screen_id_seq;
DROP SEQUENCE public.screen_cameras_id_seq;
DROP TABLE public.screen_cameras;
DROP TABLE public.screen;
DROP SEQUENCE public.operator_id_seq;
DROP TABLE public.observer;
DROP SEQUENCE public.event_id_seq;
DROP TABLE public.event;
DROP SEQUENCE public.customer_id_seq;
DROP TABLE public.customer;
DROP SEQUENCE public.camera_id_seq;
DROP TABLE public.camera;
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: camera; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE camera (
	id integer NOT NULL,
	name character varying NOT NULL,
	customer_id integer NOT NULL,
	state character varying NOT NULL,
	next_viewing_at timestamp without time zone,
	tags text NOT NULL,
	startup_delay integer DEFAULT 5 NOT NULL,
	url text NOT NULL
);


--
-- Name: camera_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE camera_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: camera_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE camera_id_seq OWNED BY camera.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer (
	id integer NOT NULL,
	name character varying NOT NULL,
	password text
);


--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customer_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customer_id_seq OWNED BY customer.id;


--
-- Name: event; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE event (
	id integer NOT NULL,
	camera_id integer NOT NULL,
	screen_id integer NOT NULL,
	type text NOT NULL
);


--
-- Name: event_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE event_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE event_id_seq OWNED BY event.id;


--
-- Name: observer; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE observer (
	id integer NOT NULL,
	name character varying NOT NULL,
	state text NOT NULL,
	password text NOT NULL
);


--
-- Name: operator_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE operator_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: operator_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE operator_id_seq OWNED BY observer.id;


--
-- Name: screen; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE screen (
	id integer NOT NULL,
	scheduled_at timestamp without time zone NOT NULL,
	viewed_at timestamp without time zone,
	observer_id integer NOT NULL
);


--
-- Name: screen_cameras; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE screen_cameras (
	id integer NOT NULL,
	screen_id integer NOT NULL,
	camera_id integer NOT NULL,
	stream_archive_info text NOT NULL
);


--
-- Name: screen_cameras_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE screen_cameras_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: screen_cameras_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE screen_cameras_id_seq OWNED BY screen_cameras.id;


--
-- Name: screen_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE screen_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: screen_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE screen_id_seq OWNED BY screen.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY camera ALTER COLUMN id SET DEFAULT nextval('camera_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY event ALTER COLUMN id SET DEFAULT nextval('event_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY observer ALTER COLUMN id SET DEFAULT nextval('operator_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY screen ALTER COLUMN id SET DEFAULT nextval('screen_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY screen_cameras ALTER COLUMN id SET DEFAULT nextval('screen_cameras_id_seq'::regclass);

--
-- Name: camera_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('camera_id_seq', 1, false);


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customer_id_seq', 5, true);


--
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: -
--

--
-- Name: event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('event_id_seq', 1, false);


--
-- Data for Name: observer; Type: TABLE DATA; Schema: public; Owner: -
--


--
-- Name: operator_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('operator_id_seq', 1, false);



--
-- Name: screen_cameras_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('screen_cameras_id_seq', 1, false);


--
-- Name: screen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('screen_id_seq', 1, false);


--
-- Name: camera_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY camera
ADD CONSTRAINT camera_pkey PRIMARY KEY (id);


--
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer
ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: event_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY event
ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- Name: operator_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY observer
ADD CONSTRAINT operator_pkey PRIMARY KEY (id);


--
-- Name: screen_cameras_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY screen_cameras
ADD CONSTRAINT screen_cameras_pkey PRIMARY KEY (id);


--
-- Name: screen_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY screen
ADD CONSTRAINT screen_pkey PRIMARY KEY (id);


--
-- Name: customer_id_name_index; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX customer_id_name_index ON camera USING btree (customer_id, name);


--
-- Name: screen_id_camera_id_index; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX screen_id_camera_id_index ON screen_cameras USING btree (screen_id, camera_id);


--
-- Name: unique_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX unique_name ON customer USING btree (name);


--
-- Name: unique_observer_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX unique_observer_name ON observer USING btree (name);


--
-- Name: camera_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY camera
ADD CONSTRAINT camera_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- Name: event_camera_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY event
ADD CONSTRAINT event_camera_id_fkey FOREIGN KEY (camera_id) REFERENCES camera(id);


--
-- Name: event_screen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY event
ADD CONSTRAINT event_screen_id_fkey FOREIGN KEY (screen_id) REFERENCES screen(id);


--
-- Name: screen_cameras_camera_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY screen_cameras
ADD CONSTRAINT screen_cameras_camera_id_fkey FOREIGN KEY (camera_id) REFERENCES camera(id);


--
-- Name: screen_cameras_screen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY screen_cameras
ADD CONSTRAINT screen_cameras_screen_id_fkey FOREIGN KEY (screen_id) REFERENCES screen(id);


--
-- Name: screen_operator_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY screen
ADD CONSTRAINT screen_operator_id_fkey FOREIGN KEY (observer_id) REFERENCES observer(id);


--
-- PostgreSQL database dump complete
--



