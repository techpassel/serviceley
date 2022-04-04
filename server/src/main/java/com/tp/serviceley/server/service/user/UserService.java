package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.AddressRequestDto;
import com.tp.serviceley.server.dto.AddressResponseDto;
import com.tp.serviceley.server.dto.UpdateUserDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.AddressMapper;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.enums.TokenType;
import com.tp.serviceley.server.model.enums.UserType;
import com.tp.serviceley.server.repository.AddressRepository;
import com.tp.serviceley.server.repository.DeactivatedUserRepository;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.repository.VerificationTokenRepository;
import com.tp.serviceley.server.service.CommonService;
import com.tp.serviceley.server.service.MailContentBuilder;
import com.tp.serviceley.server.service.MailService;
import com.tp.serviceley.server.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final SmsService smsService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final CommonService commonService;
    private final DeactivatedUserRepository deactivatedUserRepository;

    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        User user = userRepository.findById(addressRequestDto.getUserId()).orElseThrow(() -> new BackendException
                ("User with given userId not found."));

        //Every user must have one and only one default address. We have to ensure it.
        Address userDefaultAddress = addressRepository.findUserDefaultAddress(user);
        if (addressRequestDto.getIsDefaultAddress() != null && addressRequestDto.getIsDefaultAddress() == true) {
            if (userDefaultAddress != null) {
                userDefaultAddress.setIsDefaultAddress(false);
                addressRepository.save(userDefaultAddress);
            }
        } else {
            if(addressRequestDto.getId() != null){
                if(userDefaultAddress.getId() == addressRequestDto.getId()){
                    addressRequestDto.setIsDefaultAddress(true);
                } else {
                    addressRequestDto.setIsDefaultAddress(false);
                }
            } else {
                if(userDefaultAddress == null){
                    addressRequestDto.setIsDefaultAddress(true);
                } else {
                    addressRequestDto.setIsDefaultAddress(false);
                }
            }
        }

        Address address = addressMapper.mapToModel(addressRequestDto, user);
        Address createdAddress = addressRepository.save(address);
        return addressMapper.mapToDto(createdAddress);
    }

    public void deleteAddress(Long id) {
        try {
            Optional<Address> optionalAddress = addressRepository.findById(id);
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                if (address.getIsDefaultAddress()) {
                    throw new BackendException("This is your default address. Please declare some other address as " +
                            "default first to delete this address.");
                }
            } else {
                throw new BackendException("Address with given id not found.");
            }
            addressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Staff with given id doesn't exist.", e);
        }
    }

    public UpdateUserDto updateUser(UpdateUserDto updateUserDto){
        Long userId = updateUserDto.getId();
        if(userId == null){
            throw new BackendException("User id can't be null.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException("User not found."));
        if(user.getEmail() != updateUserDto.getEmail() || user.getPhone() != updateUserDto.getPhone()) throw
                new BackendException("This Api is not meant for updating email phone. Please use it to " +
                        "update other fields only.");
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setGender(updateUserDto.getGender());
        user.setUserType(updateUserDto.getUserType());
        user.setGender(updateUserDto.getGender());
        user.setPhone(updateUserDto.getPhone());
        User updatedUser = userRepository.save(user);
        return UpdateUserDto.builder()
                .id(updatedUser.getId())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .email(updatedUser.getEmail())
                .phone(updatedUser.getPhone())
                .gender(updatedUser.getGender())
                .userType(updatedUser.getUserType())
                .build();
    }

    public String sendMobileVerificationToken(Long userId, Long phone){
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        if(user.getPhone() == null || user.getPhone() != phone){
            user.setPhone(phone);
            userRepository.save(user);
        }
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByUserAndTokenType(user, TokenType.PhoneVerificationOTP);
        if(verificationToken.isPresent()){
            VerificationToken verToken = verificationToken.get();
            if(LocalDateTime.now().isBefore(verToken.getCreatedAt().plusMinutes(2))){
                throw new BackendException("Can't send another OTP now as its not been even 2 minutes " +
                        "when we sent the previous OTP on your number.");
            } else {
                verificationTokenRepository.deleteById(verToken.getId());
            }
        }
        smsService.sendOtp(user, TokenType.PhoneVerificationOTP, phone);
        return "An OTP for phone verification is sent successfully on the provided number.";
    }

    public String verifyMobileVerificationToken(Long userId, Long otp){
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        VerificationToken verificationToken = verificationTokenRepository.findByUserAndTokenType(user,
                TokenType.PhoneVerificationOTP).orElseThrow(() -> new BackendException
                ("User doesn't have requested any mobile verification token."));
        if(verificationToken.getToken().equals(otp.toString())){
            Long phone = Long.valueOf(verificationToken.getUpdatingValue());
            user.setPhone(phone);
            user.setPhoneVerified(true);
            userRepository.save(user);
            verificationTokenRepository.deleteById(verificationToken.getId());
        } else {
            throw new BackendException("Invalid OTP. Please check the OTP or resend again.");
        }
        return "Mobile number verified and updated successfully.";
    }

    public String sendUpdateEmailVerificationToken(Long userId, String email){
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByUserAndTokenType(user,
                TokenType.EmailUpdateVerification);
        if(verificationToken.isPresent()){
            VerificationToken verToken = verificationToken.get();
            verificationTokenRepository.deleteById(verToken.getId());
        }
        String token = commonService.generateVerificationToken(user, TokenType.AccountActivation);
        VerificationToken newVerificationToken = new VerificationToken();
        newVerificationToken.setToken(token);
        newVerificationToken.setTokenType(TokenType.EmailUpdateVerification);
        newVerificationToken.setUpdatingValue(email);
        newVerificationToken.setUser(user);
        verificationTokenRepository.save(newVerificationToken);
        sendUpdateEmailVerificationEmail(user, token);
        return "A verification email has been sent on the provided email.Your email will be updated after you verify the email";
    }

    private void sendUpdateEmailVerificationEmail(User user, String token){
        String url = "http://localhost:8080/api/user/update-email/"+token;
        String btnName = "Verify";
        String text = "Please click on the button below to verify your email and register it with your account in serviceley.";
        String msg = mailContentBuilder.build(text, url, btnName);

        String subject = "Please Verify your email.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    public String verifyUpdateEmailVerificationToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new
                BackendException("Invalid Token."));
        User user = verificationToken.getUser();
        user.setEmail(verificationToken.getUpdatingValue());
        userRepository.save(user);
        verificationTokenRepository.deleteById(verificationToken.getId());
        return "Email verified and updated successfully.";
    }

    public String deactivateUser(Long userId, String reason){
        User currentUser = commonService.getCurrentUser();
        if(!currentUser.getId().equals(userId) && currentUser.getUserType() != UserType.Staff){
            throw new BackendException("You don't have enough permission to deactivate this user.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        user.setActive(false);
        userRepository.save(user);
        deactivatedUserRepository.save(DeactivatedUser.builder()
                .user(user)
                .deactivatedBy(currentUser)
                .reason(reason)
                .build()
        );
        return "User deactivated successfully.";
    }
}