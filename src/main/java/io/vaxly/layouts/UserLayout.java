package io.vaxly.layouts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 20.10.2016.
 */
public class UserLayout extends HorizontalLayout implements View {


    private Button logoutBtn;
    private Button invoiceBtn;
    private Button customerBtn;
    private Button nameBtn;
    public UserLayout( ){


        nameBtn = new Button("Account");
        nameBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        nameBtn.addStyleName(ValoTheme.LABEL_LARGE);
        nameBtn.addStyleName("user-button");

        logoutBtn = new Button("Logout");
        logoutBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        logoutBtn.addStyleName(ValoTheme.LABEL_LARGE);
        logoutBtn.addStyleName("user-button");

        invoiceBtn = new Button("Invoices");
        invoiceBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        invoiceBtn.addStyleName(ValoTheme.LABEL_LARGE);
        invoiceBtn.addStyleName("user-button");

        customerBtn = new Button("Customers");
        customerBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        customerBtn.addStyleName(ValoTheme.LABEL_LARGE);
        customerBtn.addStyleName("user-button");


        addComponents(nameBtn,invoiceBtn,customerBtn,logoutBtn);
        setComponentAlignment(logoutBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(customerBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(invoiceBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(nameBtn,Alignment.TOP_LEFT);
        addStyleName("user-layout");

        setSpacing(true);

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
