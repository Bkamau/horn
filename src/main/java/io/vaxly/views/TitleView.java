package io.vaxly.views;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Created by bkamau on 2.10.2016.
 */
public class TitleView extends HorizontalLayout{

    Label qntyLable = new Label("QUANTITY");
    Label descLable = new Label("DESCRIPTION");
    Label amountLable = new Label("  AMOUNT");
    Label priceLable = new Label("  PRICE");
    Label noText = new Label("  ");
    Label noText1 = new Label("  ");

    public TitleView() {


        addComponents(noText,noText,descLable,qntyLable,amountLable,priceLable,noText1);
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
