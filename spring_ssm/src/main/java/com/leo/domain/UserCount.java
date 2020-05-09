package com.leo.domain;

public class UserCount {
    private String name;
    private Integer account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserCount{" +
                "name='" + name + '\'' +
                ", account=" + account +
                '}';
    }
}
