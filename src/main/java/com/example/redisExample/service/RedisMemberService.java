package com.example.redisExample.service;

import com.example.redisExample.Repository.RedisMemberRepository;
import com.example.redisExample.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RedisMemberService {

    private final RedisMemberRepository redisMemberRepository;

    public void join(Member member){
        redisMemberRepository.save(member);
    }

    @CachePut(value = "Member", key = "#member.id", cacheManager = "cacheManager")
    public Member update(Member member){
        redisMemberRepository.save(member);
        return redisMemberRepository.findById(member.getId()).get();
    }

    @Cacheable(value = "Member", key = "#memberId", cacheManager = "cacheManager",unless = "#result == null")
    public Member getMemberInfo(Long memberId){
        return redisMemberRepository.findById(memberId).get();
    }

    @CacheEvict(value = "Member", key = "#memberId", cacheManager = "cacheManager")
    public void removeMember(Long memberId){
        redisMemberRepository.delete(redisMemberRepository.findById(memberId).get());
    }

}
