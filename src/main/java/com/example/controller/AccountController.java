package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepo;
import com.example.demo.service.AccountService;
import com.example.demo.service.RoleService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping()
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
    public JavaMailSender emailSender;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@GetMapping(value = "/account")
	public List<Account> getAllAccount() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
		
		List<Account> listAccount = accountService.findAllAccount();
		return listAccount;
	}

	@DeleteMapping(value = "/account/delete/{id}")
	public ResponseEntity<String>  deleteAccount(@PathVariable("id") int id) {
		System.out.println("Delete account with ID = " + id + "...");
		accountService.deleteAccount(id);
		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}
	
	@PostMapping("/account/add")
	public void  add(@RequestBody Account account) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		model.addObject("username", name);
//		
//		model.addObject("account",new Account());
//		model.addObject("roles",roleService.getAllRole());
//		model.setViewName("accountform");
//		return model;
		
		accountService.saveAccount(account);
		
		 // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
         
        message.setTo(account.getAccountName());
        message.setSubject("Account has been created !");
        message.setText("Hello"+account.getAccountName() + ", \r\n" + 
        		"You have successfully action an account");
        // Send Message!
        this.emailSender.send(message);

	}

//	@RequestMapping(value = "/account/save", method = RequestMethod.POST)
//	public ModelAndView save(@ModelAttribute("account") Account account,RedirectAttributes redirect) {
//		if (accountService.saveAccount(account) == null) {
//			return new ModelAndView("redirect:/error/400");
//		}
//		accountService.saveAccount(account);
//		 // Create a Simple MailMessage.
//        SimpleMailMessage message = new SimpleMailMessage();
//         
//        message.setTo(account.getAccountName());
//        message.setSubject("Account has been created !");
//        message.setText("Hello"+account.getAccountName() + ", \r\n" + 
//        		"You have successfully action an account");
//        // Send Message!
//        this.emailSender.send(message);
//        redirect.addFlashAttribute("notification","bạn đã lưu account thành công !");
//		return new ModelAndView("redirect:/account");
//	}
	
	@PutMapping("/account/{id}/edit")
	public ResponseEntity<Account> edit(@PathVariable("id") int id, @RequestBody Account account) {
//		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
//		Account account = accountService.getAccountByID(id);
//		account.setCheck(false);
//		modelAndView.addObject("account", account);
//		modelAndView.addObject("roles",roleService.getAllRole());
//        modelAndView.setViewName("accountform");
//		return modelAndView;
		
		System.out.println("Update Account with ID = " + id + "...");
		
		Optional<Account> accountData = accountRepo.findById(id);
		
	    if (accountData.isPresent()) {
	    	Account _account = accountData.get();
	    	_account.setAccountName(account.getAccountName());
	    	_account.setPassword(account.getPassword());
	    	_account.setRoleId(account.getRoleId());
	        return new ResponseEntity<>(accountRepo.save(_account), HttpStatus.OK);
	      } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	      }
	}

}
