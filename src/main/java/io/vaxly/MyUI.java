package io.vaxly;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import io.vaxly.mainUi.CreateInvoice;
import io.vaxly.mainUi.Homepage;
import org.parse4j.Parse;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@PreserveOnRefresh
public class MyUI extends UI {

    Navigator navigator;
    private final Properties properties = new Properties();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        if(this.loadProperties("constants.properties")){
            String appID = this.properties.getProperty("APP_ID");
            String restID = this.properties.getProperty("REST_ID");
            String URL = this.properties.getProperty("URL");
            Parse.initialize(appID, restID, URL);
        }

        getPage().setTitle("Hornbill");

        Navigator navigator = new Navigator(this, this);

        // Add some Views
        navigator.addView("", new Homepage());
        navigator.addView(CreateInvoice.NAME, new CreateInvoice());



    }

    private boolean loadProperties(String filename) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            this.properties.load(inputStream);
            return true;
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
