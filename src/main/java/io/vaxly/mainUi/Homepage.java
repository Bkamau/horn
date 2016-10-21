package io.vaxly.mainUi;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 12.10.2016.
 */
public class Homepage extends Panel implements View, Button.ClickListener {

    static final String NAME = "Homepage";
    private Panel mainPanel;
    private Navigator navigator;
    private Button createBtn;
    private Button loginBtn;

    public Homepage(){

        final VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setStyleName("homepage_layout");
        layout.setSizeFull();

        VerticalLayout logoLayout = new VerticalLayout();
        logoLayout.addStyleName("logoLayout");
        logoLayout.setSizeFull();
        logoLayout.setSpacing(true);
        logoLayout.setMargin(true);
        Resource logo = new ThemeResource("img/circled.png");
        Image logoImage = new Image(null, logo);
        logoImage.setWidth(150, Unit.PIXELS);
        logoImage.setHeight(150, Unit.PIXELS);
        logoLayout.addComponent(logoImage);
        logoLayout.setComponentAlignment(logoImage,Alignment.TOP_CENTER);


        Resource logoName = new ThemeResource("img/hornbill.png");
        Image logoNameImage = new Image(null, logoName);
        logoNameImage.setWidth(600, Unit.PIXELS);
        logoNameImage.setHeight(225, Unit.PIXELS);
        logoLayout.addComponent(logoNameImage);
        logoLayout.setComponentAlignment(logoNameImage,Alignment.TOP_CENTER);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSizeUndefined();
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        loginBtn = new Button("LOGIN");
        loginBtn.setWidth(250, Unit.PIXELS);
        loginBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        loginBtn.addStyleName(ValoTheme.BUTTON_HUGE);
        loginBtn.addStyleName("login_buttons");
        loginBtn.addClickListener(this);

        createBtn = new Button("CREATE");
        createBtn.setWidth(250, Unit.PIXELS);
        createBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        createBtn.addStyleName(ValoTheme.BUTTON_HUGE);
        createBtn.addStyleName("login_buttons");
        createBtn.addClickListener(this);
        buttonsLayout.addComponents(loginBtn,createBtn);

        logoLayout.addComponent(buttonsLayout);
        logoLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);

        HorizontalLayout snapLayout = new HorizontalLayout();

        Resource snap = new ThemeResource("img/snap.png");
        Image snapImage = new Image(null, snap);
        snapLayout.addComponent(snapImage);

        logoLayout.addComponent(snapLayout);
        logoLayout.setComponentAlignment(snapLayout, Alignment.TOP_CENTER);

        layout.addComponent(logoLayout);
        setContent(layout);

    }

    private void navigate(String viewName){
        getUI().getNavigator().navigateTo(viewName);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() ==  createBtn){
            getUI().getNavigator().addView(CreateInvoice.NAME, new CreateInvoice());
            navigate(CreateInvoice.NAME);
        } else if (clickEvent.getButton() == loginBtn){
            UI.getCurrent().addWindow(new LoginSignIn());
        }
    }
}
