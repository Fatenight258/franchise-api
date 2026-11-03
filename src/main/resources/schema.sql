CREATE TABLE IF NOT EXISTS franchise (
  id   BIGINT       NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_franchise_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS branch (
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  franchise_id BIGINT       NOT NULL,
  name         VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_branch_franchise_name UNIQUE (franchise_id, name),
  CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product (
  id        BIGINT       NOT NULL AUTO_INCREMENT,
  branch_id BIGINT       NOT NULL,
  name      VARCHAR(100) NOT NULL,
  stock     INT          NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT uk_product_branch_name UNIQUE (branch_id, name),
  CONSTRAINT ck_product_stock CHECK (stock >= 0),
  CONSTRAINT fk_product_branch FOREIGN KEY (branch_id) REFERENCES branch (id) ON DELETE CASCADE
);