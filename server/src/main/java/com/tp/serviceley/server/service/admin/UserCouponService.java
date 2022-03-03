package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.UserCouponRequestDto;
import com.tp.serviceley.server.dto.UserCouponResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.UserCouponMapper;
import com.tp.serviceley.server.model.Coupon;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.UserCoupon;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.repository.CouponRepository;
import com.tp.serviceley.server.repository.UserCouponRepository;
import com.tp.serviceley.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserCouponService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserCouponMapper userCouponMapper;

    public List<UserCouponResponseDto> assignUserCoupon(UserCouponRequestDto userCouponsData){
        List<UserCoupon> userCoupons = new ArrayList<>();
        Coupon coupon = couponRepository.getById(userCouponsData.getCouponId());
        userCouponsData.getUserIds().forEach(v -> {
            User user = userRepository.getById(v);
            UserCoupon userCoupon = new UserCoupon(user, coupon, userCouponsData.getTotalAllowedUses());
            userCoupons.add(userCoupon);
        });
        List<UserCoupon> createdUserCoupons = userCouponRepository.saveAll(userCoupons);

        List<UserCouponResponseDto> assignedUserCouponData = createdUserCoupons.stream().map(v ->
                userCouponMapper.mapToDto(v)).collect(Collectors.toList());
        return assignedUserCouponData;
    }

    public void deleteUserCoupon(Long id){
        try {
            userCouponRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("User coupon with given id doesn't exist.", e);
        }
    }

    public List<DtoUser> getCouponUsers(Long couponId){
        Coupon coupon = couponRepository.getById(couponId);
        List<UserCoupon> userCoupons = userCouponRepository.findByCoupon(coupon);
        List<DtoUser> users = userCoupons.stream().map(v -> new DtoUser(v.getUser().getId(), v.getUser().getFirstName(), v.getUser().getLastName())).collect(Collectors.toList());
        return users;
    }
}
