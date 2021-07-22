package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.dao.CaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    private CaseMapper caseMapper;


}
