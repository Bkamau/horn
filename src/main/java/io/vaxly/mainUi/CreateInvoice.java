package io.vaxly.mainUi;

import com.vaadin.data.Property;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.layouts.*;
import io.vaxly.models.User;
import io.vaxly.utils.HtmlGenerator;
import io.vaxly.utils.Konstants;
import io.vaxly.utils.PdfGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.vaxly.layouts.ContactsLayout.company;
import static io.vaxly.layouts.ContactsLayout.customer;

/**
 * Created by bkamau on 12.10.2016.
 */
public class CreateInvoice extends Panel implements View, Button.ClickListener, Property.ValueChangeListener {

    public static final String NAME = "CreateInvoice";
    Navigator navigator;
    private Panel  mainPaneL = new Panel();
    private VerticalLayout firstVerticalLayout;
    private Button addBtn;

    private Button euroBtn ;
    private Button poundBtn ;
    private Button dollarBtn;
    private Button btnPreview;
    private Button btnSave;

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


    public CreateInvoice(){

        VerticalLayout mainHorizontalLayout = new VerticalLayout();
        mainHorizontalLayout.setSizeFull();

        HorizontalLayout userLayout = new UserLayout();
        HorizontalLayout submitLayout = new SubmitLayout();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        mainHorizontalLayout.addComponent(userLayout);
        mainHorizontalLayout.addComponents(horizontalLayout);
        mainHorizontalLayout.addComponent(submitLayout);
        mainHorizontalLayout.setComponentAlignment(userLayout, Alignment.TOP_CENTER);
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

        btnPreview = previewBtn();
        seconVerticalLayoutd.addComponent(btnPreview);
        seconVerticalLayoutd.setComponentAlignment(btnPreview, Alignment.BOTTOM_RIGHT);

        btnSave = saveBtn();
        seconVerticalLayoutd.addComponent(btnSave);
        seconVerticalLayoutd.setComponentAlignment(btnSave, Alignment.BOTTOM_RIGHT);


        FooterLayout footerLayout = new FooterLayout();
        seconVerticalLayoutd.addComponent(footerLayout);
        seconVerticalLayoutd.setComponentAlignment(footerLayout, Alignment.BOTTOM_CENTER);

        mainPaneL.setContent(mainHorizontalLayout);
        mainPaneL.setResponsive(true);
        setContent(mainPaneL);
    }



    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        if (clickEvent.getButton() == btnPreview){
            generatePdf();

        }else if (clickEvent.getButton() == btnSave){
            Konstants.printInfo("saved...");
        } else if (clickEvent.getButton() == addBtn) {

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

        HorizontalLayout billz = new ItemsLayout(qntytTextField, descTextField, amtTextField, priceTextField);

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

    private void generatePdf() {

        String outputFilePath = "output/pdf/invoice.pdf";
        String tampleFile = "src/main/resources/template.html";


        Map<String,Object> variables = new HashMap<String,Object>();

        List<User> users = createUserList();

        variables.put("users",users);
        variables.put("company", company);
        variables.put("customer", customer);

        try {
            new File(outputFilePath).delete();
            Konstants.printInfo("File Deleted ..");
        }catch (UnknownError ue){
            ue.printStackTrace();
        }

        try {
            htmlStr = HtmlGenerator.generate(tampleFile, variables);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            PdfGenerator.generate(htmlStr, new FileOutputStream(outputFilePath));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void showPreview(){

        Konstants.printInfo("Showing preview ..");
        Resource resource = new FileResource(new File("output/pdf/invoice.pdf"));
        BrowserWindowOpener opener = new BrowserWindowOpener(resource);
        opener.extend(btnPreview);

    }

    private Button previewBtn(){

        Button button = new Button("PREVIEW");
        button.addStyleName("preview-button");
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addStyleName(ValoTheme.BUTTON_HUGE);
        button.addClickListener(this);

        return button;
    }

    private Button saveBtn(){

        Button button = new Button("Save");
        button.addStyleName("save-button");
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addStyleName(ValoTheme.BUTTON_HUGE);
        button.addClickListener(this);

        return button;
    }

    private static List<User> createUserList() {
        User user1 = createUser(1, "marine core", 12);
        User user2 = createUser(2, "benito", 34);
        User user3 = createUser(3, "becccccccccccccccccccccnso", 26);
        User user4 = createUser(3, "beccccccccccccccccccccccccccccn", 5);
        User user5 = createUser(3, "marggggggggggggggggggggggggine", 265);

        User user14 = createUser(3, "beccccccccccccccccccccccccccccn", 5);
        User user15 = createUser(3, "marggggggggggggggggggggggggine", 265);


        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        users.add(user14);
        users.add(user15);


        return users;
    }

    private static User createUser(long id, String username, int age) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAge(age);
        return user;
    }

    public void navigate(String viewName){

        getUI().getNavigator().navigateTo(viewName);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
