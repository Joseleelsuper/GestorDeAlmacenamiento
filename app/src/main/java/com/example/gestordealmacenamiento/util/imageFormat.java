package com.example.gestordealmacenamiento.util;

public enum imageFormat{

    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    BMP("bmp"),
    GIF("gif"),
    WEBP("webp");

    private final String format;

    imageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
