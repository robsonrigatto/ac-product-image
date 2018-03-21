package br.com.rr.productimage.configuration;

import br.com.rr.productimage.controller.ImageController;
import br.com.rr.productimage.controller.ProductController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ProductController.class);
        register(ImageController.class);

        this.configureSwagger();
    }

    private void configureSwagger() {
        register(ApiListingResource.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("product-image");
        beanConfig.setVersion("0.0.1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setDescription("JPA + JAX-RS");
        beanConfig.setContact("rigatto.robson@gmail.com");
        beanConfig.setResourcePackage("br.com.rr.productimage.controller");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
}
