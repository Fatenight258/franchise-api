variable "region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "db_identifier" {
  description = "RDS instance identifier"
  type        = string
  default     = "franchise-db"
}

variable "db_name" {
  description = "Database name"
  type        = string
  default     = "franchise"
}

variable "db_username" {
  description = "Master username"
  type        = string
  default     = "franchise"
}

variable "db_instance_class" {
  description = "RDS instance class"
  type        = string
  default     = "db.t4g.micro"
}

variable "allowed_cidrs" {
  description = "CIDR blocks allowed to connect to MySQL. If empty, only the current public IP is allowed"
  type        = list(string)
  default     = []
}
