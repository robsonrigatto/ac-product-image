package br.com.rr.productimage.controller;

import br.com.rr.productimage.entity.ProductEntity;
import br.com.rr.productimage.repository.ProductRepository;
import br.com.rr.productimage.vo.ProductVO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductVO product, @Context UriInfo uriInfo) {
        ProductEntity entity = new ProductEntity();
        entity.setDescription(product.getDescription());
        entity = this.productRepository.save(entity);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(entity.getId().toString());

        return Response.created(uriBuilder.build()).build();
    }

	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
        List<ProductEntity> entityList = Lists.newArrayList(productRepository.findAll());
        List<ProductVO> vos = entityList.stream().map(entity -> new ProductVO(entity.getId(), entity.getDescription()))
                .collect(Collectors.toList());

        return Response.ok(vos).build();
	}

	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        Optional<ProductEntity> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            ProductEntity entity = optional.get();
            return Response.ok(new ProductVO(entity.getId(), entity.getDescription())).build();
        }

        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProductVO product, @Context UriInfo uriInfo) {
        ProductEntity entity = productRepository.findById(product.getId()).get();
        entity.setDescription(product.getDescription());
        entity = productRepository.save(entity);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(entity.getId().toString());

        return Response.ok(uriBuilder.build()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        productRepository.deleteById(id);
        return Response.noContent().build();
    }
}
