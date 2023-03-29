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
                return "type=\"url\" placeholder=\"https://siteName.com\" size=\"30\"";
            }
            default -> {
                return "type=\"text\"placeholder=\"idName\" size=\"30\"";
            }
        }
    }

    public static String contactToHtml(ContactType type, String value) {
        switch (type) {
            case EMAIL -> {
                return  link("mailto:", value, image("email"));
            }
            case PHONE -> {
                return  link("tel:", value, image("phone"));
            }
            case SKYPE -> {
                return  link("skype:", value, image("skype"));
            }
            case HOMEPAGE -> {
                return link("", value, image("site"));
            }
            case GITHUB -> {
                return  link("https://github.com/", value, image("github"));
            }
            case LINKEDIN -> {
                return  link("https://www.linkedin.com/", value, image("linkedIn") );
            }
            case STACKOVERFLOW -> {
                return link("https://stackoverflow.com/", value, image("sof"));
            }
            default -> {
                return value;
            }
        }
    }



    private static String link(String url, String value, String image) {
        return "<a href=\"" + url + value + "\">" + image + "</a>" +value;
    }

    private static String image(String img){
        return "<img src=\"image_source/" + img + ".png\" width=\"25px\">";
    }

}
