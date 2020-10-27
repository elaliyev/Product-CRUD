package com.example.productcrud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*.html")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false)
                .addResolver(new WebJarsResourceResolver())
                .addResolver(new PathResourceResolver())
                .addTransformer(new IndexPageTransformer());
    }

    public class IndexPageTransformer implements ResourceTransformer {
        @Override
        public Resource transform(HttpServletRequest request, Resource resource,
                                  ResourceTransformerChain transformerChain) throws IOException, IOException {
            if (resource.getURL().toString().endsWith("/index.html")) {
                String html = getHtmlContent(resource);
                html = overwritePetStore(html);
                return new TransformedResource(resource, html.getBytes());
            } else {
                return resource;
            }
        }

        private String overwritePetStore(String html) {
            return html.replace("https://petstore.swagger.io/v2/swagger.json",
                    "/api-docs");
        }
        private String getHtmlContent(Resource resource) {
            try {
                InputStream inputStream = resource.getInputStream();
                java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
                String content = s.next();
                inputStream.close();
                return content;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
