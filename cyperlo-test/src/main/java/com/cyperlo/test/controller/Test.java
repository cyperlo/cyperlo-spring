package com.cyperlo.test.controller;

import com.cyperlo.common.utils.RSAUtil;
import com.cyperlo.test.entity.RsaDTO;
import com.cyperlo.web.config.SpringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/test")
public class Test {

    @Autowired
    SpringHelper springHelper;

    @GetMapping(path = "/getKeyPair")
    public String[] getKeyPair() {
        return RSAUtil.generateKeyPair();
    }

    // 测试加密
    @GetMapping(path = "/testEncrypt")
    public String testEncrypt(@RequestParam String content) {
        return RSAUtil.encrypt(content, springHelper.getProperty("encrypt.rsa.public-key"));
    }

    @PostMapping(path = "/testDecrypt")
    public String testDecrypt(@RequestBody RsaDTO dto) {
        return RSAUtil.decrypt(dto.getContent(), springHelper.getProperty("encrypt.rsa.private-key"));
    }
}
