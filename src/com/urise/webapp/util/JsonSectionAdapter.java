package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME,section.getClass().getName());
        JsonElement element = context.serialize(section);
        retValue.add(INSTANCE,element);
        return retValue;
    }

    @Override
    public T deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        JsonPrimitive primitive = (JsonPrimitive) object.get(CLASSNAME);
        String className = primitive.getAsString();
        try {
            Class clazz = Class.forName(className);
            return context.deserialize(object.get(INSTANCE),clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

