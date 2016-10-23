package io.vaxly.layouts;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 2.10.2016.
 */
public class FooterLayout extends HorizontalLayout {

    TextField terms;
    TextField notes;


    public FooterLayout( ) {

        terms =createTextField("TERMS");
        notes = createTextField("NOTES");

        setWidth(100, Unit.PERCENTAGE);
        setSpacing(true);
        setStyleName("address-layout");
        addComponent(terms);
        addComponent(notes);
    }

    private TextField createTextField(String caption){
        TextField textField = new TextField();
        textField.setWidth(100, Unit.PERCENTAGE);
        textField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        textField.addStyleName("textfield-background");
        textField.setImmediate(true);
        textField.setCaption(caption);
        return textField;
    }
}