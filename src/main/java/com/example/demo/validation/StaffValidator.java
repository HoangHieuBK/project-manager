//package com.example.demo.validation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//import com.example.demo.entity.Staff;
//import com.example.demo.service.StaffService;
//
//@Component
//public class StaffValidator implements Validator{
//    @Autowired
//    private StaffService userService;
//    
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return Staff.class.equals(aClass);
//    }
//    @Override
//    public void validate(Object o, Errors errors) {
//    	Staff user = (Staff) o;
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
//        if (user.getUsename().length() < 6 || user.getUsename().length() > 32) {
//            errors.rejectValue("username", "Size.userForm.username");
//        }
//        if (userService.findByUsername(user.getUsename()) != null) {
//            errors.rejectValue("username", "Duplicate.userForm.username");
//        }
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
//        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.userForm.password");
//        }
//
////        if (!user.getPasswordConfirm().equals(user.getPassword())) {
////            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
////        }
//    }
//
//}
