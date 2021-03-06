package io.vaxly.mainUi;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.utils.Konstants;
import org.parse4j.ParseException;
import org.parse4j.ParseUser;

/**
 * Created by bkamau on 7.10.2016.
 */
public class LoginSignIn extends Window implements View, Button.ClickListener, Property.ValueChangeListener {

    private TabSheet tabSheet = new TabSheet();
    private VerticalLayout loginLayout = new VerticalLayout();

    private TextField loginEmailTextField = new TextField("Username", "");
    private TextField loginPasswdTextField = new TextField("Password", "");

    private TextField registerNameTextField = new TextField("Username", "");
    private TextField registerEmailTextField = new TextField("Email", "");
    private TextField registerPasswdTextField = new TextField("Password", "");
    private TextField confirmPasswordTextField = new TextField("Password", "");


    private Button loginButton = new Button("Login");


    public LoginSignIn() {



        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.addTab(loginLayout, "LOGIN");
        VerticalLayout signupLayout = new VerticalLayout();
        tabSheet.addTab(signupLayout, "SIGNUP");

        loginEmailTextField.setWidth(100.0f, Unit.PERCENTAGE);
        loginPasswdTextField.setWidth(100.0f, Unit.PERCENTAGE);

        // TODO: 22.10.2016 To  used for tests only
        loginEmailTextField.setValue("username");
        loginPasswdTextField.setValue("password");

        loginEmailTextField.addValueChangeListener(this);
        loginPasswdTextField.addValueChangeListener(this);
        loginEmailTextField.setImmediate(true);
        loginPasswdTextField.setImmediate(true);

        registerNameTextField.setWidth(100.0f, Unit.PERCENTAGE);
        registerPasswdTextField.setWidth(100.0f, Unit.PERCENTAGE);
        registerEmailTextField.setWidth(100.0f, Unit.PERCENTAGE);
        confirmPasswordTextField.setWidth(100.0f, Unit.PERCENTAGE);

        FormLayout loginFormLayout = new FormLayout();
        loginFormLayout.setSizeFull();
        loginFormLayout.setSpacing(true);
        loginFormLayout.setMargin(true);
        loginFormLayout.addComponents(loginEmailTextField,loginPasswdTextField);

        FormLayout signupFormLayout = new FormLayout();
        signupFormLayout.setSizeFull();
        signupFormLayout.setSpacing(true);
        signupFormLayout.setMargin(true);
        signupFormLayout.addComponents(registerNameTextField,registerEmailTextField,registerPasswdTextField,confirmPasswordTextField);

        loginButton.addClickListener(this);
        Button signupButton = new Button("Sign Up");
        signupButton.addClickListener(this);

        loginLayout.setMargin(true);
        loginLayout.setSpacing(true);
        loginLayout.addComponent(loginFormLayout);
        loginLayout.addComponent(loginButton);
        loginLayout.setComponentAlignment(loginButton, Alignment.BOTTOM_RIGHT);

        signupLayout.setMargin(true);
        signupLayout.setSpacing(true);
        signupLayout.addComponent(signupFormLayout);
        signupLayout.addComponent(signupButton);
        signupLayout.setComponentAlignment(signupButton, Alignment.BOTTOM_RIGHT);


        center();
        setModal(true);
        setSizeUndefined();
        //   setStyleName("login");
        setWidth(30.0f, Unit.PERCENTAGE);
        setResizable(false);
        setContent(tabSheet);

    }

    private void loginUser(String email, String password){
        if (loginEmailTextField.isEmpty() || loginPasswdTextField.isEmpty()){
            loginEmailTextField.setComponentError(new UserError("Can't be empty"));
            loginPasswdTextField.setComponentError(new UserError("Can't be empty"));
        }else {
            try {
                ParseUser.login(email,password );

                if (UI.getCurrent().getNavigator().getCurrentView().getClass() == Homepage.class) {
                    Konstants.printInfo(UI.getCurrent().getNavigator().getCurrentView().getClass().getName());
                    getUI().getNavigator().addView(CreateInvoice.NAME, new CreateInvoice());
                    navigate(CreateInvoice.NAME);
                    UI.getCurrent().removeWindow(this);
                }else if (UI.getCurrent().getNavigator().getCurrentView().getClass() == CreateInvoice.class){
                    CreateInvoice.addUserButtons();
                    UI.getCurrent().removeWindow(this);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Notification.show(e.getMessage());
            }
        }

    }

    private void signupUser(){
        if (!registerPasswdTextField.getValue().equals(confirmPasswordTextField.getValue())){
            Notification.show("Password does not match",  Notification.Type.WARNING_MESSAGE);
        }else {
            ParseUser user = new ParseUser();
            user.setUsername(registerNameTextField.getValue());
            user.setPassword(confirmPasswordTextField.getValue());
            user.setEmail(registerEmailTextField.getValue());

            try {
                user.signUp();
                tabSheet.setSelectedTab(loginLayout);
            } catch (ParseException | IllegalArgumentException e1 ) {
                e1.printStackTrace();
                Notification.show(e1.getMessage(), Notification.Type.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() == loginButton){
            loginUser(loginEmailTextField.getValue(),loginPasswdTextField.getValue());
        }else {
            signupUser();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @Override
    public void valueChange(Property.ValueChangeEvent valueChangeEvent) {

    }

    private void navigate(String viewName){
        getUI().getNavigator().navigateTo(viewName);
    }
}
