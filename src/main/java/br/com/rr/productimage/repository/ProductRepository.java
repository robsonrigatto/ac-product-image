package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "select p.* from product p " +
                    "inner join product_relationship pr on pr.product_id = p.id " +
                    "where pr.parent_product_id = :parentProductId", nativeQuery = true)
    List<ProductEntity> findAllByParentProductId(@Param("parentProductId") Long parentProductId);


    @Transactional
    @Modifying
    @Query(value = "delete from product_relationship pr where pr.parent_product_id = :parentProductId", nativeQuery = true)
    void deleteAllChildProductsByProductId(@Param("parentProductId") Long parentProductId);
}
