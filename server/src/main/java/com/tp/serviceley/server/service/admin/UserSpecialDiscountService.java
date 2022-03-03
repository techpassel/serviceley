package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.UserSpecialDiscountRequestDto;
import com.tp.serviceley.server.dto.UserSpecialDiscountResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.UserSpecialDiscountMapper;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.SpecialDiscountStatus;
import com.tp.serviceley.server.repository.SpecialDiscountRepository;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.repository.UserSpecialDiscountRepository;
import com.tp.serviceley.server.service.CommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserSpecialDiscountService {
    private final UserRepository userRepository;
    private final SpecialDiscountRepository specialDiscountRepository;
    private final UserSpecialDiscountMapper userSpecialDiscountMapper;
    private final CommonService commonService;
    private final UserSpecialDiscountRepository userSpecialDiscountRepository;

    public List<UserSpecialDiscountResponseDto> assignUserSpecialDiscount(UserSpecialDiscountRequestDto specialDiscountData){
        List<UserSpecialDiscount> userSpecialDiscounts = new ArrayList<>();
        SpecialDiscount specialDiscount = specialDiscountRepository.getById(specialDiscountData.getSpecialDiscountId());
        User currentUser = commonService.getCurrentUser();
        specialDiscountData.getUserIds().forEach(v -> {
            User user = userRepository.getById(v);
            UserSpecialDiscount usd = userSpecialDiscountMapper.mapToModel(specialDiscountData, user, specialDiscount, currentUser);
            userSpecialDiscounts.add(usd);
        });

        List<UserSpecialDiscount> assignedUserSpecialDiscounts = userSpecialDiscountRepository.saveAll(userSpecialDiscounts);
        return assignedUserSpecialDiscounts.stream().map(e -> userSpecialDiscountMapper.mapToDto(e)).collect(Collectors.toList());
    }

    public UserSpecialDiscountResponseDto approveRejectSpecialDiscount(Map<String, Object> data){
        Long id = (Long) data.get("id");
        SpecialDiscountStatus status = (SpecialDiscountStatus) data.get("status");
        String remark = (String) data.get("remark");
        UserSpecialDiscount userSpecialDiscount = userSpecialDiscountRepository.getById(id);
        User currentUser = commonService.getCurrentUser();
        userSpecialDiscount.setStatus(status);
        userSpecialDiscount.setApprovedBy(currentUser);
        userSpecialDiscount.setApprovalRemark(remark);
        UserSpecialDiscount usd = userSpecialDiscountRepository.save(userSpecialDiscount);
        return userSpecialDiscountMapper.mapToDto(usd);
    }

    public void deleteUserSpecialDiscount(Long id){
        try {
            userSpecialDiscountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("User special discount with given id doesn't exist.", e);
        }
    }

    public List<DtoUser> getSpecialDiscountUsers(Long specialDiscountId){
        SpecialDiscount specialDiscount = specialDiscountRepository.getById(specialDiscountId);
        List<UserSpecialDiscount> userSpecialDiscounts = userSpecialDiscountRepository.findBySpecialDiscount(specialDiscount);
        List<DtoUser> users = userSpecialDiscounts.stream().map(v -> new DtoUser(v.getUser().getId(), v.getUser().getFirstName(), v.getUser().getLastName())).collect(Collectors.toList());
        return users;
    }
}
