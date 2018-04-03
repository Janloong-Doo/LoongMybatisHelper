//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.freemarker;

import com.ccnode.codegenerator.log.Log;
import com.ccnode.codegenerator.log.LogFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.StringWriter;
import java.net.URI;
import java.util.Map;

public class TemplateUtil {
    private static Log log = LogFactory.getLogger(TemplateUtil.class);
    private static Configuration configuration = new Configuration(Configuration.getVersion());

    public TemplateUtil() {
    }

    public static String processToString(String templateName, Map<String, Object> root) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter out = new StringWriter();
            template.process(root, out);
            return out.toString();
        } catch (Exception var4) {
            log.error("template process catch exception", var4);
            throw new RuntimeException("process freemarker template catch exception", var4);
        }
    }

    static {
        try {
            configuration.setDirectoryForTemplateLoading(new File((new URI(TemplateUtil.class.getClassLoader().getResource("templates").toString())).getPath()));
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
    }
}
