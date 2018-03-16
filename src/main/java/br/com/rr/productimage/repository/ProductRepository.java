package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

}
