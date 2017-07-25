CREATE TABLE customers (
  id            SERIAL      NOT NULL,
  customer_name VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (customer_name)
);

CREATE TABLE companies (
  id           SERIAL      NOT NULL,
  company_name VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (company_name)
);

CREATE TABLE projects (
  id           SERIAL      NOT NULL,
  project_name VARCHAR(20) NOT NULL,
  cost         INT         NOT NULL,
  customer_id  INT         NOT NULL,
  company_id   INT         NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (company_id) REFERENCES companies (id),
  FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE TABLE developers (
  id         SERIAL      NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name  VARCHAR(30) NOT NULL,
  birth_date DATE,
  address    VARCHAR(50),
  salary     INT         NOT NULL,
  company_id INT         NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (company_id) REFERENCES companies (id)
);

CREATE TABLE skills (
  id         SERIAL      NOT NULL,
  skill_name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE developer_skills (
  developer_id INT NOT NULL,
  skill_id     INT NOT NULL,
  FOREIGN KEY (developer_id) REFERENCES developers (id),
  FOREIGN KEY (skill_id) REFERENCES skills (id),
  UNIQUE (developer_id, skill_ID)
);

CREATE TABLE developer_projects (
  developer_id INT NOT NULL,
  project_id   INT NOT NULL,
  FOREIGN KEY (developer_id) REFERENCES developers (id),
  FOREIGN KEY (project_id) REFERENCES projects (id),
  UNIQUE (developer_id, project_id)
);