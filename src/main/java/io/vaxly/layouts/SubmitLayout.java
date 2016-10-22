package io.vaxly.layouts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.mainUi.CreateInvoice;
import io.vaxly.models.User;
import io.vaxly.utils.HtmlGenerator;
import io.vaxly.utils.Konstants;
import io.vaxly.utils.PdfGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.vaxly.layouts.ContactsLayout.company;
import static io.vaxly.layouts.ContactsLayout.customer;
import static io.vaxly.mainUi.CreateInvoice.itemsData;
import static io.vaxly.mainUi.CreateInvoice.invoice;

/**
 * Created by bkamau on 20.10.2016.
 */
public class SubmitLayout extends HorizontalLayout implements View, Button.ClickListener {

    private  static  String outputFilePath = "output/pdf/invoice.pdf";

    private Button downloadBtn;
    private Button saveBtn;
    private Button previewBtn;
    private Button sendBtn;



    public SubmitLayout( ){

        downloadBtn = new Button("Download PDF");
        downloadBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        downloadBtn.addStyleName(ValoTheme.LABEL_LARGE);
        downloadBtn.addStyleName("user-button");

        saveBtn = new Button("Save");
        saveBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        saveBtn.addStyleName(ValoTheme.LABEL_LARGE);
        saveBtn.addStyleName("user-button");

        previewBtn = new Button("Preview");
        previewBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        previewBtn.addStyleName(ValoTheme.LABEL_LARGE);
        previewBtn.addStyleName("user-button");
        previewBtn.addClickListener(this);

        sendBtn = new Button("Send");
        sendBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        sendBtn.addStyleName(ValoTheme.LABEL_LARGE);
        sendBtn.addStyleName("user-button");


        addComponents(downloadBtn,saveBtn,previewBtn,sendBtn);
        setComponentAlignment(sendBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(previewBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(downloadBtn,Alignment.TOP_LEFT);
        setMargin(true);
        setSpacing(true);
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        download();
    }

    private void generatePdf() {


        String tampleFile = "src/main/resources/template.html";


        Map<String,Object> variables = new HashMap<String,Object>();


        variables.put("items",itemsData);
        variables.put("invoice",invoice);
        variables.put("company", company);
        variables.put("customer", customer);

        try {
            new File(outputFilePath).delete();
            Konstants.printInfo("File Deleted ..");
        }catch (UnknownError ue){
            ue.printStackTrace();
        }

        String htmlStr = null;
        try {
            htmlStr = HtmlGenerator.generate(tampleFile, variables);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            PdfGenerator.generate(htmlStr, new FileOutputStream(outputFilePath));
        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }

    private void download(){

        generatePdf();
        Resource res = new FileResource(new File(outputFilePath));
        FileDownloader fd = new FileDownloader(res);
        fd.extend(downloadBtn);
        Konstants.printInfo("File download complete...");

    }
    private void showPreview(){

        generatePdf();

        Konstants.printInfo("Showing preview ..");
        Resource resource = new FileResource(new File("output/pdf/invoice.pdf"));
        BrowserWindowOpener opener = new BrowserWindowOpener(resource);
        opener.extend(previewBtn);
    }
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getComponent() == previewBtn){
            showPreview();
        }
    }
}
