package io.vaxly.views;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by bkamau on 3.10.2016.
 */
public class ButtonView extends HorizontalLayout {
    Button print;
    Button save;
    public ButtonView() {
        print = new Button("Print");
        save = new Button("Save");


        addComponents(print, save);
        setComponentAlignment(print, Alignment.BOTTOM_CENTER);
        setComponentAlignment(save, Alignment.BOTTOM_CENTER);

    }
}
