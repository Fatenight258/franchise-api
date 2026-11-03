package com.franchise.infrastructure.repository.mysql.queries;

public class branchQueries {

  private static final String QUERY_FIND_ALL = """
    SELECT id, franchise_id, name, status, created_at, updated_at FROM branch
""";

  private static final String QUERY_FIND_BY_ID = """
    SELECT id, franchise_id, name, status, created_at, updated_at FROM branch WHERE id = :id
""";

  private static final String QUERY_INSERT = """
    INSERT INTO branch (franchise_id, name) VALUES (:franchiseId, :name)
""";

  private static final String QUERY_UPDATE = """
    UPDATE branch SET name = :name WHERE id = :id
""";

  private static final String QUERY_DELETE = """
    UPDATE branch
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
