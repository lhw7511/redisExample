package com.example.redisExample.Repository;

import com.example.redisExample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisMemberRepository extends JpaRepository<Member,Long> {
}
