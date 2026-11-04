package com.franchise.infrastructure.repository.mysql.queries;

public class ProductQueries {

  private static final String QUERY_FIND_BY_ID = """
    SELECT p.id, p.branch_id, b.name AS branch_name, p.name, p.stock, p.created_at, p.updated_at
    FROM product p
    JOIN branch b ON b.id = p.branch_id
    WHERE p.id = :id
""";

  private static final String QUERY_INSERT = """
    INSERT INTO product (branch_id, name, stock) VALUES (:branch_id, :name, :stock)
""";

  private static final String QUERY_UPDATE = """
    UPDATE product SET name = :name WHERE id = :id
""";

  private static final String QUERY_UPDATE_STOCK = """
    UPDATE product SET stock = :stock WHERE id = :id
""";

  private static final String QUERY_DELETE = """
    DELETE FROM product WHERE id = :id
""";

  private static final String QUERY_FIND_TOP_STOCK_BY_FRANCHISE_ID = """
    SELECT branch_id, branch_name, product_id, product_name, stock
    FROM (
      SELECT b.id AS branch_id, b.name AS branch_name,
             p.id AS product_id, p.name AS product_name, p.stock,
             RANK() OVER (PARTITION BY b.id ORDER BY p.stock DESC) AS rn
      FROM branch b
      JOIN product p ON p.branch_id = b.id
      WHERE b.franchise_id = :franchise_id
        AND b.status = 'ACTIVE'
    ) t
    WHERE rn = 1
    ORDER BY branch_id, product_id
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

  public static String updateStock() {
    return QUERY_UPDATE_STOCK;
  }

  public static String delete() {
    return QUERY_DELETE;
  }

  public static String findTopStockByFranchiseId() {
    return QUERY_FIND_TOP_STOCK_BY_FRANCHISE_ID;
  }
}
