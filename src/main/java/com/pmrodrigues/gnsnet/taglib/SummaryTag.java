package com.pmrodrigues.gnsnet.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Marceloo on 16/01/2015.
 */
public class SummaryTag extends SimpleTagSupport {

    private String text;

    @Override
    public void doTag() throws JspException, IOException {

        JspWriter out = getJspContext().getOut();
        final List<ValidationMessage> errors = (List<ValidationMessage>) getJspContext().getAttribute("errors", PageContext.REQUEST_SCOPE);

        if( errors != null && !errors.isEmpty() ) {
            out.print("<div class=\"alert alert-danger alert-dismissable\">");
            out.print("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">×</button>");
            out.print(format("<strong>%s</strong><br/>", this.text));

            for (ValidationMessage message : errors) {
                out.print(format("<p>%s</p>", message.getMessage()));
            }
            out.print("</div>");
        }

    }

    public void setText(String text) {
        this.text = text;
    }
}

