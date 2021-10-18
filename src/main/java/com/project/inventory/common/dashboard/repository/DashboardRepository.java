package com.project.inventory.common.dashboard.repository;

import com.project.inventory.common.dashboard.model.ProductsWithTotalSold;
import com.project.inventory.common.dashboard.model.TotalRevenue;
import com.project.inventory.common.dashboard.model.Totals;
import com.project.inventory.common.permission.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT count(*) totalCustomers, " +
            " (SELECT sum(quantity) FROM order_items item " +
            "left join orders o  " +
            "on item.order_id = o.id " +
            "where o.order_status = 'DELIVERED') as totalSold, " +
            " (SELECT count(*) totalProducts FROM products product " +
            "WHERE product.product_is_deleted = 0) as totalProducts " +
            "FROM accounts account " +
            "LEFT JOIN user_privileges privilege ON account.id = privilege.account_id " +
            "LEFT JOIN user_roles role ON role.id = privilege.role_id " +
            "WHERE role.role_name = 'USER' OR role.role_name = 'CUSTOMER'", nativeQuery = true)
    Totals findAllTotals();

    @Query(value = "SELECT year(paid_at) year, sum(total_amount) totalRevenue " +
            " FROM orders o " +
            "WHERE o.payment_status = 1", nativeQuery = true)
    TotalRevenue getTotalRevenueByYear();

    @Query(value = "SELECT product.id id, product.product_name productName, product.product_price productPrice, product.barcode barcode, count(product.id) as totalSold, o.order_status status " +
            "FROM order_items item " +
            "JOIN products product " +
            "ON item.product_id = product.id " +
            "JOIN orders o " +
            "ON item.order_id = o.id " +
            "WHERE o.order_status = 'DELIVERED' AND (product.product_name LIKE concat('%',:query, '%') OR product.barcode LIKE  concat('%',:query, '%')) " +
            "GROUP BY product.id", nativeQuery = true)
    Page<ProductsWithTotalSold> findAllProductSAndCountTotalSold( @Param ( "query" ) String query, Pageable pageable);

    @Query(value = "select year(paid_at) year, month(paid_at) month,sum(total_amount) totalRevenue " +
            "from orders " +
            "where year(paid_at) =:year AND payment_status = 1 " +
            "group by year(paid_at), month(paid_at) " +
            "order by year(paid_at), month(paid_at)", nativeQuery = true)
    List<TotalRevenue> getRevenue( @Param("year") int year);
}
