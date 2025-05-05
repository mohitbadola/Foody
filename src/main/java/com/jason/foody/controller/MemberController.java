package com.jason.foody.controller;

import com.jason.foody.entity.Member;
import com.jason.foody.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/all")
    ResponseEntity<List<Member>> getAllMembers(){
        return new ResponseEntity<>(memberService.getAllMembers(),HttpStatus.OK);
    }
}
