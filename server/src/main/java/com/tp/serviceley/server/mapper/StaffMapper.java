package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.StaffRequestDto;
import com.tp.serviceley.server.dto.StaffResponseDto;
import com.tp.serviceley.server.model.Staff;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class StaffMapper {
    @Mapping(target = "id", source = "staffRequestDto.id")
    @Mapping(target = "user", source = "user")
    public abstract Staff mapToModel(StaffRequestDto staffRequestDto, User user);

    @Mapping(target = "user", expression = "java(getDtoUser(staff))")
    public abstract StaffResponseDto mapToDto(Staff staff);

    public DtoUser getDtoUser(Staff staff){
        User user = staff.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }
}
