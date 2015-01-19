package com.pmrodrigues.gnsnet.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/01/2015.
 */
public class InputTextTag extends SimpleTagSupport {

    private ResourceBundle message = ResourceBundle.getBundle("labels");

    private String label;
    private String value;
    private String id;
    private String errorField;


    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        final JspWriter writer = getJspContext().getOut();
        final List<ValidationMessage> errors = (List<ValidationMessage>) getJspContext().getAttribute("errors", PageContext.REQUEST_SCOPE);

        String errorCss = "";

        if (errors != null && !errors.isEmpty()) {
            for(ValidationMessage message : errors ) {
                if( message.getCategory().equalsIgnoreCase(this.errorField) ){
                    errorCss = "has-error";
                    break;
                }
            }
        }


        final String label = message.getString(this.label);
        writer.print(format("<div class=\"form-group %s\">", errorCss));
        writer.print(format("<label for=\"%s\">%s</label>", this.id, label));
        writer.print(format("<input type=\"text\" value=\"%s\" id=\"%s\" name=\"%s\" class=\"form-control\" placeholder=\"%s\" />",
                this.value == null ? "" : this.value, this.id, this.id, label));
        writer.print("</div>");

        writer.flush();
    }

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }
}
