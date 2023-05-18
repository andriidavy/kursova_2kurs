package com.example.registration.model.custom;

import java.util.List;

public class CustomDTO {
    private int customId;
    private int customerId;
    private String customerName;
    private String customerSurname;
    private String status;
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
}
