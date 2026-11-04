output "db_host" {
  description = "RDS endpoint host"
  value       = aws_db_instance.mysql.address
}

output "db_port" {
  description = "RDS port"
  value       = aws_db_instance.mysql.port
}

output "db_name" {
  description = "Database name"
  value       = aws_db_instance.mysql.db_name
}

output "db_username" {
  description = "Master username"
  value       = aws_db_instance.mysql.username
}

output "db_password" {
  description = "Master password"
  value       = random_password.db.result
  sensitive   = true
}

output "allowed_cidrs" {
  description = "CIDR blocks allowed to connect"
  value       = local.allowed_cidrs
}
