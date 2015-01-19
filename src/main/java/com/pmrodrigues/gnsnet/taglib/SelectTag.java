package com.pmrodrigues.gnsnet.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;

/**
 * Created by Marceloo on 19/01/2015.
 */
public class SelectTag extends SimpleTagSupport {

    private ResourceBundle message = ResourceBundle.getBundle("labels");

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }

    public Collection getValue() {
        return value;
    }

    public void setValue(Collection value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getLabelField() {
        return labelField;
    }

    public void setLabelField(String labelField) {
        this.labelField = labelField;
    }

    public Object getSelected() {
        return selected;
    }

    public void setSelected(Object selected) {
        this.selected = selected;
    }

    private String errorField;
    private Collection value;
    private String id;
    private String valueField;
    private String labelField;
    private Object selected;

    @Override
    public void doTag() throws JspException, IOException {

        try {
            final String label = message.getString(this.label);
            JspWriter out = getJspContext().getOut();
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


            out.println(format("<div class=\"form-group %s\">",errorCss));
            out.println(format("<label for=\"%s\">%s</label>",this.id,label));
            out.println(format("<select name=\"%s\" id=\"%s\" class=\"form-control\">",this.id,this.id));
            out.println("<option></option>");

            for( Object item : this.value ){
                out.println(format("<option value=\"%s\" %s>%s</option>",
                                getProperty(item, valueField),
                                (item.equals(selected) ? "selected" : ""),
                                getProperty(item, labelField)));
            }

            out.println("</select>");
            out.println("</div>");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JspException(e);
        }
    }
}
