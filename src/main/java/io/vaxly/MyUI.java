package io.vaxly;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI implements Button.ClickListener{

    public VerticalLayout mainLayout;
    public HorizontalLayout  billsHorizontalLayout;
    HorizontalLayout billz;
    Button addBtn;
    Button delBtn;

    int addBtnIndex;
    int delBtnIndex;

    ArrayList<Button> addBtnList = new ArrayList<>();
    ArrayList<Button> delBtnList = new ArrayList<>();
    ArrayList<Component> componentArrayList= new ArrayList<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        VerticalLayout v1 = new VerticalLayout();
        VerticalLayout v2 = new VerticalLayout();

        mainLayout = new VerticalLayout();
       // mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setStyleName("main-layout");

        horizontalLayout.addComponents(v1, mainLayout, v2);
        horizontalLayout.setExpandRatio(v1, 1);
        horizontalLayout.setExpandRatio(mainLayout, 3);
        horizontalLayout.setExpandRatio(v2, 1);

        mainLayout.addComponent(new DetailLayout());
        mainLayout.addComponent(new AddressView());
        mainLayout.addComponent(new TitleView());

        addDemBills();




        setContent(horizontalLayout);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() == addBtn){

            addDemBills();

            delBtnList.get((delBtnList.size()-2)).setStyleName("visible");
            delBtnList.get((delBtnList.size()-2)).addStyleName(ValoTheme.BUTTON_BORDERLESS);
            addBtnIndex = addBtnList.indexOf(addBtn);
            addBtnList.get(addBtnIndex-1).setStyleName("invisible");


        }else {
            delBtnIndex = delBtnList.indexOf(clickEvent.getButton());

            Notification.show("del" + delBtnIndex , Notification.Type.TRAY_NOTIFICATION);

            addBtnList.remove(delBtnIndex);
            delBtnList.remove(delBtnIndex);
            mainLayout.removeComponent(componentArrayList.get(delBtnIndex));
            componentArrayList.remove(delBtnIndex);
        }
    }

    private void addDemBills(){


        addBtn = new Button("", FontAwesome.PLUS_SQUARE);
        delBtn = new Button("", FontAwesome.MINUS_SQUARE);
        addBtn.addClickListener(this);
        delBtn.addClickListener(this);

        addBtn.setWidth(100, Unit.PERCENTAGE);
        addBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        addBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        delBtn.setWidth(100, Unit.PERCENTAGE);
        delBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        delBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        delBtn.setStyleName("invisible");

        billsHorizontalLayout = new HorizontalLayout();
        // billsHorizontalLayout.setMargin(true);
        billsHorizontalLayout.setSpacing(true);
        billsHorizontalLayout.setWidth(100,Unit.PERCENTAGE);
        billsHorizontalLayout.setStyleName("bills");

        billz = new BillsView();

        billsHorizontalLayout.addComponents(addBtn, billz, delBtn);
        billsHorizontalLayout.setExpandRatio(addBtn,1);
        billsHorizontalLayout.setExpandRatio(billz,15);
        billsHorizontalLayout.setExpandRatio(delBtn,1);

        addBtnList.add(addBtn);
        delBtnList.add(delBtn);
        componentArrayList.add(billsHorizontalLayout);

        mainLayout.addComponents(billsHorizontalLayout);


    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
