package io.vaxly.mainUi;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.parse4j.ParseException;
import org.parse4j.ParseUser;

/**
 * Created by bkamau on 7.10.2016.
 */
public class LoginSignin extends Window implements Button.ClickListener {

    TabSheet tabSheet = new TabSheet();
    VerticalLayout loginLayout = new VerticalLayout();
    VerticalLayout signupLayout = new VerticalLayout();

    FormLayout loginFormLayout = new FormLayout();
    FormLayout signupFormLayout = new FormLayout();

    TextField loginEmailTextField = new TextField("Email", "");
    TextField loginPasswdTextField = new TextField("Password", "");

    TextField registerNameTextField = new TextField("Username", "");
    TextField registerEmailTextField = new TextField("Email", "");
    TextField registerPasswdTextField = new TextField("Password", "");
    TextField confirmPasswordTextField = new TextField("Password", "");


    Button loginButton = new Button("Login");
    Button signupButton = new Button("Sign Up");


    public LoginSignin() {


        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.addTab(loginLayout, "LOGIN");
        tabSheet.addTab(signupLayout, "SIGNUP");


        loginEmailTextField.setWidth(100.0f, Unit.PERCENTAGE);
        loginPasswdTextField.setWidth(100.0f, Unit.PERCENTAGE);

        registerNameTextField.setWidth(100.0f, Unit.PERCENTAGE);
        registerPasswdTextField.setWidth(100.0f, Unit.PERCENTAGE);
        registerEmailTextField.setWidth(100.0f, Unit.PERCENTAGE);
        confirmPasswordTextField.setWidth(100.0f, Unit.PERCENTAGE);


        loginFormLayout.setSizeFull();
        loginFormLayout.setSpacing(true);
        loginFormLayout.setMargin(true);
        loginFormLayout.addComponents(loginEmailTextField,loginPasswdTextField);

        signupFormLayout.setSizeFull();
        signupFormLayout.setSpacing(true);
        signupFormLayout.setMargin(true);
        signupFormLayout.addComponents(registerNameTextField,registerEmailTextField,registerPasswdTextField,confirmPasswordTextField);

        loginButton.addClickListener(this);
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
        try {
            ParseUser.login(email,password );
            UI.getCurrent().removeWindow(this);
        } catch (ParseException e) {
            e.printStackTrace();
            Notification.show(e.getMessage());
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
}
