package com.jason.foody.controller;

import com.jason.foody.entity.Member;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.exception.InvalidOperationException;
import com.jason.foody.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/sign-up")
    ResponseEntity<UUID> signUp(@Valid @RequestBody Member member) throws InvalidOperationException{
        UUID id = memberService.saveMember(member);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    ResponseEntity<List<Member>> getAllMembers(){
        return new ResponseEntity<>(memberService.getAllMembers(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<Member> getMemberById(@PathVariable UUID id) throws InvalidIdException {
        Member member = memberService.getMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/update")
    ResponseEntity<Member> updateMember(@Valid @RequestBody Member member) throws InvalidOperationException, InvalidIdException {
        Member updatedMember = memberService.updateMember(member);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteMember(@RequestBody Member member) throws InvalidIdException {
        memberService.removeMember(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
