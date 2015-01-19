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
import static org.apache.commons.beanutils.BeanUtils.getProperty;

/**
 * Created by Marceloo on 09/01/2015.
 */
public class TreeViewTag extends SimpleTagSupport {

    private Collection list;

    private String value;

    private String label;

    private String url;

    private String subList;

    private String id;

    public TreeViewTag() {
        super();
    }

    public void doTag() throws JspException, IOException {

        final JspWriter writer = getJspContext().getOut();
        writer.println(format("<div id=\"%s\" class=\"css-treeview\"><ul>", this.id));

        try {
            createTreeView(list);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        writer.println("</ul></div>");
        writer.flush();

    }

    private void createTreeView(final Collection list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {

        final JspWriter writer = getJspContext().getOut();
        for (final Object entity : list) {
            if (!GenericValidator.isBlankOrNull(url) && !GenericValidator.isBlankOrNull(value)) {

                Collection sublist = null;
                if (!GenericValidator.isBlankOrNull(this.subList)) {
                    sublist = (Collection) PropertyUtils.getProperty(entity, this.subList);
                }

                writer.println("<li>");
                if (sublist != null && !sublist.isEmpty()) {

                    writer.println(format("<input type=\"checkbox\" id=\"item-%s\" /><label for=\"item-%s\"><a href=\"%s\">%s</a></label>",
                            getProperty(entity, value),
                            getProperty(entity, value),
                            format("%s?id=%s", url, getProperty(entity, value)),
                            getProperty(entity, label)));

                    writer.println("<ul>");
                    createTreeView(sublist);
                    writer.append("</ul>");

                } else {

                    writer.println(format("<a href=\"%s\">%s</a>",
                            format("%s?id=%s", url, getProperty(entity, value)),
                            getProperty(entity, label)));
                }

                writer.println("</li>");

            }
        }
    }

    public void setList(final Collection list) {
        this.list = list;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setSubList(final String subList) {
        this.subList = subList;
    }

    public void setId(final String id) {
        this.id = id;
    }
}