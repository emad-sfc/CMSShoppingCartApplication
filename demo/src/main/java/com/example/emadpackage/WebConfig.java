package com.example.emadpackage;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	

//     @Override
//     public void addViewControllers(ViewControllerRegistry registry) {
//         registry.addViewController("/").setViewName("home");
//     }

   //when replacing ("/static/**") with media below (/media/**), all images are not being displayed.Images who have space b/w words are not being displayed.
     //i.e 'black tshirt' is not displayed but 'apples' is displayed.
     
     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry
             .addResourceHandler("/static/**")   
             .addResourceLocations("file:/C:/Users/EMAD/git/SpringProject-shoppingcart/demo/src/main/resources/static/media/");
     }
}
