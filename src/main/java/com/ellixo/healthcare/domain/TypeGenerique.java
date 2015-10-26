package com.ellixo.healthcare.domain;

public enum TypeGenerique {
    PRINCEPS(0), GENERIQUE(1), GENERIQUE_COMPLEMENT_POSOLOGIQUE(2), GENERIQUE_SUBSTITUABLE(4);

    private int code;

    TypeGenerique(int code) {
        this.code = code;
    }

    public static TypeGenerique fromCode(int code) {
        for (TypeGenerique type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
