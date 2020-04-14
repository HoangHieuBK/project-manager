package com.example.demo.dto;

import java.util.Collection;

import com.example.demo.entity.Role;
import com.example.demo.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Integer accountId;

    private String accountName;

    private String password;
    
    private Collection<Staff> staffCollection;
    
    private Role roleId;
    
    private boolean check = true;

    
}
