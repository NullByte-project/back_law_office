package com.example.back_law_office.controllers;

import com.example.back_law_office.models.ApprovalCode;
import com.example.back_law_office.services.ApprovalCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approval-codes")
public class ApprovalCodeController {

    @Autowired
    private ApprovalCodeService approvalCodeService;

    @PostMapping("/generate")
    public ResponseEntity<ApprovalCode> createApprovalCode() {
        ApprovalCode approvalCode = approvalCodeService.createApprovalCode();
        return ResponseEntity.status(HttpStatus.CREATED).body(approvalCode);
    }
}