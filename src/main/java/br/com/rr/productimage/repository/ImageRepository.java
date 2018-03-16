package br.com.rr.productimage.repository;

import br.com.rr.productimage.entity.ImageEntity;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {
}
