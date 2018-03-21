package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ImageEntity;
import br.com.rr.productimage.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {

    @Query(value = "select img.* from image img inner join product_image pi on pi.image_id = img.id where pi.product_id = :productId", nativeQuery = true)
    List<ImageEntity> findAllByProductId(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query(value = "delete from product_image pi where pi.product_id = :productId", nativeQuery = true)
    void deleteAllImagesByProductId(@Param("productId") Long productId);
}
