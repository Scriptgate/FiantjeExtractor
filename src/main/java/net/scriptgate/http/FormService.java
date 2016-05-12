package net.scriptgate.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class FormService {

    private final Logger log = getLogger(FormService.class);

    String getFormParameters(String html, String username, String password) throws UnsupportedEncodingException {
        log.debug("Extracting form data...");
        Elements inputElements = findInputElements(html);
        List<String> parameters = buildParameterList(username, password, inputElements);
        return parameters.stream().collect(joining("&"));
    }

    private List<String> buildParameterList(String username, String password, Elements inputElements) throws UnsupportedEncodingException {
        List<String> parameters = new ArrayList<>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if(key.equals(getUsernameField())) {
                value = username;
            }

            if(key.equals(getPasswordField())) {
                value = password;
            }
            parameters.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }
        return parameters;
    }

    private Elements findInputElements(String html) {
        Document document = Jsoup.parse(html);
        Element loginForm = document.getElementById(getFormId());
        return loginForm.getElementsByTag("input");
    }

    protected abstract String getFormId();

    protected abstract String getUsernameField();

    protected abstract String getPasswordField();
}
