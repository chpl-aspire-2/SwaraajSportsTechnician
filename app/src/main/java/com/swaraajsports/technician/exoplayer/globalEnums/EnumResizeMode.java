package com.swaraajsports.technician.exoplayer.globalEnums;

public enum EnumResizeMode {

    UNDEFINE("UNDEFINE", -1),
    FIT("Fit", 1),
    FILL("Fill", 2),
    ZOOM("Zoom", 3);

    private String valueStr;

    private final Integer value;

    EnumResizeMode(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumResizeMode get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumResizeMode[] arr$ = values();
        for (EnumResizeMode val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumResizeMode get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumResizeMode[] arr$ = values();
        for (EnumResizeMode val : arr$) {
            if (val.value.equals(value)) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public Integer getValue() {
        return value;
    }
}
