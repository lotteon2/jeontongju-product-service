package com.jeontongju.product.repository;

import com.jeontongju.product.domain.Product;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, String> {

  @Query("select p from Product p where p.sellerId = :sellerId AND p.isDeleted = false ")
  List<Product> findBySellerIdWithoutIsDeleted(Long sellerId);

  @Query(
      "SELECT p FROM Product p WHERE p.productId = :productId AND p.isDeleted = false AND p.isActivate = true")
  Optional<Product> findByProductIdsForOrder(@Param("productId") String productId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT p FROM Product p where p.productId = :productId")
  Optional<Product> findByProductIdForUpdateStock(String productId);

  List<Product> findBySellerId(Long memberId);
}
