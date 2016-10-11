package io.vaxly;

import com.vaadin.ui.Button;

/**
 * Created by bkamau on 11.10.2016.
 */
public class ButtonsView  {

    private Button bPreview;

    public ButtonsView() {
    }

    public Button previewButton (){

        bPreview = new Button("PREVIEW");

        return bPreview;
    }
}
