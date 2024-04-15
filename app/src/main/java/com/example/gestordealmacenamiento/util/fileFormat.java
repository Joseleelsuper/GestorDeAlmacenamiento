package com.example.gestordealmacenamiento.util;

public enum fileFormat {

    TXT("txt"),
    PDF("pdf"),
    DOC("doc"),
    DOCX("docx"),
    XLS("xls"),
    XLSX("xlsx"),
    PPT("ppt"),
    PPTX("pptx"),
    HTML("html"),
    XML("xml"),
    JSON("json"),
    CSV("csv"),
    ZIP("zip"),
    RAR("rar"),
    TAR("tar");

    private final String format;

    fileFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
