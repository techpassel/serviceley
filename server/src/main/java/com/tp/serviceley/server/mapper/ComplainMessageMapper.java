package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ComplainMessageRequestDto;
import com.tp.serviceley.server.dto.ComplainMessageResponseDto;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.DtoStaff;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ComplainMessageMapper {
    @Mapping(target = "id", source = "complainMessageRequestDto.id")
    @Mapping(target = "complain", source = "complain")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "messageFor", source = "messageFor")
    @Mapping(target = "userType", source = "complainMessageRequestDto.userType")
    @Mapping(target = "file1", source = "file1")
    @Mapping(target = "file2", source = "file2")
    @Mapping(target = "file3", source = "file3")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract ComplainMessage mapToModel(ComplainMessageRequestDto complainMessageRequestDto,
                                               Complain complain, User user, Staff messageFor, String file1,
                                               String file2, String file3);

    @Mapping(target = "complainId", expression = "java(getComplainId(complainMessage))")
    @Mapping(target = "user", expression = "java(getDtoUser(complainMessage))")
    @Mapping(target = "messageFor", expression = "java(getDtoStaff(complainMessage))")
    public abstract ComplainMessageResponseDto mapToDto(ComplainMessage complainMessage);

    Long getComplainId(ComplainMessage complainMessage){
        return complainMessage.getComplain().getId();
    }

    DtoUser getDtoUser(ComplainMessage complainMessage){
        User user = complainMessage.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    DtoStaff getDtoStaff(ComplainMessage complainMessage){
        Staff  staff = complainMessage.getMessageFor();
        DtoUser staffUser = new DtoUser(staff.getUser().getId(), staff.getUser().getFirstName(),
                staff.getUser().getLastName());
        return new DtoStaff(staff.getId(), staffUser);
    }
}
