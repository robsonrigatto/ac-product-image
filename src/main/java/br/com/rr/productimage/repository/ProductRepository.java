package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ProductEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByParentProduct(ProductEntity parentProduct);
}
