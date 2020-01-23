package com.every.every_server.lib;

public class Url {
    private String base;
    private boolean isQueryParamExist;

    public Url(String base) {
        this.base = base;
    }

    public String addQuery(String key, String value) {
        // Query가 없을 경우
        if (!isQueryParamExist) {
            this.base += "?" + key + "=" + value;
            this.isQueryParamExist = true;
            return this.base;
        } else {
            this.base += "&" + key + "=" + value;
            return this.base;
        }
    }

    /**
     * Getter
     */
    public String getBase() {
        return base;
    }
}


