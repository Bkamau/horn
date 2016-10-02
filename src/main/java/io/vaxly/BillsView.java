package io.vaxly;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 2.10.2016.
 */
public class BillsView extends HorizontalLayout {



    TextField itemTextField = new TextField();
    TextField descTextField = new TextField();
    TextField amtTextField = new TextField();
    TextField priceTextField = new TextField();

    Button addBtn = new Button("Back", FontAwesome.PLUS_SQUARE);
    Button delBtn = new Button("Back", FontAwesome.MINUS_SQUARE);

    public BillsView() {
        super();




        addBtn.setWidth(100, Unit.PERCENTAGE);
        addBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        addBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        delBtn.setWidth(100, Unit.PERCENTAGE);
        delBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        delBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        itemTextField.setInputPrompt("Enter product/service description");
        itemTextField.setWidth(100, Unit.PERCENTAGE);
        descTextField.setWidth(100, Unit.PERCENTAGE);
        amtTextField.setWidth(100, Unit.PERCENTAGE);
        priceTextField.setWidth(100, Unit.PERCENTAGE);



        addComponents(addBtn,itemTextField,descTextField,amtTextField,priceTextField,delBtn);
        setExpandRatio(itemTextField, 5);
        setExpandRatio(descTextField,15);
        setExpandRatio(amtTextField,5);
        setExpandRatio(priceTextField,5);
        setExpandRatio(addBtn,1);
        setExpandRatio(delBtn,1);

        setMargin(false);
        setSpacing(true);
        setWidth(100,Unit.PERCENTAGE);
        setStyleName("bills");
    }


}
