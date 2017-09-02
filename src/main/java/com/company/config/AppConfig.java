package com.company.config;

public class AppConfig {
    public static final AppConfig INSTANCE =
            new AppConfig();

    private String jdbcUrl;
    private String userName;
    private String password;
    private String driverClassName;
    private Integer countField;

    private AppConfig() {
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String baseUrl) {
        this.jdbcUrl = baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Integer getCountField() {
        return countField;
    }

    public void setCountField(Integer countField) {
        this.countField = countField;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "jdbcUrl='" + jdbcUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", countField=" + countField +
                '}';
    }
}
