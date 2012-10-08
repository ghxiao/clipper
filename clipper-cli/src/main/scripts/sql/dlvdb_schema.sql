
SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

drop table if exists concept_assertion cascade;
drop table if exists data_role_assertion cascade;
drop table if exists object_role_assertion cascade;
drop table if exists individual_name cascade;
drop table if exists predicate_name cascade;

CREATE TABLE concept_assertion (
    concept integer NOT NULL,
    individual integer NOT NULL
);


ALTER TABLE public.concept_assertion OWNER TO xiao;

CREATE TABLE data_role_assertion (
    data_role integer NOT NULL,
    individual integer NOT NULL,
    value text NOT NULL,
    datatype character varying(255) NOT NULL,
    language character varying(10) NOT NULL
);


ALTER TABLE public.data_role_assertion OWNER TO xiao;

CREATE TABLE individual_name (
    id SERIAL,
    name text NOT NULL UNIQUE
);

CREATE TABLE predicate_name (
    id SERIAL,
    name text NOT NULL UNIQUE
);


ALTER TABLE public.individual_name OWNER TO xiao;

--
-- Name: object_role_assertion; Type: TABLE; Schema: public; Owner: xiao; Tablespace: 
--

CREATE TABLE object_role_assertion (
    object_role integer NOT NULL,
    a integer NOT NULL,
    b integer NOT NULL
);


ALTER TABLE public.object_role_assertion OWNER TO xiao;

ALTER TABLE public.result OWNER TO xiao;

ALTER TABLE ONLY concept_assertion
    ADD CONSTRAINT concept_assertion_pkey PRIMARY KEY (concept, individual);

--
-- Name: data_role_assertion_pkey; Type: CONSTRAINT; Schema: public; Owner: xiao; Tablespace: 
--

ALTER TABLE ONLY data_role_assertion
    ADD CONSTRAINT data_role_assertion_pkey PRIMARY KEY (data_role, individual, value, datatype, language);

--
-- Name: individual_name_pkey; Type: CONSTRAINT; Schema: public; Owner: xiao; Tablespace: 
--

ALTER TABLE ONLY individual_name
    ADD CONSTRAINT individual_name_pkey PRIMARY KEY (id);

ALTER TABLE ONLY predicate_name
    ADD CONSTRAINT predicate_name_pkey PRIMARY KEY (id);


--
-- Name: object_role_assertion_pkey; Type: CONSTRAINT; Schema: public; Owner: xiao; Tablespace: 
--

ALTER TABLE ONLY object_role_assertion
    ADD CONSTRAINT object_role_assertion_pkey PRIMARY KEY (object_role, a, b);

--
-- Name: concept_assertion_concept_idx; Type: INDEX; Schema: public; Owner: xiao; Tablespace: 
--

CREATE INDEX concept_assertion_concept_idx ON concept_assertion USING btree (concept);


--
-- Name: data_role_assertion_individual_role_idx; Type: INDEX; Schema: public; Owner: xiao; Tablespace: 
--

CREATE INDEX data_role_assertion_individual_role_idx ON data_role_assertion USING btree (individual, data_role);


--
-- Name: data_role_assertion_role_idx; Type: INDEX; Schema: public; Owner: xiao; Tablespace: 
--

CREATE INDEX data_role_assertion_role_idx ON data_role_assertion USING btree (data_role);

--
-- Name: object_role_assertion_a_role_idx; Type: INDEX; Schema: public; Owner: xiao; Tablespace: 
--

CREATE INDEX object_role_assertion_a_role_idx ON object_role_assertion USING btree (a, object_role);


--
-- Name: object_role_assertion_b_role_idx; Type: INDEX; Schema: public; Owner: xiao; Tablespace: 
--

CREATE INDEX object_role_assertion_b_role_idx ON object_role_assertion USING btree (b, object_role);


--
-- Name: object_role_assertion_role_idx; Type: INDEX; Schema: public; Owner: xiao; Tablespace: 
--

CREATE INDEX object_role_assertion_role_idx ON object_role_assertion USING btree (object_role);


--
-- Name: concept_assertion_concept_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

-- ALTER TABLE ONLY concept_assertion
--    ADD CONSTRAINT concept_assertion_concept_fkey FOREIGN KEY (concept) REFERENCES tbox_name(id);

ALTER TABLE ONLY concept_assertion
    ADD CONSTRAINT concept_assertion_concept_fkey FOREIGN KEY (concept) REFERENCES predicate_name(id);

--
-- Name: concept_assertion_individual_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

ALTER TABLE ONLY concept_assertion
    ADD CONSTRAINT concept_assertion_individual_fkey FOREIGN KEY (individual) REFERENCES individual_name(id);


--
-- Name: data_role_assertion_data_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

ALTER TABLE ONLY data_role_assertion
    ADD CONSTRAINT data_role_assertion_data_role_fkey FOREIGN KEY (data_role) REFERENCES predicate_name(id);


--
-- Name: data_role_assertion_individual_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

ALTER TABLE ONLY data_role_assertion
    ADD CONSTRAINT data_role_assertion_individual_fkey FOREIGN KEY (individual) REFERENCES individual_name(id);


--
-- Name: object_role_assertion_a_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

ALTER TABLE ONLY object_role_assertion
    ADD CONSTRAINT object_role_assertion_a_fkey FOREIGN KEY (a) REFERENCES individual_name(id);


--
-- Name: object_role_assertion_b_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

ALTER TABLE ONLY object_role_assertion
    ADD CONSTRAINT object_role_assertion_b_fkey FOREIGN KEY (b) REFERENCES individual_name(id);


--
-- Name: object_role_assertion_object_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: xiao
--

ALTER TABLE ONLY object_role_assertion
    ADD CONSTRAINT object_role_assertion_object_role_fkey FOREIGN KEY (object_role) REFERENCES predicate_name(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
--REVOKE ALL ON SCHEMA public FROM postgres;
--GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


