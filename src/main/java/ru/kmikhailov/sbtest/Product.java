package ru.kmikhailov.sbtest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Product {
    @NotNull
    private String code;

    @NotNull
    @Size(min=13, max=13)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
