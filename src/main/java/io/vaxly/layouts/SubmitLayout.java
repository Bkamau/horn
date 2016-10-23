package io.vaxly.layouts;

import com.google.gson.Gson;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.mainUi.LoginSignIn;
import io.vaxly.utils.HtmlGenerator;
import io.vaxly.utils.Konstants;
import io.vaxly.utils.PdfGenerator;
import io.vaxly.utils.SendMail;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import org.parse4j.callback.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.vaxly.MyUI.properties;
import static io.vaxly.layouts.ContactsLayout.company;
import static io.vaxly.layouts.ContactsLayout.customer;
import static io.vaxly.layouts.DetailLayout.image;
import static io.vaxly.mainUi.CreateInvoice.invoice;
import static io.vaxly.mainUi.CreateInvoice.itemsData;

/**
 * Created by bkamau on 20.10.2016.
 */
public class SubmitLayout extends HorizontalLayout implements View, Button.ClickListener {

    private  static  String outputFilePath = "output/pdf/invoice.pdf";
    private  Button loginBtn;
    private Button downloadBtn;
    private Button saveBtn;
    private Button previewBtn;
    private Button sendBtn;

    String customerId;
    String myCompanyId;
    String itemsId;
    String invoiceId;


    public SubmitLayout( ){

        downloadBtn = createButton ("Download PDF");
        loginBtn = createButton("Login");
        saveBtn =  createButton("Save");
        previewBtn = createButton("Preview");
        sendBtn = createButton("Send");

        if (ParseUser.currentUser != null){
            addComponent(saveBtn);
        }
        else {
            addComponent(loginBtn);
        }
        addComponents(downloadBtn,previewBtn,sendBtn);

        setMargin(true);
        setSpacing(true);
    }

    private Button createButton(String name){
        Button btn = new Button(name);
        btn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        btn.addStyleName(ValoTheme.LABEL_LARGE);
        btn.addStyleName("user-button");
        btn.addClickListener(this);

        return btn;
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

    private void sendMail(){

        // SMTP info
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String mailFrom = properties.getProperty("mailFrom");
        String password = properties.getProperty("password");

        // message info
        String mailTo = "mndungu@abo.fi";
        String subject = "Invoice";
        String message = "Please find your new invoice attached to this email. We thank you for your loyalty.";

        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = "output/pdf/invoice.pdf";


        try {
            SendMail.sendmail(host, port, mailFrom, password, mailTo,
                    subject, message ,attachFiles);
            Konstants.printInfo("Mail sent ...");
        } catch (Exception ex) {
            Konstants.printInfo("Could not send email...");
            ex.printStackTrace();
        }
    }

    private void addInvoice(){

        ParseObject customerobj = saveCustomer();
        customerobj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    customerId = customerobj.getObjectId();
                    System.out.println("CustomerId: " + customerId);
                    Notification.show("Customer details saved successfully");

                    ParseObject mycompany = saveCompany();
                    mycompany.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                myCompanyId = mycompany.getObjectId();
                                System.out.println("myCompanyId: " + myCompanyId);
                                Notification.show("My Company details saved successfully");

                                ParseObject item = saveItems();
                                item.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null){
                                            itemsId = item.getObjectId();
                                            System.out.println("ItemId: " + item.getObjectId());
                                            Notification.show("Items saved successfully");

                                            byte [] myimage = image;
                                            if(myimage != null) {
                                                ParseFile file = new ParseFile("logo.png", myimage);
                                                file.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if(e==null){
                                                            Notification.show("logo saved successfully");

                                                            ParseObject invoiceobj = saveInvoice();
                                                            invoiceobj.put("logo", file);
                                                            invoiceobj.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(ParseException e) {
                                                                    if(e == null){
                                                                        invoiceId = invoiceobj.getObjectId();
                                                                        System.out.println("Invoice id: " +  invoiceId);
                                                                        Notification.show("Invoice saved successfully");
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                            else {
                                                System.out.println("ERROR GETTING BYTE IMAGE");

                                                ParseObject invoiceobj = saveInvoice();
                                                invoiceobj.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if(e == null){
                                                            invoiceId = invoiceobj.getObjectId();
                                                            System.out.println("Invoice id: " +  invoiceId);
                                                            Notification.show("Invoice saved successfully");
                                                        }else{
                                                            System.out.println("ERROR SAVING INVOICE" + e.getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        else{
                                            Notification.show("ERROR SAVING ITEMS: " +  e.getMessage());
                                        }
                                    }
                                });


                            }else{
                                Notification.show("ERROR SAVING MY COMPANY DETAILS:  " +  e.getMessage());
                            }
                        }
                    });
                }
                else{
                    Notification.show("ERROR SAVING CUSTOMER DETAILS: " + e.getMessage());
                }
            }
        });

    }

    private ParseObject saveInvoice(){

        ParseUser user = ParseUser.currentUser;

        Date myDate = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(myDate);

        ParseObject invoiceobj = new ParseObject("Invoice");
        invoiceobj.put("user", user);
        invoiceobj.put("id", invoice.getId());
        invoiceobj.put("issueDate", invoice.getIssueDate());
        invoiceobj.put("dueDate", invoice.getDueDate());
        invoiceobj.put("customerId", customerId);
        invoiceobj.put("mycompanyId", myCompanyId);
        invoiceobj.put("itemsId", itemsId);
        invoiceobj.put("subTotal", invoice.getSubTotal());
        invoiceobj.put("tax", invoice.getTax());
        invoiceobj.put("total", invoice.getTotal());
        invoiceobj.put("currency", invoice.getCurrency());

        return invoiceobj;

    }

    private ParseObject saveItems(){
        JSONObject obj = new JSONObject();
        String json = new Gson().toJson(itemsData);
        obj.put("gsonItems", json);

        ParseObject item = new ParseObject("Items");
        item.put("item", obj);

        return item;
    }

    private ParseObject saveCompany(){

        ParseObject mycompany = new ParseObject("Company");

        mycompany.put("name", company.getName());
        mycompany.put("address", company.getAddress());
        mycompany.put("zip",company.getZip());
        mycompany.put("city", company.getCity());
        mycompany.put("country", company.getCountry());
        mycompany.put("companyNo",company.getCompanyNumber());
        mycompany.put("bankAcc",company.getBankAcc());
        mycompany.put("bankbic",company.getBankBic());

        return mycompany;
    }

    private ParseObject saveCustomer(){
        ParseObject customerobj = new ParseObject("Customer");
        customerobj.put("name", customer.getName());
        customerobj.put("address", customer.getAddress());
        customerobj.put("zip", customer.getZip());
        customerobj.put("city", customer.getCity());
        customerobj.put("country", customer.getCountry());

        return customerobj;
    }


    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getComponent() == previewBtn){
            showPreview();
        }else if (clickEvent.getComponent() == loginBtn){
            UI.getCurrent().addWindow(new LoginSignIn());
        }else if (clickEvent.getComponent() == sendBtn){
            sendMail();
        } else if(clickEvent.getButton() == saveBtn){
            addInvoice();
            Konstants.printInfo("Invoice saved successfully" );
            Notification.show("Invoice saved successfully");
        }
    }
}
