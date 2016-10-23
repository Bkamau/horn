package io.vaxly.layouts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import io.vaxly.mainUi.Homepage;
import org.parse4j.ParseUser;
import io.vaxly.models.Company;
import io.vaxly.models.Customer;
import io.vaxly.models.Invoice;
import io.vaxly.models.Item;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkamau on 20.10.2016.
 */
public class UserLayout extends HorizontalLayout implements View, Button.ClickListener {

    private Button logoutBtn;
    private Button invoiceBtn;
    private Button customerBtn;
    private Button nameBtn;

    public static Invoice gotinvoice = new Invoice();
    ArrayList<Item> getItemsDataSet = new ArrayList<Item>();

    public UserLayout( ){


        nameBtn = new Button("Account");
        nameBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        nameBtn.addStyleName(ValoTheme.LABEL_LARGE);
        nameBtn.addStyleName("user-button");

        logoutBtn = new Button("Logout");
        logoutBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        logoutBtn.addStyleName(ValoTheme.LABEL_LARGE);
        logoutBtn.addStyleName("user-button");
        logoutBtn.addClickListener(this);

        invoiceBtn = new Button("Invoices");
        invoiceBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        invoiceBtn.addStyleName(ValoTheme.LABEL_LARGE);
        invoiceBtn.addStyleName("user-button");

        customerBtn = new Button("Customers");
        customerBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        customerBtn.addStyleName(ValoTheme.LABEL_LARGE);
        customerBtn.addStyleName("user-button");


        addComponents(nameBtn,invoiceBtn,customerBtn,logoutBtn);
        setComponentAlignment(logoutBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(customerBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(invoiceBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(nameBtn,Alignment.TOP_LEFT);
        addStyleName("user-layout");

        setSpacing(true);

    }

    private Button createButton(String btnName){
        Button btn = new Button(btnName);
        btn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        btn.addStyleName(ValoTheme.LABEL_LARGE);
        btn.addStyleName("user-button");

        return btn;
    }

    private void getInvoice(){

        ParseUser user = ParseUser.currentUser;
        System.out.println("User (i):" + user);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invoice");
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    System.out.println("User size: " + list.size());
                    for(int i = 0; i < list.size(); i++){

                        ParseObject invoiceobj = list.get(i);

                        gotinvoice.setId(invoiceobj.getInt("id"));
                        System.out.println("Invoice Id set" );

                        gotinvoice.setIssueDate(invoiceobj.getString("issueDate"));
                        System.out.println("Invoice issueDate set" );

                        gotinvoice.setDueDate(invoiceobj.getString("dueDate"));
                        System.out.println("Invoice dueDate set" );

                        gotinvoice.setImageUrl(invoiceobj.getParseFile("logo").getUrl());

                        String custId = invoiceobj.getString("customerId");
                        ParseQuery<ParseObject> custquery = ParseQuery.getQuery("Customer");
                        custquery.getInBackground(custId, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if(e == null){
                                    Customer customer = new Customer();
                                    customer.setName(parseObject.getString("name"));
                                    customer.setAddress(parseObject.getString("address"));
                                    customer.setZip(parseObject.getString("zip"));
                                    customer.setCity(parseObject.getString("city"));
                                    customer.setCountry(parseObject.getString("country"));

                                    gotinvoice.setCustomer(customer);
                                    System.out.println("Invoice customer set");

                                }else{
                                    System.out.println("ERROR LOADING CUSTOMER FOR INVOICE :" + e.getMessage());
                                }
                            }
                        });

                        String mycompId = invoiceobj.getString("mycompanyId");
                        System.out.println("MyComp id: " + mycompId );
                        ParseQuery<ParseObject> mycompquery = ParseQuery.getQuery("MyCompany");
                        mycompquery.getInBackground(mycompId, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if(e == null){
                                    Company myCompany = new Company();
                                    myCompany.setName(parseObject.getString("name"));
                                    myCompany.setCompanyNumber(parseObject.getString("companyNo"));
                                    myCompany.setAddress(parseObject.getString("address"));
                                    myCompany.setZip(parseObject.getString("zip"));
                                    myCompany.setCity(parseObject.getString("city"));
                                    myCompany.setCountry(parseObject.getString("country"));
                                    myCompany.setBankAcc(parseObject.getString("bankAcc"));
                                    myCompany.setBankBic(parseObject.getString("bankbic"));

                                    gotinvoice.setCompany(myCompany);
                                    System.out.println("Invoice myCompany set" );

                                } else{
                                    System.out.println("ERROR LOADING MY COMPANY DETAILS FOR INVOICE:" + e.getMessage());
                                }
                            }
                        });

                        String itemsId = invoiceobj.getString("itemsId");
                        ParseQuery<ParseObject> itemsquery = ParseQuery.getQuery("Items");
                        itemsquery.getInBackground(itemsId, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if(e == null){

                                    Object item = parseObject.get("item");
                                    String result = item.toString().replaceAll("=", ":");

                                    JSONObject object = new JSONObject(result);

                                    JSONArray jArr = object.getJSONArray("gsonItems");

                                    System.out.println("All Items Array: "  + jArr);

                                    for(int i=0; i < jArr.length(); i++){
                                        JSONObject obj = jArr.getJSONObject(i);
                                        Item myItem = new Item();

                                        myItem.setName(obj.getString("name"));
                                        System.out.println("Name (item)" + i + ": " + obj.getString("name"));

                                        myItem.setQuantity(obj.getString("quantity"));
                                        myItem.setPrice(obj.getString("price"));
                                        myItem.setAmount(obj.getString("amount"));

                                        getItemsDataSet.add(myItem);

                                        System.out.println("Item, Name (ii)" + i + ": " + myItem.getName());
                                    }
                                    gotinvoice.setItems(getItemsDataSet);


                                } else{
                                    System.out.println("ERROR LOADING MY ITEMS FOR INVOICE:" + e.getMessage());
                                }

                            }
                        });
                    }

                }else{
                    System.out.println("ERROR LOADING INVOICE:" + e.getMessage());
                }
            }
        });

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getComponent() == logoutBtn){
            ParseUser.currentUser = null;
            getUI().getNavigator().addView(Homepage.NAME, new Homepage());
            navigate(Homepage.NAME);
        }
    }

    private void navigate(String viewName){
        getUI().getNavigator().navigateTo(viewName);
    }
}
