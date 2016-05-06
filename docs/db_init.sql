-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2016-05-06 04:08:59.144

-- tables
-- Table: department
CREATE TABLE department (
  id serial  NOT NULL,
  name text  NOT NULL,
  description text  NOT NULL,
  CONSTRAINT department_pk PRIMARY KEY (id)
);

-- Table: employee
CREATE TABLE employee (
  id serial  NOT NULL,
  department_id int  NOT NULL,
  full_name text  NOT NULL,
  phone text  NOT NULL,
  email text  NOT NULL,
  vip boolean  NOT NULL DEFAULT false,
  CONSTRAINT employee_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
  id serial  NOT NULL,
  name text  NOT NULL,
  description text  NOT NULL,
  comment text  NULL,
  CONSTRAINT role_name_uk UNIQUE (name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
  CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: ticket
CREATE TABLE ticket (
  id serial  NOT NULL,
  priority int  NOT NULL DEFAULT 5,
  subject text  NOT NULL,
  owner_id int  NOT NULL,
  assigned_to_id int  NULL,
  typical_id int  NULL,
  created timestamp  NOT NULL,
  solved timestamp  NOT NULL,
  status text  NOT NULL,
  status_reason text  NULL,
  comment text  NULL,
  CONSTRAINT ticket_pk PRIMARY KEY (id)
);

-- Table: ticket_message
CREATE TABLE ticket_message (
  id serial  NOT NULL,
  body text  NOT NULL,
  created timestamp  NOT NULL,
  author_id int  NOT NULL,
  ticket_id int  NOT NULL,
  CONSTRAINT ticket_message_pk PRIMARY KEY (id)
);

-- Table: typical_story
CREATE TABLE typical_story (
  id serial  NOT NULL,
  instructions text  NOT NULL,
  keys text  NOT NULL,
  CONSTRAINT typical_story_pk PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE users (
  id serial  NOT NULL,
  employee_id int  NULL,
  login text  NOT NULL,
  password text  NOT NULL,
  role_id int  NOT NULL,
  CONSTRAINT user_login_uk UNIQUE (login) NOT DEFERRABLE  INITIALLY IMMEDIATE,
  CONSTRAINT users_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: employee_department (table: employee)
ALTER TABLE employee ADD CONSTRAINT employee_department
FOREIGN KEY (department_id)
REFERENCES department (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

-- Reference: ticket_message_ticket (table: ticket_message)
ALTER TABLE ticket_message ADD CONSTRAINT ticket_message_ticket
FOREIGN KEY (ticket_id)
REFERENCES ticket (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

-- Reference: ticket_typical (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_typical
FOREIGN KEY (typical_id)
REFERENCES typical_story (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

-- Reference: ticket_user_assigned (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_user_assigned
FOREIGN KEY (assigned_to_id)
REFERENCES users (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

-- Reference: ticket_user_owner (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_user_owner
FOREIGN KEY (owner_id)
REFERENCES users (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

-- Reference: user_employee (table: users)
ALTER TABLE users ADD CONSTRAINT user_employee
FOREIGN KEY (employee_id)
REFERENCES employee (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

-- Reference: user_role (table: users)
ALTER TABLE users ADD CONSTRAINT user_role
FOREIGN KEY (role_id)
REFERENCES role (id)
NOT DEFERRABLE
INITIALLY IMMEDIATE
;

DELETE from role;
DELETE from users;

-- End of file.
INSERT INTO role (name, description) VALUES ('ADMIN', 'admin');
INSERT INTO role (name, description) VALUES ('EMPLOYEE', 'employee');
INSERT INTO role (name, description) VALUES ('SUPPORT', 'support');
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'admin@ruven.ru', '0102', (select id from role where role.name = 'ADMIN'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'employee@ruven.ru', '0102', (select id from role where role.name = 'EMPLOYEE'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support1@ruven.ru', '0102', (select id from role where role.name = 'EMPLOYEE'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support2@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support3@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support4@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support5@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support6@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support7@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support8@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support9@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support10@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support11@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support12@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));
INSERT INTO users (employee_id, login, password, role_id) VALUES (null, 'support13@ruven.ru', '0102', (select id from role where role.name = 'SUPPORT'));