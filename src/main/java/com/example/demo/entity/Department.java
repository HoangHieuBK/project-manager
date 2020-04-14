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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 *
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "department")
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findByDepartmentId", query = "SELECT d FROM Department d WHERE d.departmentId = :departmentId"),
    @NamedQuery(name = "Department.findByDepartmentName", query = "SELECT d FROM Department d WHERE d.departmentName = :departmentName"),
    @NamedQuery(name = "Department.findByManagerName", query = "SELECT d FROM Department d WHERE d.managerName = :managerName"),
    @NamedQuery(name = "Department.findByDiscription", query = "SELECT d FROM Department d WHERE d.discription = :discription")})
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    @Basic(optional = false)
    @Column(name = "department_id")
    private Integer departmentId;

    @Basic(optional = false)
    @NotBlank
    @Column(name = "department_name")
    private String departmentName;

    @Basic(optional = false)
    @NotBlank
    @Column(name = "manager_name")
    private String managerName;

    @Basic(optional = false)
    @Column(name = "discription")
    private String discription;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentId")
    private Collection<Staff> staffCollection;
    
    @JsonIgnore
    @Transient
    private boolean check= true;
    
    public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

    
}
