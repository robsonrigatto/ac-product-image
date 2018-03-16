package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ImageEntity;
import br.com.rr.productimage.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {

    @Query(value = "select img.* from image img inner join product_image pi on pi.image_id = img.id where pi.product_id = :productId", nativeQuery = true)
    List<ImageEntity> findAllByProductId(@Param("productId") Long productId);
}
