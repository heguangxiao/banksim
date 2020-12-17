package vn.htcjsc.web.banksim.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import vn.htcjsc.web.banksim.common.DateProc;
import vn.htcjsc.web.banksim.db.DBPool;

public class WebInitializer implements WebApplicationInitializer {

    static Logger logger = Logger.getLogger(WebInitializer.class);
    public static String configDir;
    public static String rootDir;
    public static ServletContext context;

    //---
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("----On Startup - CMS [" + DateProc.createTimestamp() + "]------");
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);
        ctx.setServletContext(servletContext);
        Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
        
        // UtF8 Charactor Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
 
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
        
        context = servletContext;
        // Add New Test
        servletContext.addListener(new RequestContextListener());
        // assign root Dir        
        rootDir = servletContext.getRealPath("/");
        rootDir = rootDir.replaceAll("\\\\", "/");

        configDir = rootDir + "WEB-INF/classes/";
        configDir = configDir.replaceAll("\\\\", "/");
        DBPool.init();
//        System.out.println("-->rootDir: " + rootDir);
//        System.out.println("--->configDir: " + configDir);

        System.out.println("----On End - CMS [" + DateProc.createTimestamp() + "]------");
    }

//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{WebConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return null;
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
}
