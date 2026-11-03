CREATE TABLE IF NOT EXISTS franchise (
  id         BIGINT                     NOT NULL AUTO_INCREMENT,
  name       VARCHAR(100)               NOT NULL,
  status     ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  created_at DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT uk_franchise_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS branch (
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  franchise_id BIGINT       NOT NULL,
  name         VARCHAR(100) NOT NULL,
  status       ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT uk_branch_franchise_name UNIQUE (franchise_id, name),
  CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  branch_id  BIGINT       NOT NULL,
  name       VARCHAR(100) NOT NULL,
  stock      INT          NOT NULL DEFAULT 0,
  created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT uk_product_branch_name UNIQUE (branch_id, name),
  CONSTRAINT ck_product_stock CHECK (stock >= 0),
  CONSTRAINT fk_product_branch FOREIGN KEY (branch_id) REFERENCES branch (id) ON DELETE CASCADE
);


INSERT IGNORE INTO franchise (id, name, status) VALUES
  (1, 'Cosechas',      'ACTIVE'),
  (2, 'El Rancherito', 'ACTIVE'),
  (3, 'Arturo Calle',  'INACTIVE');

INSERT IGNORE INTO branch (id, franchise_id, name, status) VALUES
  (1, 1, 'El Poblado',   'ACTIVE'),
  (2, 1, 'Laureles',     'ACTIVE'),
  (3, 1, 'Belén',        'ACTIVE'),
  (4, 2, 'Las Palmas',   'ACTIVE'),
  (5, 2, 'Centro',       'INACTIVE'),
  (6, 3, 'C.C. Santafé', 'INACTIVE'),
  (7, 3, 'C.C. Oviedo',  'INACTIVE');

INSERT IGNORE INTO product (id, branch_id, name, stock) VALUES
  (1,  1, 'Jugo de Mango',       120),
  (2,  1, 'Batido de Fresa',      80),
  (3,  1, 'Smoothie Verde',       35),
  (4,  1, 'Jugo de Lulo',         15),
  (5,  2, 'Jugo de Mango',        60),
  (6,  2, 'Batido de Banano',     60),
  (7,  2, 'Jugo de Maracuyá',     20),
  (8,  4, 'Bandeja Paisa',        90),
  (9,  4, 'Arepa con Quesito',   200),
  (10, 4, 'Mazamorra',             0),
  (11, 5, 'Bandeja Paisa',       150),
  (12, 5, 'Arepa con Quesito',   140),
  (13, 5, 'Chicharrón',           75),
  (14, 6, 'Camisa Oxford',        45),
  (15, 6, 'Pantalón Chino',       30),
  (16, 7, 'Camisa Oxford',        25),
  (17, 7, 'Blazer Clásico',       10),
  (18, 7, 'Jean Slim Fit',        50);
