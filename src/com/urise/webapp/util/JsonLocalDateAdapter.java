package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonLocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

 //   private  static  final  DateTimeFormatter  FORMATTER =DateTimeFormatter.ofPattern("yyyy-MMM-dd");
    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Override
    public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(element.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }





}
