/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

/**
 *
 * @author Private
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "vn.htcjsc.web.banksim.*")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/tiles/tiles.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

//    @Bean
//    public UrlBasedViewResolver viewResolver() {
//        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
//        viewResolver.setViewClass(TilesView.class);
//        return viewResolver;
//    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // GUI
        registry.addResourceHandler("/js/**").addResourceLocations("/res/js/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
        // Admin
        registry.addResourceHandler("/admin/css/**").addResourceLocations("/admin/resources/css/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
        registry.addResourceHandler("/admin/js/**").addResourceLocations("/admin/resources/js/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
        registry.addResourceHandler("/admin/dateTimeMaster/**").addResourceLocations("/admin/resources/dateTimeMaster/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
        registry.addResourceHandler("/admin/img/**").addResourceLocations("/admin/resources/img/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
        registry.addResourceHandler("/admin/images/**").addResourceLocations("/admin/resources/images/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
        registry.addResourceHandler("/admin/controller/**").addResourceLocations("/admin/resources/controller/").setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS).cachePublic());
    }

//    @Bean
//    public ConnectionPoolManager initConnPool() {
//        System.out.println("Vao initConnPool Luc: "+DateProc.createTimestamp());
//        ConnectionPoolManager connPoolMng = null;
//        try {
//            File fcf = new File(WebInitializer.configDir + "dbpool.properties");
//            connPoolMng = ConnectionPoolManager.getInstance(fcf, "UTF-8");
//            connPoolMng.registerShutdownHook();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return connPoolMng;
//    }
}
