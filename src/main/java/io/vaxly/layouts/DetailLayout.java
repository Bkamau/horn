package io.vaxly.layouts;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by bkamau on 2.10.2016.
 */
public class DetailLayout extends HorizontalLayout {

     FormLayout detailDatesLayout = new FormLayout();
     VerticalLayout detailLogoLayout = new VerticalLayout();

     TextField invoiceField = new TextField("Invoice #", "");
     DateField issueDate = new PopupDateField("Issue Date");
     DateField dueDate = new PopupDateField("Due Date");

     VerticalLayout dropPane;
     Label infoLabel;

    GregorianCalendar calEnd = new GregorianCalendar();


    public DetailLayout(){

        detailDatesLayout.setSizeFull();
        detailDatesLayout.setSpacing(true);
        detailDatesLayout.setMargin(true);
        detailDatesLayout.addStyleName("detailDatesLayout");

        detailLogoLayout.setSizeFull();
        detailLogoLayout.setSpacing(true);
        detailLogoLayout.setMargin(true);
        detailLogoLayout.addStyleName("detailLogoLayout");

        addComponents(detailDatesLayout,detailLogoLayout);
        setExpandRatio(detailDatesLayout, 3);
        setExpandRatio(detailLogoLayout, 2);

        calEnd.set(java.util.Calendar.DATE, 1);
        calEnd.roll(java.util.Calendar.DATE, -1);

        invoiceField.setWidth(100.0f, Unit.PERCENTAGE);
        invoiceField.setValue("  001");
        issueDate.setWidth(100.0f, Unit.PERCENTAGE);
        issueDate.setValue(new Date());
        issueDate.setDateFormat("dd-MM-yyyy");
        dueDate.setWidth(100.0f, Unit.PERCENTAGE);
        dueDate.setDateFormat("dd-MM-yyyy");
        dueDate.setValue(calEnd.getTime());
        detailDatesLayout.addComponents(invoiceField,issueDate,dueDate);

        invoiceField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        invoiceField.addStyleName("textfield-background");

        issueDate.addStyleName(ValoTheme.DATEFIELD_BORDERLESS);
        issueDate.addStyleName("textfield-background");

        dueDate.addStyleName(ValoTheme.DATEFIELD_BORDERLESS);
        dueDate.addStyleName("textfield-background");

        infoLabel = new Label("Drag and Drop your Logo Here. Max 2MB");

        dropPane = new VerticalLayout(infoLabel);
        dropPane.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropPane.setWidth(150.0f, Unit.PIXELS);
        dropPane.setHeight(150.0f, Unit.PIXELS);
        dropPane.addStyleName("droparea");

        ImageDropBox dropBox = new ImageDropBox(dropPane);
        dropBox.setSizeUndefined();
        detailLogoLayout.addComponent(dropBox);
        detailLogoLayout.setComponentAlignment(dropBox, Alignment.MIDDLE_CENTER);


        setSizeFull();
        setStyleName("defaul-layout");
    }


    @StyleSheet("dragndropexample.css")
    public class ImageDropBox extends DragAndDropWrapper implements DropHandler {

        public byte[] byteArray;

        public Component component;
        private static final long FILE_SIZE_LIMIT = 2 * 1024 * 1024; // 2MB

        public ImageDropBox(final Component root) {
            super(root);
            setDropHandler(this);
        }

        @Override
        public void drop(final DragAndDropEvent dropEvent) {

            // expecting this to be an html5 drag
            final WrapperTransferable tr = (WrapperTransferable) dropEvent
                    .getTransferable();
            final Html5File[] files = tr.getFiles();
            if (files != null) {
                for (final Html5File html5File : files) {
                    final String fileName = html5File.getFileName();

                    if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                        Notification
                                .show("File rejected. Max 2Mb files are accepted by Sampler",
                                        Notification.Type.WARNING_MESSAGE);
                    } else {

                        final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                        final StreamVariable streamVariable = new StreamVariable() {

                            @Override
                            public OutputStream getOutputStream() {
                                return bas;
                            }

                            @Override
                            public boolean listenProgress() {
                                return false;
                            }

                            @Override
                            public void onProgress(
                                    final StreamingProgressEvent event) {
                            }

                            @Override
                            public void streamingStarted(
                                    final StreamingStartEvent event) {
                            }

                            @Override
                            public void streamingFinished(
                                    final StreamingEndEvent event) {
                                showFile(fileName, html5File.getType(), bas);
                            }

                            @Override
                            public void streamingFailed(
                                    final StreamingErrorEvent event) {
                            }

                            @Override
                            public boolean isInterrupted() {
                                return false;
                            }
                        };
                        html5File.setStreamVariable(streamVariable);
                    }
                }

            } else {
                final String text = tr.getText();
                if (text != null) {
                }
            }
        }

        private void showFile(final String name, final String type,
                              final ByteArrayOutputStream bas) {
            // resource for serving the file contents
            final StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                @Override
                public InputStream getStream() {
                    if (bas != null) {
                         byteArray = bas.toByteArray();

                        return new ByteArrayInputStream(byteArray);
                    }
                    return null;
                }
            };
            final StreamResource resource = new StreamResource(streamSource, name);


            // show the file contents - images only for now
            final Embedded embedded = new Embedded(name, resource);
            embedded.setWidth(150.0f, Unit.PIXELS);
            embedded.setHeight(150.0f, Unit.PIXELS);

            component = new ImageDropBox(embedded);

            dropPane.removeComponent(infoLabel);
            dropPane.removeStyleName("droparea");
            dropPane.addComponent(component);

        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }

}
