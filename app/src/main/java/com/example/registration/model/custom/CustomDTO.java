package com.example.registration.model.custom;

import java.util.List;

public class CustomDTO {
    private int customId;

    private int customerId;
    private String customerName;
    private String customerSurname;

    private int employeeId;
    private String employeeName;
    private String employeeSurname;

    private String department;
    private String status;
    private double price;
    private boolean isChatOpen;
    private boolean isChatEnable;
    private List<CustomProductDTO> customProductDTOList;

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public List<CustomProductDTO> getCustomProductList() {
        return customProductDTOList;
    }

    public void setCustomProductList(List<CustomProductDTO> customProductDTOList) {
        this.customProductDTOList = customProductDTOList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isChatOpen() {
        return isChatOpen;
    }

    public void setChatOpen(boolean chatOpen) {
        isChatOpen = chatOpen;
    }

    public boolean isChatEnable() {
        return isChatEnable;
    }

    public void setChatEnable(boolean chatEnable) {
        isChatEnable = chatEnable;
    }

    public CustomDTO copyWithUpdatedChatStatus(boolean isChatEnable) {
        CustomDTO copy = new CustomDTO();
        copy.customId = this.customId;
        copy.customerId = this.customerId;
        copy.customerName = this.customerName;
        copy.customerSurname = this.customerSurname;
        copy.employeeId = this.employeeId;
        copy.employeeName = this.employeeName;
        copy.employeeSurname = this.employeeSurname;
        copy.status = this.status;
        copy.department = this.department;
        copy.price = this.price;
        copy.isChatOpen = this.isChatOpen;
        copy.isChatEnable = isChatEnable;
        copy.customProductDTOList = this.customProductDTOList;
        return copy;
    }
}
