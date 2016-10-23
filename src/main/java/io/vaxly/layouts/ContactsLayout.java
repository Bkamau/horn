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

    private Window companyWindow  = myCompanyWindow();
    private Window customerWindow = myCustomerWindow();

    public static Company company;
    public static Customer customer;

    public ContactsLayout() {

        toTextField   = mainFields("TO", "ADD CUSTOMER");
        fromTextField = mainFields("FROM", "ADD COMPANY");

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

        nameTextField        = createFields("Name");
        nameTextField.focus();
        addressTextField     = createFields("Address");
        zipCodeTextField     = createFields("Zip Code");
        cityTextField        = createFields("City");
        companyNoTextField   = createFields("Company No.");
        bankDetailsTextField = createFields("Bank Account");
        bicTextField         = createFields("Bank BIC");
        countryTextField     = createFields("Country");
        companydBtn          = createButtons();


        FormLayout formLayout = new FormLayout();
        formLayout.addComponents(nameTextField, addressTextField, zipCodeTextField, cityTextField, countryTextField, companyNoTextField,
                bankDetailsTextField, bicTextField, companydBtn);

        VerticalLayout layout =  createVerticalLayout(formLayout);

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

        customerNameTF    = createFields("Name");
        customerNameTF.focus();
        customerAddressTF = createFields("Address");
        customerZipTF     = createFields("Zip Code");
        customerCityTF    = createFields("City");
        customerCountryTF = createFields("Country");
        saveCustomerBtn   = createButtons();

        formLayout.addComponents(customerNameTF,customerAddressTF,customerZipTF,customerCityTF, customerCountryTF,saveCustomerBtn);

        VerticalLayout layout =  createVerticalLayout(formLayout);

        window.setContent(layout);
        window.setResizable(false);
        window.addStyleName("corners");
        window.setPosition(960,320);
        window.setIcon(new ThemeResource("images/client.png"));
        return window;
    }

    private VerticalLayout createVerticalLayout(Component component){
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setWidth(400, Unit.PIXELS);
        layout.addComponent(component);
        return layout;
    }

    private TextField mainFields(String caption, String inprompt){
        TextField textField = new TextField();
        textField.setWidth(100,Unit.PERCENTAGE);
        textField.setImmediate(true);
        textField.setCaption(caption);
        textField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);;
        textField.addStyleName("textfield-background");
        textField.setInputPrompt(inprompt);

        return textField;
    }

    private TextField createFields(String name){
        TextField textField = new TextField(name);
        textField.setWidth(230.0f, Unit.PIXELS);
        return textField;
    }

    private Button createButtons(){
        Button button = new Button("Save");
        button.setWidth(230.0f, Unit.PIXELS);
        button.addStyleName("preview-button");
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addClickListener(this);
        return button;
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
