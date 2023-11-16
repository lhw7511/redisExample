package com.example.redisExample.controller;

import com.example.redisExample.entity.Member;
import com.example.redisExample.service.RedisMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisMemberService redisMemberService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getMemberInfo(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(redisMemberService.getMemberInfo(memberId));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody Map<String, String> memberInfo) {
        Member member = new Member();
        member.setName(memberInfo.get("name"));
        redisMemberService.join(member);
        return ResponseEntity.ok("가입 완료");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMember(@RequestBody Map<String, String> memberInfo) {
        System.out.println(memberInfo);
        Member member = new Member();
        member.setId(Long.parseLong(memberInfo.get("id")));
        member.setName(memberInfo.get("name") + "update");
        redisMemberService.update(member);
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId) {
        redisMemberService.removeMember(memberId);
        return ResponseEntity.ok("삭제 완료");
    }
}
