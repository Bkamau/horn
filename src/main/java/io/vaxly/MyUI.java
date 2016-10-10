package io.vaxly;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
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
public class MyUI extends UI implements Button.ClickListener, Property.ValueChangeListener{

    private VerticalLayout firstVerticalLayout;

    private Button addBtn;

    private TextField subTitle ;
    private TextField taxTitle ;
    private Label totalLable ;

    Label priceLabel;

    private ArrayList<Button> addBtnList = new ArrayList<>();
    private ArrayList<Button> delBtnList = new ArrayList<>();
    private ArrayList<Component> componentArrayList= new ArrayList<>();
    private ArrayList<TextField> priceArrayList = new ArrayList<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Panel  mainPaneL = new Panel();
        mainPaneL.setResponsive(true);




        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        mainPaneL.setContent(horizontalLayout);

        VerticalLayout v1 = new VerticalLayout();
        VerticalLayout v2 = new VerticalLayout();


        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setStyleName("main-layout");

        horizontalLayout.addComponents(v1, mainLayout, v2);
        horizontalLayout.setExpandRatio(v1, 1);
        horizontalLayout.setExpandRatio(mainLayout, 3);
        horizontalLayout.setExpandRatio(v2, 1);

        firstVerticalLayout = new VerticalLayout();
        VerticalLayout seconVerticalLayoutd = new VerticalLayout();
        seconVerticalLayoutd.setWidth(100, Unit.PERCENTAGE);
        mainLayout.addComponents(firstVerticalLayout, seconVerticalLayoutd);

        firstVerticalLayout.addComponent(new DetailLayout());
        firstVerticalLayout.addComponent(new AddressView());
        firstVerticalLayout.addComponent(new TitleView());

        VerticalLayout totalVerticalLayout = totalLayout();

        addDemBills();

        seconVerticalLayoutd.addComponent(totalVerticalLayout);
        seconVerticalLayoutd.setComponentAlignment(totalVerticalLayout, Alignment.MIDDLE_RIGHT);


        HorizontalLayout footerLayout = new FooterView();
        seconVerticalLayoutd.addComponent(footerLayout);
        seconVerticalLayoutd.setComponentAlignment(footerLayout, Alignment.BOTTOM_CENTER);


        setContent(mainPaneL);

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() == addBtn){

            addDemBills();

            delBtnList.get((delBtnList.size()-2)).setStyleName("visible");
            delBtnList.get((delBtnList.size()-2)).addStyleName(ValoTheme.BUTTON_BORDERLESS);
            int addBtnIndex = addBtnList.indexOf(addBtn);
            addBtnList.get(addBtnIndex -1).setStyleName("invisible");


        }else {
            int delBtnIndex = delBtnList.indexOf(clickEvent.getButton());

            Notification.show("del" + delBtnIndex, Notification.Type.TRAY_NOTIFICATION);

            addBtnList.remove(delBtnIndex);
            delBtnList.remove(delBtnIndex);
            firstVerticalLayout.removeComponent(componentArrayList.get(delBtnIndex));
            componentArrayList.remove(delBtnIndex);
            priceArrayList.remove(delBtnIndex);
            totalPrice();


        }
    }

    private void addDemBills(){


        addBtn = new Button("", FontAwesome.PLUS_SQUARE);
        Button delBtn = new Button("", FontAwesome.MINUS_SQUARE);
        addBtn.addClickListener(this);
        delBtn.addClickListener(this);

        addBtn.setWidth(100, Unit.PERCENTAGE);
        addBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        addBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        delBtn.setWidth(100, Unit.PERCENTAGE);
        delBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        delBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        delBtn.setStyleName("invisible");

        HorizontalLayout billsHorizontalLayout = new HorizontalLayout();
        // billsHorizontalLayout.setMargin(true);
        billsHorizontalLayout.setSpacing(true);
        billsHorizontalLayout.setWidth(100,Unit.PERCENTAGE);
        billsHorizontalLayout.setStyleName("bills");


        TextField qntytTextField = new TextField();
        TextField descTextField = new TextField();
        TextField amtTextField = new TextField();
        TextField priceTextField = new TextField();

       priceTextField.addValueChangeListener(this);

        HorizontalLayout billz = new BillsView(qntytTextField, descTextField, amtTextField, priceTextField);

        billsHorizontalLayout.addComponents(addBtn, billz, delBtn);
        billsHorizontalLayout.setExpandRatio(addBtn,1);
        billsHorizontalLayout.setExpandRatio(billz,15);
        billsHorizontalLayout.setExpandRatio(delBtn,1);

        addBtnList.add(addBtn);
        delBtnList.add(delBtn);
        componentArrayList.add(billsHorizontalLayout);

        priceArrayList.add(priceTextField);
        totalPrice();

        firstVerticalLayout.addComponents(billsHorizontalLayout);


    }


    private VerticalLayout totalLayout (){

        VerticalLayout layout = new VerticalLayout();
        FormLayout totalFormLayout = new FormLayout();

         subTitle = new TextField("Sub Total");
         taxTitle = new TextField("Tax");
         totalLable = new Label("TOTAL");

        taxTitle.setInputPrompt("                               24%");
        taxTitle.addValueChangeListener(this);
        taxTitle.setImmediate(true);

        totalLable.setValue(" 000");
        totalLable.setCaption("TOTAL");
        totalLable.setStyleName(ValoTheme.LABEL_BOLD);
        totalLable.setStyleName(ValoTheme.LABEL_HUGE);
        totalFormLayout.addComponents(subTitle,taxTitle,totalLable);

        layout.addComponents(totalFormLayout);

        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth(35, Unit.PERCENTAGE);
        layout.setStyleName("total-style");
        return layout;


    }

    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        totalPrice();
    }

    private void totalPrice(){

        double sum = 0;
        for (int i = 0; i < priceArrayList.size(); i++){
           String value = priceArrayList.get(i).getValue();
            if (value.isEmpty()){
                sum = sum;
            }else {
                double total = Double.parseDouble(priceArrayList.get(i).getValue());
                sum += total;
            }

        }

        double tax;
        subTitle.setValue(String.valueOf(sum));

        String taxation = taxTitle.getValue();
        if (!taxation.isEmpty()){
            tax = 1 + (Double.parseDouble(taxTitle.getValue())/100);
        }else {
            tax = 1.24;
        }

        Double finalSum = sum*tax;
        totalLable.setValue(String.valueOf(finalSum));



    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
