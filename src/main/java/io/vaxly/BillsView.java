package io.vaxly;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;

/**
 * Created by bkamau on 2.10.2016.
 */
public class BillsView extends HorizontalLayout {



    TextField itemTextField = new TextField();
    TextField descTextField = new TextField();
    TextField amtTextField = new TextField();
    TextField priceTextField = new TextField();


    String basepath = VaadinService.getCurrent()
            .getBaseDirectory().getAbsolutePath();

    Resource delResource = new ThemeResource("../images/delete.png");
    Image addImage = new Image("", delResource);
    Image delImage = new Image("", delResource);

    public BillsView() {
        super();

        addImage.setWidth(5, Unit.PERCENTAGE);
        delImage.setWidth(5, Unit.PERCENTAGE);
        itemTextField.setInputPrompt("Enter product/service description");
        itemTextField.setWidth(100, Unit.PERCENTAGE);
        descTextField.setWidth(100, Unit.PERCENTAGE);
        amtTextField.setWidth(100, Unit.PERCENTAGE);
        priceTextField.setWidth(100, Unit.PERCENTAGE);

        setMargin(false);
        setSpacing(true);
        setWidth(100,Unit.PERCENTAGE);
        setStyleName("bills");

        addComponents(addImage,itemTextField,descTextField,amtTextField,priceTextField,delImage);
        setExpandRatio(itemTextField, 5);
        setExpandRatio(descTextField,15);
        setExpandRatio(amtTextField,5);
        setExpandRatio(priceTextField,5);
        setExpandRatio(addImage,1);
        setExpandRatio(delImage,1);
    }


}
