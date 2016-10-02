package io.vaxly;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

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
        toTextField.setInputPrompt("ADD CUSTOMER");

        fromTextField = new TextField();
        fromTextField.setWidth(100,Unit.PERCENTAGE);
        fromTextField.setImmediate(true);
        fromTextField.setCaption("FROM");
        fromTextField.setInputPrompt("ADD COMPANY");

        setWidth(100, Unit.PERCENTAGE);
        setSpacing(true);
        setMargin(true);
        setStyleName("defaul-layout");
        addComponent(toTextField);
        addComponent(fromTextField);
    }
}
