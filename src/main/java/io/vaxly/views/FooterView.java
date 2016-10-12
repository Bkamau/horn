package io.vaxly.views;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 2.10.2016.
 */
public class FooterView extends HorizontalLayout {

    TextField terms;
    TextField notes;


    public FooterView( ) {

        terms = new TextField();
        terms.setWidth(100, Unit.PERCENTAGE);
        terms.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        terms.addStyleName("textfield-background");
        terms.setImmediate(true);
        terms.setCaption("ADD TERMS");

        notes = new TextField();
        notes.setWidth(100,Unit.PERCENTAGE);
        notes.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        notes.addStyleName("textfield-background");
        notes.setImmediate(true);
        notes.setCaption("NOTES");



        setWidth(100, Unit.PERCENTAGE);
        setSpacing(true);
        setStyleName("address-layout");
        addComponent(terms);
        addComponent(notes);
    }
}
