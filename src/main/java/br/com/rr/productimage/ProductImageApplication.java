package br.com.rr.productimage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProductImageApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new ProductImageApplication()
				.configure(new SpringApplicationBuilder(ProductImageApplication.class))
				.run(args);
	}
}
