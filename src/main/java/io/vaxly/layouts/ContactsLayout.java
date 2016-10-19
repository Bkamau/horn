package io.vaxly.layouts;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 2.10.2016.
 */
@PreserveOnRefresh
public class ContactsLayout extends HorizontalLayout implements LayoutEvents.LayoutClickListener, Button.ClickListener{

    private TextField toTextField;
    private TextField fromTextField;

    private TextField nameTextField;
    private TextField customerNameTF;

    private Button companydBtn;
    private  Button saveCustomerBtn;

    private Window companyWindow = myCompanyWindow();
    private Window customerWindow = myCustomerWindow();

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
        addComponent(toTextField);
        addComponent(fromTextField);
        addLayoutClickListener(this);
    }


    private Window myCompanyWindow (){

        Window window = new Window();
        VerticalLayout layout = new VerticalLayout();

        nameTextField = new TextField("Name");
        nameTextField.setWidth(230.0f, Unit.PIXELS);
        nameTextField.focus();
        TextField addressTextField = new TextField("Address");
        addressTextField.setWidth(230.0f, Unit.PIXELS);
        TextField zipCodeTextField = new TextField("Zip Code");
        zipCodeTextField.setWidth(230.0f, Unit.PIXELS);
        TextField cityTextField = new TextField("City");
        cityTextField.setWidth(230.0f, Unit.PIXELS);
        TextField companyNoTextField = new TextField("Company No.");
        companyNoTextField.setWidth(230.0f, Unit.PIXELS);
        TextField bankDetailsTextField = new TextField("Bank Account");
        bankDetailsTextField.setWidth(230.0f, Unit.PIXELS);
        TextField bicTextField = new TextField("Bank BIC");
        bicTextField.setWidth(230.0f, Unit.PIXELS);

        companydBtn = new Button("Save");
        companydBtn.setWidth(230.0f, Unit.PIXELS);
        companydBtn.addStyleName("preview-button");
        companydBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        companydBtn.addClickListener(this);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponents(nameTextField, addressTextField, zipCodeTextField, cityTextField, companyNoTextField,
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

       TextField customerAddressTF = new TextField("Address");
       customerAddressTF.setWidth(230.0f, Unit.PIXELS);

       TextField customerZipTF = new TextField("Zip Code");
       customerZipTF.setWidth(230.0f, Unit.PIXELS);

       TextField customerCityTF = new TextField("City");
       customerCityTF.setWidth(230.0f, Unit.PIXELS);

       saveCustomerBtn = new Button("Save");
       saveCustomerBtn.setWidth(230.0f, Unit.PIXELS);
       saveCustomerBtn.addStyleName("preview-button");
       saveCustomerBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
       saveCustomerBtn.addClickListener(this);

       formLayout.addComponents(customerNameTF,customerAddressTF,customerZipTF,customerCityTF,saveCustomerBtn);

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
        if (layoutClickEvent.getClickedComponent() == toTextField){
            UI.getCurrent().addWindow(companyWindow);
        }else if (layoutClickEvent.getClickedComponent() == fromTextField){
            UI.getCurrent().addWindow(customerWindow);
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getComponent() == companydBtn){
            if (!nameTextField.isEmpty()){
                toTextField.setValue(nameTextField.getValue());
                toTextField.addStyleName(ValoTheme.LABEL_LARGE);
                toTextField.addStyleName(ValoTheme.LABEL_BOLD);
            }
            UI.getCurrent().removeWindow(companyWindow);
        }else if (clickEvent.getComponent() == saveCustomerBtn){
            if (!customerNameTF.isEmpty()){
                fromTextField.setValue(customerNameTF.getValue());
                fromTextField.addStyleName(ValoTheme.LABEL_LARGE);
                fromTextField.addStyleName(ValoTheme.LABEL_BOLD);
            }
            UI.getCurrent().removeWindow(customerWindow);
        }

    }
}

