package io.jus.hopegaarden.utils;

import io.jus.hopegaarden.domain.define.member.Member;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    public static Map<String, String> createTokenMap(Member member) {
        HashMap<String, String> map = new HashMap<>();

        map.put("role", String.valueOf(member.getRole()));
        map.put("nickname", String.valueOf(member.getNickname()));
        map.put("password", String.valueOf(member.getPassword()));

        return map;
    }
}
