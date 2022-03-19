package com.cos.photogram.util;

public class Script {

    public static String back(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert("+msg+");"); //경고창 하나 띄우고
        sb.append("history.back();"); //뒤로 돌아간다
        sb.append("</script>");
        return sb.toString();

    }
}
