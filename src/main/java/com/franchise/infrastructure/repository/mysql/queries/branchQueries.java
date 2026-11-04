package com.franchise.infrastructure.repository.mysql.queries;

public class branchQueries {

  private static final String QUERY_FIND_BY_ID = """
    SELECT b.id, b.franchise_id, f.name AS franchise_name, b.name, b.status, b.created_at, b.updated_at
    FROM branch b
    JOIN franchise f ON f.id = b.franchise_id
    WHERE b.id = :id
""";

  private static final String QUERY_INSERT = """
    INSERT INTO branch (franchise_id, name) VALUES (:franchise_id, :name)
""";

  private static final String QUERY_UPDATE = """
    UPDATE branch SET name = :name WHERE id = :id
""";

  public static String findById() {
    return QUERY_FIND_BY_ID;
  }

  public static String insert() {
    return QUERY_INSERT;
  }

  public static String update() {
    return QUERY_UPDATE;
  }
}
