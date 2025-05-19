package com.jason.foody.service;

import com.jason.foody.entity.Member;
import com.jason.foody.entity.Role;
import com.jason.foody.exception.InvalidIdException;
import com.jason.foody.exception.InvalidOperationException;
import com.jason.foody.repository.MemberRepository;
import com.jason.foody.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UUID saveMember(Member member) throws InvalidOperationException {
        member.setCreated(LocalDateTime.now());
        List<Role> roles = new ArrayList<>();

        for (Role role : member.getRoles()) {
            Optional<Role> fetchedRole = roleRepository.findByRole(role.getRole());
            if (fetchedRole.isPresent()) {
                roles.add(fetchedRole.get());
            } else {
                throw new InvalidOperationException("Role " + role.getRole() + " not found");
            }
        }
        member.setRoles(roles);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(UUID id) throws InvalidIdException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("There is no member with id: " + id));
    }

    public Member updateMember(Member member) throws InvalidIdException, InvalidOperationException {
        Member savedMember = getMemberById(member.getId());
        if (!savedMember.getEmail().equals(member.getEmail())) {
            throw new InvalidOperationException("Email id cannot be modified");
        }
        member.setModified(LocalDateTime.now());
        return memberRepository.save(member);
    }

    public void removeMember(Member member) throws InvalidIdException {
        Member savedMember = getMemberById(member.getId());
        memberRepository.delete(savedMember);
    }

}
