package io.vaxly.mainUi;

import com.vaadin.data.Property;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.layouts.*;
import io.vaxly.models.Invoice;
import io.vaxly.models.Item;
import org.parse4j.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static io.vaxly.layouts.DetailLayout.dueDate;
import static io.vaxly.layouts.DetailLayout.invoiceField;
import static io.vaxly.layouts.DetailLayout.issueDate;

/**
 * Created by bkamau on 12.10.2016.
 */
public class CreateInvoice extends Panel implements View, Button.ClickListener, Property.ValueChangeListener {

    public static final String NAME = "CreateInvoice";
    Navigator navigator;
    private Panel  mainPaneL = new Panel();
    private VerticalLayout firstVerticalLayout;
    HorizontalLayout billz;
    private Button addBtn;

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

    private  String htmlStr ;
    private ArrayList<Button> addBtnList = new ArrayList<>();
    private ArrayList<Button> delBtnList = new ArrayList<>();
    private ArrayList<Component> componentArrayList= new ArrayList<>();
    private ArrayList<TextField> priceArrayList = new ArrayList<>();

    public static ArrayList<Item> itemsData = new ArrayList<Item>();
    private Component billshl;
    private HorizontalLayout bills;

    private  VerticalLayout mainHorizontalLayout;

    public static Invoice invoice = new Invoice();

    static HorizontalLayout topHorizontalLayout;
    public CreateInvoice(){

        mainHorizontalLayout = new VerticalLayout();
        mainHorizontalLayout.setSizeFull();

        topHorizontalLayout = new HorizontalLayout();
        topHorizontalLayout.setSizeFull();

        HorizontalLayout submitLayout = new SubmitLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();


        addUserButtons();

        mainHorizontalLayout.addComponent(topHorizontalLayout);
        mainHorizontalLayout.addComponents(horizontalLayout);
        mainHorizontalLayout.addComponent(submitLayout);
        mainHorizontalLayout.setComponentAlignment(topHorizontalLayout, Alignment.TOP_CENTER);
        mainHorizontalLayout.setComponentAlignment(submitLayout, Alignment.BOTTOM_CENTER);


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
        firstVerticalLayout.addComponent(new ContactsLayout());
        firstVerticalLayout.addComponent(new TitleLayout());

        VerticalLayout totalVerticalLayout = totalLayout();

        addDemBills();

        seconVerticalLayoutd.addComponent(totalVerticalLayout);
        seconVerticalLayoutd.setComponentAlignment(totalVerticalLayout, Alignment.MIDDLE_RIGHT);


        FooterLayout footerLayout = new FooterLayout();
        seconVerticalLayoutd.addComponent(footerLayout);
        seconVerticalLayoutd.setComponentAlignment(footerLayout, Alignment.BOTTOM_CENTER);

        mainPaneL.setContent(mainHorizontalLayout);
        mainPaneL.setResponsive(true);
        setContent(mainPaneL);
    }

    public static void addUserButtons(){
        if (ParseUser.currentUser != null){
            HorizontalLayout userLayout = new UserLayout();
            topHorizontalLayout.addComponent(userLayout);
            topHorizontalLayout.setComponentAlignment(userLayout, Alignment.TOP_CENTER);
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

     if (clickEvent.getButton() == addBtn) {

            addDemBills();

            delBtnList.get((delBtnList.size() - 2)).setStyleName("visible");
            delBtnList.get((delBtnList.size() - 2)).addStyleName(ValoTheme.BUTTON_BORDERLESS);
            int addBtnIndex = addBtnList.indexOf(addBtn);
            addBtnList.get(addBtnIndex - 1).setStyleName("invisible");

        }else {

            int delBtnIndex = delBtnList.indexOf(clickEvent.getButton());

            addBtnList.remove(delBtnIndex);
            delBtnList.remove(delBtnIndex);
            firstVerticalLayout.removeComponent(componentArrayList.get(delBtnIndex));
            componentArrayList.remove(delBtnIndex);
            priceArrayList.remove(delBtnIndex);
            totalPrice();

        }

    }

    private void addDemBills(){


        addBtn = new Button( FontAwesome.PLUS_SQUARE);
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

        billz = new ItemsLayout(qntytTextField, descTextField, amtTextField, priceTextField);

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

     /*
        *Method loops through the item rows
        * Then loops through the row textfields
        * Gets the values
        * Checks whether null
        * sets the values to an Item(model) object
        * adds the objects to an empty(cleared) arraylist
    */

    public void setitems() {
        int billzSize = billz.getComponentCount();
        int billzhlsize = componentArrayList.size();
        System.out.println("billsSize: " + billzSize);
        System.out.println("billsHorizontalLayout Size: " + billzhlsize);
        itemsData.clear();

        for (int i = 0; i < billzhlsize; i++) {
            Item realItem = new Item();
            Component componentlist = componentArrayList.get(i);
            HorizontalLayout hl = (HorizontalLayout) componentlist;
            billshl = hl.getComponent(1);
            bills = (HorizontalLayout) billshl;

            for (int j = 0; j < billzSize; j++) {
                Component comp = bills.getComponent(j);
                TextField txfield = (TextField) comp;
                String value = txfield.getValue();
                if(!value.isEmpty()) {

                    switch (j) {
                        case 0:
                            realItem.setName(value);
                            break;
                        case 1:
                            realItem.setQuantity(value);
                            break;
                        case 2:
                            realItem.setPrice(value);
                            break;
                        case 3:
                            realItem.setAmount(value);
                            break;
                    }
                }
                else{
                    System.out.println("Value (" + j + " " + i  + ") is null");
                    System.out.println("Value set to empty" );

                    switch (j){
                        case 1:
                            realItem.setQuantity("");
                            break;
                        case 2:
                            realItem.setPrice("");
                            break;
                        case 3:
                            realItem.setAmount("");
                            break;
                    }
                }

            }
            if((realItem.getName() == null))  {
                System.out.println("Name is null" );

            }
            else {
                System.out.println("Name is not null" + i);

                itemsData.add(realItem);
            }

        }

    }

    /*
        *Gets values from TextFields
        * Sets the values to invoice(model) object
        * prints values to log
     */

    private void setInvoiceDetails(){

        System.out.println("***** Get Values and set to models ******");

        String DATE_FORMAT = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String invoiceNo = invoiceField.getValue();
        int id = Integer.parseInt(invoiceNo);
        System.out.println("Invoice id: " + id);

        String issuedate = sdf.format(issueDate.getValue());
        System.out.println("issueDate: " + issuedate);

        String duedate = sdf.format(dueDate.getValue());
        System.out.println("dueDate: " + duedate);

        String subTotal = subTitle.getValue();
        System.out.println("subTotal: " + subTotal);

        String tax = taxTitle.getValue();
        System.out.println("tax: " + tax);

        String finalTotal = totalSum;
        System.out.println("finalTotal: " + finalTotal);

        String curr = currency;
        System.out.println("Currency: " + curr);

        System.out.println("************************");

        invoice.setDueDate(duedate);
        invoice.setIssueDate(issuedate);
        invoice.setId(id);
        invoice.setSubTotal(subTotal);
        invoice.setTax(tax);
        invoice.setTotal(finalTotal);
        invoice.setCurrency(curr);

    }


    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
        totalPrice();
        setitems();
        setInvoiceDetails();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
