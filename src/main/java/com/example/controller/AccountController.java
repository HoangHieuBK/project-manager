package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.RoleService;

@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
    public JavaMailSender emailSender;
	
	@GetMapping(value = "/account")
	public List<Account> getAllAccount() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		
		List<Account> listAccount = accountService.findAllAccount();
		return listAccount;
	}

	@GetMapping(value = "/account/delete/{id}")
	public String deleteAccount(@PathVariable int id, Model model,RedirectAttributes redirect) {
		accountService.deleteAccount(id);
		redirect.addFlashAttribute("notification","bạn đã xóa account thành công !");
		return "redirect:/account";
	}
	
	@GetMapping("/account/add")
	public ModelAndView add() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		model.addObject("username", name);
		
		model.addObject("account",new Account());
		model.addObject("roles",roleService.getAllRole());
		model.setViewName("accountform");
		return model;
	}

	@RequestMapping(value = "/account/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("account") Account account,RedirectAttributes redirect) {
		if (accountService.saveAccount(account) == null) {
			return new ModelAndView("redirect:/error/400");
		}
		accountService.saveAccount(account);
		 // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
         
        message.setTo(account.getAccountName());
        message.setSubject("Account has been created !");
        message.setText("Hello"+account.getAccountName() + ", \r\n" + 
        		"You have successfully action an account");
        // Send Message!
        this.emailSender.send(message);
        redirect.addFlashAttribute("notification","bạn đã lưu account thành công !");
		return new ModelAndView("redirect:/account");
	}
	
	@GetMapping("/account/{id}/edit")
	public ModelAndView edit(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		Account account = accountService.getAccountByID(id);
		account.setCheck(false);
		modelAndView.addObject("account", account);
		modelAndView.addObject("roles",roleService.getAllRole());
        modelAndView.setViewName("accountform");
		return modelAndView;
	}

}
