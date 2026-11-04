package com.franchise.infrastructure.repository.mysql.queries;

public class FranchiseQueries {

  private static final String QUERY_FIND_ALL = """
    SELECT id, name, status, created_at, updated_at FROM franchise
""";

  private static final String QUERY_FIND_BY_ID = """
    SELECT id, name, status, created_at, updated_at FROM franchise WHERE id = :id
""";

  private static final String QUERY_INSERT = """
    INSERT INTO franchise (name) VALUES (:name)
""";

  private static final String QUERY_UPDATE = """
    UPDATE franchise SET name = :name WHERE id = :id
""";

  private static final String QUERY_DELETE = """
    UPDATE franchise
    SET status = CASE WHEN status = 'ACTIVE' THEN 'INACTIVE' ELSE 'ACTIVE' END
    WHERE id = :id
""";

  public static String findAll() {
    return QUERY_FIND_ALL;
  }

  public static String findById() {
    return QUERY_FIND_BY_ID;
  }

  public static String insert() {
    return QUERY_INSERT;
  }

  public static String update() {
    return QUERY_UPDATE;
  }

  public static String delete() {
    return QUERY_DELETE;
  }
}
