package io.vaxly;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 10.10.2016.
 */
public class TotalView  extends VerticalLayout {

    FormLayout totalFormLayout = new FormLayout();

    TextField subTitle = new TextField("Sub Total");
    TextField taxTitle = new TextField("Tax");
    Label totalLable = new Label("TOTAL");

    public TotalView() {

        taxTitle.setInputPrompt("                                    %");

        totalLable.setValue(" 000");
        totalLable.setCaption("TOTAL");
        totalLable.setStyleName(ValoTheme.LABEL_BOLD);
        totalLable.setStyleName(ValoTheme.LABEL_HUGE);
        totalFormLayout.addComponents(subTitle,taxTitle,totalLable);

        addComponents(totalFormLayout);

        setMargin(true);
        setSpacing(true);
        setWidth(35, Unit.PERCENTAGE);
    }
}
