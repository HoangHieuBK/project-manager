package com.example.demo.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "account", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
@JsonIgnoreProperties(value = { "roleId", "staffCollection" })
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "account_id")
	private Integer accountId;

	@NotBlank
	@Size(min = 3, max = 50)
	@Basic(optional = false)
	@Column(name = "account_name")
	private String accountName;

    @NotBlank
    @Size(min=3, max = 50)
    @Column(name = "username")
    private String username;
    
	@NaturalId
	@NotBlank
	@Size(max = 50)
	@Column(name = "email")
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 100)
	@Basic(optional = false)
	@Column(name = "password")
	private String password;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
	private Collection<Staff> staffCollection;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Transient
	private boolean check = true;

	public Account() {
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Account(Integer accountId) {
		this.accountId = accountId;
	}

	public Account(String accountName, String username, String email, String password) {
		this.accountName = accountName;
        this.username = username;
		this.email = email;
		this.password = password;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public Set<Role> getRoles() {
        return roles;
    }
 
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
	public Collection<Staff> getStaffCollection() {
		return staffCollection;
	}

	public void setStaffCollection(Collection<Staff> staffCollection) {
		this.staffCollection = staffCollection;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (accountId != null ? accountId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Account)) {
			return false;
		}
		Account other = (Account) object;
		if ((this.accountId == null && other.accountId != null)
				|| (this.accountId != null && !this.accountId.equals(other.accountId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gdfgdfgdfg.Account[ accountId=" + accountId + " ]";
	}

}
