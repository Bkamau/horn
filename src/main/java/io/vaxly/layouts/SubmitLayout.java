package io.vaxly.layouts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by bkamau on 20.10.2016.
 */
public class SubmitLayout extends HorizontalLayout implements View {


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

        sendBtn = new Button("Send");
        sendBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        sendBtn.addStyleName(ValoTheme.LABEL_LARGE);
        sendBtn.addStyleName("user-button");


        addComponents(downloadBtn,saveBtn,previewBtn,sendBtn);
        setComponentAlignment(sendBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(previewBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);
        setComponentAlignment(downloadBtn,Alignment.TOP_LEFT);

        setSpacing(true);

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
