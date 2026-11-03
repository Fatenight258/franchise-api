package com.franchise.infrastructure.repository.mysql.queries;

public class productQueries {

  private static final String QUERY_FIND_ALL = """
    SELECT id, branch_id, name, stock, created_at, updated_at FROM product
""";

  private static final String QUERY_FIND_BY_ID = """
    SELECT id, branch_id, name, stock, created_at, updated_at FROM product WHERE id = :id
""";

  private static final String QUERY_INSERT = """
    INSERT INTO product (branch_id, name, stock) VALUES (:branchId, :name, :stock)
""";

  private static final String QUERY_UPDATE = """
    UPDATE product SET name = :name, stock = :stock WHERE id = :id
""";

  private static final String QUERY_DELETE = """
    DELETE FROM product WHERE id = :id
""";

  private static final String QUERY_FIND_TOP_STOCK_BY_FRANCHISE_ID = """
    SELECT branch_id, branch_name, product_id, product_name, stock
    FROM (
      SELECT b.id AS branch_id, b.name AS branch_name,
             p.id AS product_id, p.name AS product_name, p.stock,
             ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY p.stock DESC, p.id) AS rn
      FROM branch b
      JOIN product p ON p.branch_id = b.id
      WHERE b.franchise_id = :franchiseId
        AND b.status = 'ACTIVE'
    ) t
    WHERE rn = 1
    ORDER BY branch_id
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

  public static String findTopStockByFranchiseId() {
    return QUERY_FIND_TOP_STOCK_BY_FRANCHISE_ID;
  }
}
