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

variable "app_instance_type" {
  description = "EC2 instance type for the API"
  type        = string
  default     = "t3.micro"
}

variable "app_allowed_cidrs" {
  description = "CIDR blocks allowed to call the API on port 8080"
  type        = list(string)
  default     = ["0.0.0.0/0"]
}

variable "image_tag" {
  description = "Docker image tag deployed on the instance"
  type        = string
  default     = "latest"
}
