package io.vaxly.layouts;

import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 2.10.2016.
 */
public class ItemsLayout extends HorizontalLayout {

    double quantity,amount;


    public ItemsLayout(TextField qntytTextField, TextField descTextField , TextField amtTextField , TextField priceTextField) {
        super();

        descTextField.setInputPrompt("Enter product/service description");
        qntytTextField.setWidth(100, Unit.PERCENTAGE);
        descTextField.setWidth(100, Unit.PERCENTAGE);
        amtTextField.setWidth(100, Unit.PERCENTAGE);
        priceTextField.setWidth(100, Unit.PERCENTAGE);

        qntytTextField.setImmediate(true);
        amtTextField.setImmediate(true);
        priceTextField.setImmediate(true);

        descTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        descTextField.addStyleName("textfield-background");
        descTextField.focus();

        qntytTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        qntytTextField.addStyleName("textfield-background");

        amtTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        amtTextField.addStyleName("textfield-background");

        priceTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        priceTextField.addStyleName("textfield-background");

        qntytTextField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (qntytTextField.isEmpty()){
                    quantity = 0;

                }else {
                    quantity = Double.parseDouble(qntytTextField.getValue());
                }

                priceTextField.setValue(String.valueOf(quantity*amount));
            }
        });

        amtTextField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (amtTextField.isEmpty()){
                    amount = 0;
                }else {
                    amount = Double.parseDouble(amtTextField.getValue());
                }

                priceTextField.setValue(String.valueOf(quantity*amount));
            }
        });



        addComponents(descTextField,qntytTextField,amtTextField,priceTextField);
        setExpandRatio(qntytTextField, 5);
        setExpandRatio(descTextField,15);
        setExpandRatio(amtTextField,5);
        setExpandRatio(priceTextField,5);

        setMargin(false);
        setSpacing(true);
        setWidth(100,Unit.PERCENTAGE);
        //setStyleName("bills");
    }



}
