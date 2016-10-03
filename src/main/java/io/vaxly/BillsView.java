package io.vaxly;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

/**
 * Created by bkamau on 2.10.2016.
 */
public class BillsView extends HorizontalLayout {

    TextField qntytTextField = new TextField();
    TextField descTextField = new TextField();
    TextField amtTextField = new TextField();
    TextField priceTextField = new TextField();


    public BillsView( ) {
        super();

        descTextField.setInputPrompt("Enter product/service description");
        qntytTextField.setWidth(100, Unit.PERCENTAGE);
        descTextField.setWidth(100, Unit.PERCENTAGE);
        amtTextField.setWidth(100, Unit.PERCENTAGE);
        priceTextField.setWidth(100, Unit.PERCENTAGE);

        addComponents(descTextField,qntytTextField,amtTextField,priceTextField);
        setExpandRatio(qntytTextField, 5);
        setExpandRatio(descTextField,15);
        setExpandRatio(amtTextField,5);
        setExpandRatio(priceTextField,5);

        setMargin(false);
        setSpacing(true);
        setWidth(100,Unit.PERCENTAGE);
       // setStyleName("bills");
    }



}
