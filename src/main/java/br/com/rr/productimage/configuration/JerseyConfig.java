package br.com.rr.productimage.configuration;

import br.com.rr.productimage.controller.ImageController;
import br.com.rr.productimage.controller.ProductController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ProductController.class);
        register(ImageController.class);
    }
}
