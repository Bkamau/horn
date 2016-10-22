package io.vaxly.layouts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.mainUi.Homepage;
import org.parse4j.ParseUser;

/**
 * Created by bkamau on 20.10.2016.
 */
public class UserLayout extends HorizontalLayout implements View, Button.ClickListener {


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
        logoutBtn.addClickListener(this);

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

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getComponent() == logoutBtn){
            ParseUser.currentUser = null;
            getUI().getNavigator().addView(Homepage.NAME, new Homepage());
            navigate(Homepage.NAME);
        }
    }

    private void navigate(String viewName){
        getUI().getNavigator().navigateTo(viewName);
    }
}
