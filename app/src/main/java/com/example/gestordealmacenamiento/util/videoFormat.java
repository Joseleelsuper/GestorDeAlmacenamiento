package com.example.gestordealmacenamiento.util;

public enum videoFormat {

    MP4("mp4"),
    AVI("avi"),
    FLV("flv"),
    MOV("mov"),
    WMV("wmv"),
    MKV("mkv"),
    WEBM("webm"),
    VOB("vob"),
    OGG("ogg"),
    QT("qt"),
    SWF("swf"),
    RM("rm"),
    RMVB("rmvb"),
    MPG("mpg"),
    MPEG("mpeg"),
    M4V("m4v"),
    M2V("m2v"),
    TS("ts"),
    MTS("mts"),
    M2TS("m2ts"),
    ASF("asf"),
    AMV("amv"),
    M2P("m2p"),
    M2T("m2t"),
    MPE("mpe"),
    MPV("mpv");

    private final String format;

    videoFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
