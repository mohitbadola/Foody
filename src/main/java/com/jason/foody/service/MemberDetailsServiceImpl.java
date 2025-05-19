package com.jason.foody.service;

import com.jason.foody.entity.Member;
import com.jason.foody.repository.MemberRepository;
import com.jason.foody.security.MyMemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(username);
        if(member.isPresent()){
            return new MyMemberDetails(member.get());
        }else {
            throw new UsernameNotFoundException("Invalid username: " + username + ".");
        }
    }
}
