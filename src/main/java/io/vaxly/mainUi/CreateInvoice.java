package io.vaxly.mainUi;

import com.vaadin.data.Property;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.views.*;

import java.util.ArrayList;

/**
 * Created by bkamau on 12.10.2016.
 */
public class CreateInvoice extends Panel implements View, Button.ClickListener, Property.ValueChangeListener {

    public static final String NAME = "CreateInvoice";
    Navigator navigator;
    Panel  mainPaneL = new Panel();
    private VerticalLayout firstVerticalLayout;

    private Button addBtn;
    private  Button delBtn;
    private Button btnpreview;

    private Button euroBtn ;
    private Button poundBtn ;
    private Button dollarBtn;

    private TextField subTitle ;
    private TextField taxTitle ;
    private Label totalLable ;

    private PopupView popup;
    Label priceLabel;
    private String totalSum ;
    private String currency ;

    private ArrayList<Button> addBtnList = new ArrayList<>();
    private ArrayList<Button> delBtnList = new ArrayList<>();
    private ArrayList<Component> componentArrayList= new ArrayList<>();
    private ArrayList<TextField> priceArrayList = new ArrayList<>();



    public CreateInvoice(){

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

        btnpreview = new Button("PREVIEW");
        btnpreview.addStyleName("preview-button");
        btnpreview.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        btnpreview.addStyleName(ValoTheme.BUTTON_HUGE);
        btnpreview.addClickListener(this);


        seconVerticalLayoutd.addComponent(btnpreview);
        seconVerticalLayoutd.setComponentAlignment(btnpreview, Alignment.BOTTOM_RIGHT);

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


        }else if (clickEvent.getButton() == delBtn){
            int delBtnIndex = delBtnList.indexOf(clickEvent.getButton());

            Notification.show("del" + delBtnIndex, Notification.Type.TRAY_NOTIFICATION);

            addBtnList.remove(delBtnIndex);
            delBtnList.remove(delBtnIndex);
            firstVerticalLayout.removeComponent(componentArrayList.get(delBtnIndex));
            componentArrayList.remove(delBtnIndex);
            priceArrayList.remove(delBtnIndex);
            totalPrice();


        }else if (clickEvent.getButton() == euroBtn){
            String currency = " EURO";
            totalLable.setValue(totalSum + "&nbsp; &nbsp; " + currency);
            totalLable.setContentMode(ContentMode.HTML);
            popup.setVisible(false);

        }else if (clickEvent.getButton() == poundBtn){

        }else if (clickEvent.getButton() == dollarBtn){

        }else if (clickEvent.getButton() == btnpreview){


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
        taxTitle = new TextField("Tax  (%) ");
        totalLable = new Label("TOTAL");

        taxTitle.setValue("24");
        taxTitle.addValueChangeListener(this);
        taxTitle.setImmediate(true);

        taxTitle.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        taxTitle.addStyleName("textfield-background");
        subTitle.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        subTitle.addStyleName("textfield-background");


        totalLable.setCaption("TOTAL");
        totalLable.setStyleName(ValoTheme.LABEL_BOLD);
        totalLable.setStyleName(ValoTheme.LABEL_HUGE);



        euroBtn = new Button("Euro " , FontAwesome.EURO);
        poundBtn= new Button("Pound" , FontAwesome.GBP);
        dollarBtn= new Button("Dollar" , FontAwesome.DOLLAR);

        euroBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        poundBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        dollarBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        euroBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        poundBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        dollarBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);


        euroBtn.addStyleName("currency-buttons");
        poundBtn.addStyleName("currency-buttons");
        dollarBtn.addStyleName("currency-buttons");


        euroBtn.addClickListener(this);
        poundBtn.addClickListener(this);
        dollarBtn.addClickListener(this);




        VerticalLayout popupContent = new VerticalLayout();
        popupContent.addComponents(euroBtn,poundBtn,dollarBtn);


        popup = new PopupView("Change Currency", popupContent);
        popup.setHideOnMouseOut(false);





        totalFormLayout.addComponents(subTitle,taxTitle,totalLable, popup);

        layout.addComponents(totalFormLayout);

        // layout.setMargin(true);
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

        totalSum  =  String.valueOf(finalSum);
        currency = " EURO";
        totalLable.setValue(totalSum + "&nbsp; &nbsp; " + currency);
        totalLable.setContentMode(ContentMode.HTML);



    }


    public void navigate(String viewName){

        getUI().getNavigator().navigateTo(viewName);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
