
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
@JsonIgnoreProperties(value = { "staffProject" })
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByStaffId", query = "SELECT s FROM Staff s WHERE s.staffId = :staffId"),
    @NamedQuery(name = "Staff.findByName", query = "SELECT s FROM Staff s WHERE s.name = :name"),
    @NamedQuery(name = "Staff.findByGender", query = "SELECT s FROM Staff s WHERE s.gender = :gender"),
    @NamedQuery(name = "Staff.findByPossition", query = "SELECT s FROM Staff s WHERE s.possition = :possition"),
    @NamedQuery(name = "Staff.findBySkill", query = "SELECT s FROM Staff s WHERE s.skill = :skill"),
    @NamedQuery(name = "Staff.findByTelephone", query = "SELECT s FROM Staff s WHERE s.telephone = :telephone"),
    @NamedQuery(name = "Staff.findByDescription", query = "SELECT s FROM Staff s WHERE s.description = :description")})
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staff_id")
    private Integer staffId;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "possition")
    private String possition;
    
    @Column(name = "skill")
    private String skill;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "description")
    private String description;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "staff_project",
        joinColumns = @JoinColumn(name = "staff_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Collection<Project> staffProject;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffId")
    @Nullable
	private Collection<Task> task;
    
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne(optional = false)
    private Department departmentId;
    
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne(optional = false)
    private Account accountId;

}
