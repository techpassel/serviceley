package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoStaff;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainMessageResponseDto {
    private Long id;
    private Long complainId;
    // We are returning complainId only and not other details of complain because most of the time
    // we will show complain message inside complain itself. So information about complain won't be
    // required for display purposes. However, we are sending complainId it might be useful in cases
    // like edit, delete etc.
    private DtoUser user;
    private UserType userType;
    private boolean isInternalMessage;
    private DtoStaff messageFor;
    //'messageForStaffId' will be used for staffs only.
    private String message;
    private String file1;
    private String file2;
    private String file3;
}
