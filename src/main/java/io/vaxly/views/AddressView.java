package io.vaxly.views;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 2.10.2016.
 */
public class AddressView extends HorizontalLayout {

    TextField toTextField;
    TextField fromTextField;

    public AddressView() {

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
    }
}
