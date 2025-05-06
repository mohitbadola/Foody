package com.jason.foody.service;

import com.jason.foody.entity.Member;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.exception.InvalidOperationException;
import com.jason.foody.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public UUID saveMember(Member member){
        member.setCreated(LocalDateTime.now());
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member getMemberById(UUID id) throws InvalidIdException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("There is no member with id: " + id));
    }

    public void updateMember(Member member) throws InvalidIdException, InvalidOperationException {
        Member savedMember = getMemberById(member.getId());
        if(!savedMember.getEmail().equals(member.getEmail())){
            throw new InvalidOperationException("Email id cannot be modified");
        }
        savedMember.setModified(LocalDateTime.now());
        memberRepository.save(member);
    }

    public void removeMember(Member member) throws InvalidIdException {
        Member savedMember = getMemberById(member.getId());
        memberRepository.delete(savedMember);
    }

}
