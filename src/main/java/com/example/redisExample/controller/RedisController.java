package com.example.redisExample.controller;

import com.example.redisExample.entity.Member;
import com.example.redisExample.service.RedisMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisMemberService redisMemberService;

    private final RedisTemplate<String ,Object> redisTemplate;

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

    @GetMapping("/templateTest")
    public void templateTest(){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("testKey","testValue");
    }


    @GetMapping("/templateListTest")
    public void templateListTest(){
        ListOperations<String, Object> stringObjectListOperations = redisTemplate.opsForList();

        stringObjectListOperations.rightPush("listTest","h");
        stringObjectListOperations.rightPush("listTest","e");
        stringObjectListOperations.rightPush("listTest","l");
        stringObjectListOperations.rightPush("listTest","l");
        stringObjectListOperations.rightPush("listTest","o");

        stringObjectListOperations.rightPushAll("listTest"," ","w","o","r","l","d");

        String character_1 = (String) stringObjectListOperations.index("listTest", 1);

        System.out.println("character_1 = " + character_1);

        Long size = stringObjectListOperations.size("listTest");

        System.out.println("size = " + size);

        List<Object> ResultRange = stringObjectListOperations.range("listTest", 0, 10);

        System.out.println("ResultRange = " + Arrays.toString(ResultRange.toArray()));

    }

    @GetMapping("/templateHashTest")
    public void templateHashTest(){
        String key = "testHash";

        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

        stringObjectObjectHashOperations.put(key, "Hello", "helloVal");
        stringObjectObjectHashOperations.put(key, "Hello2", "helloVal2");
        stringObjectObjectHashOperations.put(key, "Hello3", "helloVal3");

        Object hello = stringObjectObjectHashOperations.get(key, "Hello");

        System.out.println("hello = " + hello);

        Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);

        System.out.println("entries = " + entries.get("Hello2"));

        Long size = stringObjectObjectHashOperations.size(key);

        System.out.println("size = " + size);
    }
}
