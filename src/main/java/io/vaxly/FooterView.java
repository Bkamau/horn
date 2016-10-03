package io.vaxly;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;

/**
 * Created by bkamau on 2.10.2016.
 */
public class FooterView extends HorizontalLayout {

    TextArea terms;
    TextArea notes;

    public FooterView() {

        terms = new TextArea();
        terms.setWidth(100, Unit.PERCENTAGE);
        terms.setImmediate(true);
        terms.setCaption("ADD TERMS");

        notes = new TextArea();
        notes.setWidth(100,Unit.PERCENTAGE);
        notes.setImmediate(true);
        notes.setCaption("NOTES");

        setWidth(100, Unit.PERCENTAGE);
        setSpacing(true);
        setMargin(false);
        setStyleName("address-layout");
        addComponent(terms);
        addComponent(notes);
    }
}
