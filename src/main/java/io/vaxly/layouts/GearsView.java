package io.vaxly.layouts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

/**
 * Created by bkamau on 16.10.2016.
 */
public class GearsView extends Window implements View {

    HorizontalLayout layout;
    public GearsView()  {


        center();
        setModal(true);
        setSizeFull();
        setResizable(false);
         setClosable(false);
        addStyleName("preview-layout");

        Resource res = new ThemeResource("img/gears.svg");
        Embedded gears = new Embedded("", res);

        layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.addStyleName("preview-layout");
        layout.addComponent(gears);
        layout.setComponentAlignment(gears, Alignment.MIDDLE_CENTER);

        setContent(layout);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
