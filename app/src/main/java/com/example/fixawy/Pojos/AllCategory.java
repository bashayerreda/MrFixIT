package com.example.fixawy.Pojos;

import java.util.List;

public class AllCategory {
    int categoryId;
    String categoryTitle;
    List<User>employeeDataList;

    public List<User> getEmployeeDataList() {
        return employeeDataList;
    }

    public void setEmployeeDataList(List<User> employeeDataList) {
        this.employeeDataList = employeeDataList;
    }

    public AllCategory(int categoryId, String categoryTitle, List<User> employeeDataList) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.employeeDataList = employeeDataList;
    }

    public AllCategory(int categoryId, String[] strings) {
    }

    public AllCategory(int categoryId, String categoryTitle) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
    }



    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}