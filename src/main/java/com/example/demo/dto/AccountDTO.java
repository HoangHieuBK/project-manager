package com.example.demo.dto;

import java.util.Collection;

import com.example.demo.entity.Role;
import com.example.demo.entity.Staff;

public class AccountDTO {
    private Integer accountId;

    private String accountName;

    private String password;
    
    private Collection<Staff> staffCollection;
    
    private Role roleId;
    
    private boolean check = true;

	public AccountDTO() {
		super();
	}

	public AccountDTO(Integer accountId, String accountName, String password, Collection<Staff> staffCollection,
			Role roleId, boolean check) {
		super();
		this.accountId = accountId;
		this.accountName = accountName;
		this.password = password;
		this.staffCollection = staffCollection;
		this.roleId = roleId;
		this.check = check;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Staff> getStaffCollection() {
		return staffCollection;
	}

	public void setStaffCollection(Collection<Staff> staffCollection) {
		this.staffCollection = staffCollection;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
    
    
}
