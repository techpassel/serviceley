package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.StaffRequestDto;
import com.tp.serviceley.server.dto.StaffResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.StaffMapper;
import com.tp.serviceley.server.model.Staff;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.repository.StaffRepository;
import com.tp.serviceley.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final StaffMapper staffMapper;

    public StaffResponseDto createStaff(StaffRequestDto staffRequestDto){
        User user = userRepository.findById(staffRequestDto.getUserId()).orElseThrow(() -> new
                BackendException("User with given id not found."));
        Staff staff = staffMapper.mapToModel(staffRequestDto, user);
        Staff createdStaff = staffRepository.save(staff);
        return staffMapper.mapToDto(createdStaff);
    }

    public void deleteStaff(Long id){
        try {
            staffRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Staff with given id doesn't exist.", e);
        }
    }
}
