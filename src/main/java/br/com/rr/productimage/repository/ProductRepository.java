package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    @Query(value = "select p.* from product p " +
                    "inner join product_relationship pr on pr.product_id = p.id " +
                    "where pr.parent_product_id = :parentProductId", nativeQuery = true)
    List<ProductEntity> findAllByParentProductId(@Param("parentProductId") Long parentProductId);
}
