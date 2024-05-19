package com.kazurayam.testng;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.testng.ITestContext;

import java.text.DateFormat;
import java.util.Date;

public class ITestContextObjectMapperBuilder {

    private boolean enablePrettyPrint;
    private DateFormat dateFormat;
    public ITestContextObjectMapperBuilder() {
        enablePrettyPrint = false;
        dateFormat = DateSerializer.getDefaultDateFormat();
    }

    public ITestContextObjectMapperBuilder enablePrettyPrint(boolean enablePrettyPrint) {
        this.enablePrettyPrint = enablePrettyPrint;
        return this;
    }

    public ITestContextObjectMapperBuilder dateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public ObjectMapper build() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT,
                this.enablePrettyPrint);

        SimpleModule module = new SimpleModule("ITestContextSerializer");
        module.addSerializer(ITestContext.class, new ITestContextSerializer());
        module.addSerializer(Date.class, new DateSerializer());

        objectMapper.registerModule(module);
        return objectMapper;
    }
}