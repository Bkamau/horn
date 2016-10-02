package io.vaxly;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Created by bkamau on 2.10.2016.
 */
public class TitleView extends HorizontalLayout{

    Label itemLabel = new Label("ITEM");
    Label descLable = new Label("DESCRIPTION");
    Label amountLable = new Label("AMOUNT");
    Label priceLable = new Label("PRICE");

    public TitleView() {


        setWidth(100, Unit.PERCENTAGE);
        addComponents(itemLabel,descLable,amountLable,priceLable);
        setExpandRatio(itemLabel,1);
        setExpandRatio(descLable,3);
        setExpandRatio(amountLable,1);
        setExpandRatio(priceLable,1);
        setSpacing(true);
        setMargin(true);





    }

}
