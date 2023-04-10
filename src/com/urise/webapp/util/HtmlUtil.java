package com.urise.webapp.util;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Organization;

public class HtmlUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static String periodToHtml(Organization.Period period) {
        return DateUtil.format(period.getDateFrom()) + " - " + DateUtil.format(period.getDateTo());
    }

    public static String contactPattern(ContactType type) {
        switch (type) {
            case EMAIL -> {
                return "type=\"email\" placeholder=\"" + type.getTitle() + ": example@gmail.com\" maxlength=\"30\"";
            }
            case PHONE -> {
                String phonePattern ="pattern=\"[0-9]{1,4}-[0-9]{3}-[0-9]{3}-[0-9]{4}\"";
                return "<type=\"tel\" placeholder=\"" + type.getTitle() + ": 7-900-123-4567\" " +
                         phonePattern +" maxlength=\"20\"";
            }
            case HOMEPAGE -> {
                return "type=\"url\" placeholder=\"" + type.getTitle() + ": https://siteName.com\" maxlength=\"30\"";
            }
            default -> {
                return "type=\"text\"placeholder=\"" + type.getTitle() + ": idName\" maxlength=\"30\"";
            }
        }
    }

    public static String contactToHtml(ContactType type, String value, String theme) {
        switch (type) {
            case EMAIL -> {
                return link("mailto:", value, image("email", theme));
            }
            case PHONE -> {
                return link("tel:", value, image("phone", theme));
            }
            case SKYPE -> {
                return link("skype:", value, image("skype", theme));
            }
            case HOMEPAGE -> {
                return link("", value, image("site", theme));
            }
            case GITHUB -> {
                return link("https://github.com/", value, image("github", theme));
            }
            case LINKEDIN -> {
                return link("https://www.linkedin.com/", value, image("linkedIn", theme));
            }
            case STACKOVERFLOW -> {
                return link("https://stackoverflow.com/", value, image("sof", theme));
            }
            default -> {
                return value;
            }
        }
    }


    private static String link(String url, String value, String image) {
        return "<a class='link-style' href=\"" + url + value + "\">" + image + value + "</a>";
    }

    public static String image(String img, String theme) {
        return "<img class='image' src=\"image_source/" + theme + "/" + img + ".png\"" + "alt=" + img + ">";
    }
    }
