data "aws_vpc" "default" {
  default = true
}

data "http" "my_ip" {
  url = "https://checkip.amazonaws.com"
}

locals {
  allowed_cidrs = length(var.allowed_cidrs) > 0 ? var.allowed_cidrs : ["${chomp(data.http.my_ip.response_body)}/32"]
}

resource "random_password" "db" {
  length           = 20
  special          = true
  override_special = "!#$%^&*()-_=+"
}

resource "aws_security_group" "db" {
  name        = "${var.db_identifier}-sg"
  description = "MySQL access for franchise-api"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    description = "MySQL"
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = local.allowed_cidrs
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_instance" "mysql" {
  identifier     = var.db_identifier
  engine         = "mysql"
  engine_version = "8.4"
  instance_class = var.db_instance_class

  allocated_storage     = 20
  max_allocated_storage = 0
  storage_type          = "gp3"
  storage_encrypted     = true

  db_name  = var.db_name
  username = var.db_username
  password = random_password.db.result

  publicly_accessible    = true
  vpc_security_group_ids = [aws_security_group.db.id]
  multi_az               = false

  backup_retention_period = 1
  skip_final_snapshot     = true
  deletion_protection     = false
  apply_immediately       = true
}
