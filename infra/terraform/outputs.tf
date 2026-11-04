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

output "ecr_repository_url" {
  description = "ECR repository for the API image"
  value       = aws_ecr_repository.app.repository_url
}

output "app_url" {
  description = "Base URL of the deployed API"
  value       = "http://${aws_instance.app.public_ip}:8080/api/v1"
}

output "swagger_url" {
  description = "Swagger UI of the deployed API"
  value       = "http://${aws_instance.app.public_ip}:8080/api/v1/swagger-ui.html"
}
