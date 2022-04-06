package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.Complain;
import com.tp.serviceley.server.model.Staff;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainMessageRequestDto {
    private Long id;
    private Long complainId;
    private Long userId;
    private UserType userType;
    private boolean isInternalMessage;
    private Long messageForStaffId;
    //'messageForStaffId' will be used for staffs only.
    private String message;
    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;
}
