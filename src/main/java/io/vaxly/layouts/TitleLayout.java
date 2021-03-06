package io.vaxly.layouts;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Created by bkamau on 2.10.2016.
 */
public class TitleLayout extends HorizontalLayout{

    Label qntyLable = new Label("QUANTITY");
    Label descLable = new Label("DESCRIPTION");
    Label amountLable = new Label("  AMOUNT");
    Label priceLable = new Label("  PRICE");
    Label noText = new Label("  ");
    Label noText1 = new Label("  ");

    public TitleLayout() {


        addComponents(noText,noText,descLable,qntyLable,priceLable,amountLable,noText1);
        setExpandRatio(qntyLable,5);
        setExpandRatio(descLable,15);
        setExpandRatio(amountLable,5);
        setExpandRatio(priceLable,5);
        setExpandRatio(noText, 2);
        setExpandRatio(noText1, 2);

        setSpacing(true);
        setMargin(false);
        setWidth(100, Unit.PERCENTAGE);
        setStyleName("title-layout");

    }

}
