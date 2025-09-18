package com.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.api.model.StockDto;

public class UserDto {

	private Integer uid;
    private String uname;
    private String email;
    private String role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private List<StockDto> stocks; // list of stocks fetched from StockService

    public UserDto() {}

    public UserDto(Integer uid, String uname, String email, String role,
                             LocalDateTime createdDate, LocalDateTime modifiedDate,
                             List<StockDto> stocks) {
        this.uid = uid;
        this.uname = uname;
        this.email = email;
        this.role = role;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.stocks = stocks;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<StockDto> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDto> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "UserWithStocksDto [uid=" + uid + ", uname=" + uname + ", email=" + email + 
               ", role=" + role + ", createdDate=" + createdDate + ", modifiedDate=" + 
               modifiedDate + ", stocks=" + stocks + "]";
    }
}
