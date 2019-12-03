package com.example.masterslave.enums;

public enum DataSourceTypeEnum {

    MASTER("master","主库"),
    SLAVE("slave","从库");

    private String name;
    private String type;

    DataSourceTypeEnum(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


} 