package br.com.rr.productimage.controller;

import br.com.rr.productimage.entity.ImageEntity;
import br.com.rr.productimage.entity.ProductEntity;
import br.com.rr.productimage.repository.ImageRepository;
import br.com.rr.productimage.repository.ProductRepository;
import br.com.rr.productimage.vo.ImageVO;
import br.com.rr.productimage.vo.ProductVO;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Path("/products")
@Api
public class ProductController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(ProductVO productVO, @Context UriInfo uriInfo) {
        ProductEntity savedEntity = this.voToEntity(productVO);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(savedEntity.getId().toString());

        return Response.created(uriBuilder.build()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@DefaultValue("false") @QueryParam("includeChildProducts") Boolean includeChildProducts,
                            @DefaultValue("false") @QueryParam("includeImages") Boolean includeImages) {
        List<ProductEntity> entityList = Lists.newArrayList(productRepository.findAll());

        List<ProductVO> productsVO = entityList.stream().map(entity -> {
            ProductVO productVO = new ProductVO(entity.getId(), entity.getDescription());
            this.loadChildrenInformation(includeChildProducts, includeImages, entity, productVO);

            return productVO;
        }).collect(Collectors.toList());

        return Response.ok(productsVO).build();
	}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id,
                             @DefaultValue("false") @QueryParam("includeChildProducts") Boolean includeChildProducts,
                             @DefaultValue("false") @QueryParam("includeImages") Boolean includeImages) {
        Optional<ProductEntity> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            ProductEntity entity = optional.get();
            ProductVO productVO = new ProductVO(entity.getId(), entity.getDescription());

            this.loadChildrenInformation(includeChildProducts,includeImages, entity, productVO);

            return Response.ok(productVO).build();
        }

        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductVO productVO, @Context UriInfo uriInfo) {
        ProductEntity savedEntity = this.voToEntity(productVO);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(savedEntity.getId().toString());

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        productRepository.deleteById(id);
        return Response.noContent().build();
    }

    private ProductEntity voToEntity(ProductVO productVO) {
        final ProductEntity entity = new ProductEntity();
        entity.setId(productVO.getId());
        entity.setDescription(productVO.getDescription());

        entity.getChildProducts().clear();
        productVO.getChildProducts().stream().forEach(cpVO -> {
            entity.getChildProducts().add(productRepository.findById(cpVO.getId()).get());
        });

        entity.getImages().clear();
        productVO.getImages().stream().forEach(imgVO -> {
            ImageEntity image = new ImageEntity();
            image.setFileName(imgVO.getFileName());
            image.setFileContent(imgVO.getFileContent());
            entity.getImages().add(image);
        });

        return this.productRepository.save(entity);
    }

    private void loadChildrenInformation(Boolean includeChildProducts, Boolean includeImages,
                                         ProductEntity entity, ProductVO vo) {
        if(includeChildProducts) {
            List<ProductEntity> childProducts = productRepository.findAllByParentProductId(entity.getId());
            List<ProductVO> childProductsVO = childProducts.stream().map(cp -> new ProductVO(cp.getId(), cp.getDescription()))
                    .collect(Collectors.toList());
            vo.setChildProducts(childProductsVO);
        }

        if(includeImages) {
            List<ImageEntity> images = imageRepository.findAllByProductId(entity.getId());

            List<ImageVO> imageVOS = images.stream().map(img -> new ImageVO(img.getId(), img.getFileName(), img.getFileContent()))
                    .collect(Collectors.toList());
            vo.setImages(imageVOS);
        }
    }
}
