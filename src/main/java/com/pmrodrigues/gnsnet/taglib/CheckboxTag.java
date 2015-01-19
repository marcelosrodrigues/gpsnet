package com.pmrodrigues.gnsnet.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static java.lang.String.format;

/**
 * Created by Marceloo on 19/01/2015.
 */
public class CheckboxTag extends SimpleTagSupport {

    private String id;
    private String label;
    private Collection value;
    private String labelField;
    private String valueField;
    private Collection checked;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Collection getValue() {
        return value;
    }

    public void setValue(Collection value) {
        this.value = value;
    }

    public String getLabelField() {
        return labelField;
    }

    public void setLabelField(String labelField) {
        this.labelField = labelField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public Collection getChecked() {
        return checked;
    }

    public void setChecked(Collection checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void doTag() throws JspException, IOException {

        try {
            final JspWriter out = this.getJspContext().getOut();

            out.write(format("<label for=\"%s\">%s </label>&nbsp;",this.id,this.label));

            for( Object item : value ) {
                out.write("<label class=\"checkbox-inline\">");

                Object value = PropertyUtils.getProperty(item,valueField);
                Object label = PropertyUtils.getProperty(item,labelField);

                out.write(format("<input type=\"checkbox\" name=\"%s\" id=\"%s\" value=\"%s\" checked=\"%s\" />%s",
                                 this.id,this.id,value,this.value.contains(item) ? "checked" : "" ,label));
                out.write("</label>");
            }
            out.write("</div>");
            out.flush();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JspException(e);

        }

    }
}
