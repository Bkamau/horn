package io.vaxly.layouts;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.models.Company;
import io.vaxly.models.Customer;

/**
 * Created by bkamau on 2.10.2016.
 */
@PreserveOnRefresh
public class ContactsLayout extends HorizontalLayout implements LayoutEvents.LayoutClickListener, Button.ClickListener{

    private TextField toTextField;
    private TextField fromTextField;

    private TextField nameTextField;
    private TextField customerNameTF;

    private TextField addressTextField;
    private TextField zipCodeTextField;
    private TextField cityTextField;
    private TextField companyNoTextField;
    private TextField bankDetailsTextField;
    private TextField bicTextField;
    private TextField countryTextField;
    private TextField customerAddressTF;
    private TextField customerZipTF;
    private TextField customerCityTF;
    private TextField customerCountryTF;

    private Button companydBtn;
    private  Button saveCustomerBtn;

    private Window companyWindow = myCompanyWindow();
    private Window customerWindow = myCustomerWindow();

    public static Company company;
    public static Customer customer;

    public ContactsLayout() {

        toTextField = new TextField();
        toTextField.setWidth(100, Unit.PERCENTAGE);
        toTextField.setImmediate(true);
        toTextField.setCaption("TO");
        toTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        toTextField.addStyleName("textfield-background");
        toTextField.setInputPrompt("ADD CUSTOMER");


        fromTextField = new TextField();
        fromTextField.setWidth(100,Unit.PERCENTAGE);
        fromTextField.setImmediate(true);
        fromTextField.setCaption("FROM");
        fromTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);;
        fromTextField.addStyleName("textfield-background");
        fromTextField.setInputPrompt("ADD COMPANY");

        setWidth(100, Unit.PERCENTAGE);
        setSpacing(true);
        setMargin(false);
        setStyleName("address-layout");
        addComponent(fromTextField);
        addComponent(toTextField);
        addLayoutClickListener(this);
    }


    private Window myCompanyWindow (){

        Window window = new Window();
        VerticalLayout layout = new VerticalLayout();

        nameTextField = new TextField("Name");
        nameTextField.setWidth(230.0f, Unit.PIXELS);
        nameTextField.focus();
        addressTextField = new TextField("Address");
        addressTextField.setWidth(230.0f, Unit.PIXELS);
        zipCodeTextField = new TextField("Zip Code");
        zipCodeTextField.setWidth(230.0f, Unit.PIXELS);
        cityTextField = new TextField("City");
        cityTextField.setWidth(230.0f, Unit.PIXELS);
        companyNoTextField = new TextField("Company No.");
        companyNoTextField.setWidth(230.0f, Unit.PIXELS);
        bankDetailsTextField = new TextField("Bank Account");
        bankDetailsTextField.setWidth(230.0f, Unit.PIXELS);
        bicTextField = new TextField("Bank BIC");
        bicTextField.setWidth(230.0f, Unit.PIXELS);
        countryTextField = new TextField("Country");
        countryTextField.setWidth(230.0f, Unit.PIXELS);

        companydBtn = new Button("Save");
        companydBtn.setWidth(230.0f, Unit.PIXELS);
        companydBtn.addStyleName("preview-button");
        companydBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        companydBtn.addClickListener(this);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponents(nameTextField, addressTextField, zipCodeTextField, cityTextField, countryTextField, companyNoTextField,
                bankDetailsTextField, bicTextField, companydBtn);

        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setWidth(400, Unit.PIXELS);
        layout.addComponent(formLayout);

        window.setContent(layout);
        window.setResizable(false);
        window.addStyleName("corners");
        window.setPosition(425,320);
        window.setIcon(new ThemeResource("images/company.png"));
        return window;
    }

   private Window myCustomerWindow(){
       Window window = new Window();
       FormLayout formLayout = new FormLayout();
       VerticalLayout layout = new VerticalLayout();

       customerNameTF = new TextField("Name");
       customerNameTF.setWidth(230.0f, Unit.PIXELS);
       customerNameTF.focus();
       customerAddressTF = new TextField("Address");
       customerAddressTF.setWidth(230.0f, Unit.PIXELS);

       customerZipTF = new TextField("Zip Code");
       customerZipTF.setWidth(230.0f, Unit.PIXELS);

       customerCityTF = new TextField("City");
       customerCityTF.setWidth(230.0f, Unit.PIXELS);

       customerCountryTF = new TextField("Country");
       customerCountryTF.setWidth(230.0f, Unit.PIXELS);

       saveCustomerBtn = new Button("Save");
       saveCustomerBtn.setWidth(230.0f, Unit.PIXELS);
       saveCustomerBtn.addStyleName("preview-button");
       saveCustomerBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
       saveCustomerBtn.addClickListener(this);

       formLayout.addComponents(customerNameTF,customerAddressTF,customerZipTF,customerCityTF, customerCountryTF,saveCustomerBtn);

       layout.setSpacing(true);
       layout.setMargin(true);
       layout.setWidth(400, Unit.PIXELS);
       layout.addComponent(formLayout);


       window.setContent(layout);
       window.setResizable(false);
       window.addStyleName("corners");
       window.setPosition(960,320);
       window.setIcon(new ThemeResource("images/client.png"));
       return window;
   }

    @Override
    public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
        if (layoutClickEvent.getClickedComponent() == fromTextField){
            UI.getCurrent().addWindow(companyWindow);
        }else if (layoutClickEvent.getClickedComponent() == toTextField){
            UI.getCurrent().addWindow(customerWindow);
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getComponent() == companydBtn){
            company = new Company();
            if (!nameTextField.isEmpty()){
                fromTextField.setValue(nameTextField.getValue());
                fromTextField.addStyleName(ValoTheme.LABEL_LARGE);
                fromTextField.addStyleName(ValoTheme.LABEL_BOLD);

                company.setName(nameTextField.getValue());
                company.setAddress(addressTextField.getValue());
                company.setZip(zipCodeTextField.getValue() + " ");
                company.setCity(cityTextField.getValue());
                company.setCompanyNumber(companyNoTextField.getValue());
                company.setBankAcc(bankDetailsTextField.getValue() + " ");
                company.setBankBic(bicTextField.getValue());
                company.setCountry(countryTextField.getValue());

                UI.getCurrent().removeWindow(companyWindow);

            }else {
                nameTextField.setComponentError(new UserError("This Field is needed"));
            }



        }else if (clickEvent.getComponent() == saveCustomerBtn){
            customer = new Customer();
            if (!customerNameTF.isEmpty()){
                toTextField.setValue(customerNameTF.getValue());
                toTextField.addStyleName(ValoTheme.LABEL_LARGE);
                toTextField.addStyleName(ValoTheme.LABEL_BOLD);

                customer.setName(customerNameTF.getValue());
                customer.setAddress(customerAddressTF.getValue());
                customer.setZip(customerZipTF.getValue() + " ");
                customer.setCity(customerCityTF.getValue());
                customer.setCountry(customerCountryTF.getValue());

                UI.getCurrent().removeWindow(customerWindow);

            }else {
                customerNameTF.setComponentError(new UserError("This Field is needed"));
            }



        }

    }


}

