package br.com.rr.productimage.controller;

import br.com.rr.productimage.entity.ImageEntity;
import br.com.rr.productimage.repository.ImageRepository;
import br.com.rr.productimage.vo.ImageVO;
import io.swagger.annotations.Api;
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
@Path("/images")
@Api
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ImageVO image, @Context UriInfo uriInfo) {
        ImageEntity entity = new ImageEntity();
        entity.setFileName(image.getFileName());
        entity.setFileContent(image.getFileContent());
        entity = this.imageRepository.save(entity);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(entity.getId().toString());

        return Response.created(uriBuilder.build()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<ImageEntity> entityList = Lists.newArrayList(imageRepository.findAll());
        List<ImageVO> vos = entityList.stream().map(entity -> new ImageVO(entity.getId(), entity.getFileName(), entity.getFileContent()))
                .collect(Collectors.toList());

        return Response.ok(entityList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        Optional<ImageEntity> entity = imageRepository.findById(id);

        if(entity.isPresent()) {
            return Response.ok(entity.get()).build();
        }

        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ImageVO image, @Context UriInfo uriInfo) {
        ImageEntity entity = imageRepository.findById(image.getId()).get();
        entity.setFileName(image.getFileName());
        entity.setFileContent(image.getFileContent());
        entity = imageRepository.save(entity);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(entity.getId().toString());

        return Response.ok(uriBuilder.build()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        imageRepository.deleteById(id);
        return Response.noContent().build();
    }
}
