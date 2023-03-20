package com.urise.webapp.util;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Organization;

import java.util.UUID;

public class HtmlUtil {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static String periodToHtml(Organization.Period period) {
        return DateUtil.format(period.getDateFrom()) + " - " + DateUtil.format(period.getDateTo());
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static String contactPattern(ContactType type) {
        switch (type) {
            case EMAIL -> {
                return "type=\"email\" placeholder=\"example@gmail.com\" size=\"30\"";
            }
            case PHONE -> {
                return "type=\"tel\" placeholder=\"+7-111-11-11\" size=\"15\"";
            }
            case HOMEPAGE -> {
                return "type=\"url\" placeholder=\"siteName.com\" size=\"30\"";
            }
            default -> {
                return "type=\"text\"placeholder=\"idName\" size=\"30\"";
            }
        }
    }

    public static String contactToHtml(ContactType type, String value) {
        switch (type) {
            case EMAIL -> {
                return image("email") + link("mailto:", value);
            }
            case PHONE -> {
                return image("phone") + link("tel:", value);
            }
            case SKYPE -> {
                return image("skype") + link("skype:", value);
            }
            case HOMEPAGE -> {
                return image("site") + link("", value);
            }
            case GITHUB -> {
                return image("github") + link("https://github.com/", value);
            }
            case LINKEDIN -> {
                return  image("linkedIn") + link("https://www.linkedin.com/", value);
            }
            case STACKOVERFLOW -> {
                return image("sof") + link("https://stackoverflow.com/", value);
            }
            default -> {
                return value;
            }
        }
    }



    private static String link(String url, String value) {
        return "<a href=\"" + url + value + "\">" + value + "</a>";
    }

    private static String image(String img){
        return "<img src=\"image_source/" + img + ".png\" width=\"25px\">";
    }

}
