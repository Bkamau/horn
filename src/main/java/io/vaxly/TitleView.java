package io.vaxly;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Created by bkamau on 2.10.2016.
 */
public class TitleView extends HorizontalLayout{

    Label itemLabel = new Label("ITEM");
    Label descLable = new Label("DESCRIPTION");
    Label amountLable = new Label("  AMOUNT");
    Label priceLable = new Label("  PRICE");
    Label noText = new Label("  ");
    Label noText1 = new Label("  ");

    public TitleView() {


        addComponents(noText,itemLabel,descLable,amountLable,priceLable,noText1);
        setExpandRatio(itemLabel,5);
        setExpandRatio(descLable,15);
        setExpandRatio(amountLable,5);
        setExpandRatio(priceLable,5);
        setExpandRatio(noText, 1);
        setExpandRatio(noText1, 1);

        setSpacing(true);
        setMargin(false);
        setWidth(100, Unit.PERCENTAGE);
        setStyleName("title-layout");

    }

}
