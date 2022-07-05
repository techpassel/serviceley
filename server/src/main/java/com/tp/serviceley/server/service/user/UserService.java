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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
            if (userDefaultAddress != null && userDefaultAddress.getId() != addressRequestDto.getId()) {
                userDefaultAddress.setIsDefaultAddress(false);
                addressRepository.save(userDefaultAddress);
            }
        } else {
            if (addressRequestDto.getId() != null) {
                addressRequestDto.setIsDefaultAddress(userDefaultAddress.getId() == addressRequestDto.getId());
            } else {
                addressRequestDto.setIsDefaultAddress(userDefaultAddress == null);
            }
        }

        Address address = addressMapper.mapToModel(addressRequestDto, user);
        Address createdAddress = addressRepository.save(address);
        if(user.getOnboardingState() < 2){
            user.setOnboardingState(2);
            userRepository.save(user);
        }
        return addressMapper.mapToDto(createdAddress);
    }

    public List<AddressResponseDto> getUserAddresses(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException("User with given userId not found."));
        List<Address> addresses = addressRepository.findByUser(user);
        return addresses.stream().map(addressMapper::mapToDto).collect(Collectors.toList());
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

    public UpdateUserDto getUserDetails(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BackendException("User not found."));
        return UpdateUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dob(user.getDob())
                .gender(user.getGender())
                .userType(user.getUserType())
                .build();
    }

    public UpdateUserDto updateUser(UpdateUserDto updateUserDto) {
        Long userId = updateUserDto.getId();
        if (userId == null) {
            throw new BackendException("User id can't be null.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException("User not found."));
        if (updateUserDto.getEmail() != null && user.getEmail() != updateUserDto.getEmail())
            throw new BackendException("This Api is not meant for updating email.");
        if(updateUserDto.getPhone() != null && user.getPhone() != updateUserDto.getPhone() &&
                user.getOnboardingState() > 2){
            throw new BackendException("This Api is not meant for updating phone.");
        }
        if (updateUserDto.getFirstName() != null) user.setFirstName(updateUserDto.getFirstName());
        if (updateUserDto.getLastName() != null) user.setLastName(updateUserDto.getLastName());
        if (updateUserDto.getGender() != null) user.setGender(updateUserDto.getGender());
        if (updateUserDto.getUserType() != null) user.setUserType(updateUserDto.getUserType());
        if (updateUserDto.getDob() != null) user.setDob(updateUserDto.getDob());
        if (updateUserDto.getPhone() != null) user.setPhone(updateUserDto.getPhone());
        if (user.getOnboardingState() == 0) {
            user.setOnboardingState(1);
        }
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

    public String sendMobileVerificationToken(Long userId, String phone) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        if (user.isPhoneVerified() == false && user.getPhone() != phone) {
            user.setPhone(phone);
            userRepository.save(user);
        }
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByUserAndTokenType
                (user, TokenType.PhoneVerificationOTP);
        if (verificationToken.isPresent()) {
            VerificationToken verToken = verificationToken.get();
            if (LocalDateTime.now().isBefore(verToken.getCreatedAt().plusMinutes(1))) {
                throw new BackendException("Can't send another OTP now as its not been even 2 minutes " +
                        "when we sent the previous OTP on your number.");
            } else {
                verificationTokenRepository.deleteById(verToken.getId());
            }
        }
        Integer num = ThreadLocalRandom.current().nextInt(100001, 999999);
        smsService.sendOtp(user, TokenType.PhoneVerificationOTP, phone, num);
        //return "An OTP for phone verification is sent successfully on the provided number.";
        /*
            Actually we should return above response but since on production server we can't send otp.
            So as temporary workaround we will return otp to the client and will display it on the screen to users.
            So that they can complete the required steps and can proceed further.
         */
        return num.toString();
    }

    public String verifyMobileVerificationToken(Long userId, Long otp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        VerificationToken verificationToken = verificationTokenRepository.findByUserAndTokenType(user,
                TokenType.PhoneVerificationOTP).orElseThrow(() -> new BackendException
                ("User request token for mobile number updation not found."));
        boolean isTokenExpired = verificationToken.getUpdatedAt().plusMinutes(15).isBefore(LocalDateTime.now());
        if (verificationToken.getToken().equals(otp.toString()) && !isTokenExpired) {
            String phone = verificationToken.getUpdatingValue();
            user.setPhone(phone);
            user.setPhoneVerified(true);
            userRepository.save(user);
            verificationTokenRepository.deleteById(verificationToken.getId());
        } else {
            if(isTokenExpired){
                throw new BackendException("OTP expired. Please resend again.");
            } else {
                throw new BackendException("Invalid OTP. Please check the OTP or resend again.");
            }
        }
        if(user.getOnboardingState() == 2){
            user.setOnboardingState(3);
            userRepository.save(user);
        }
        return "Mobile number verified and updated successfully.";
    }

    public String sendUpdateEmailVerificationToken(Long userId, String email) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException
                ("User with given id not found."));
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByUserAndTokenType(user,
                TokenType.EmailUpdateVerification);
        if (verificationToken.isPresent()) {
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

    private void sendUpdateEmailVerificationEmail(User user, String token) {
        String url = "http://localhost:8080/api/user/update-email/" + token;
        String btnName = "Verify";
        String text = "Please click on the button below to verify your email and register it with your account in serviceley.";
        String msg = mailContentBuilder.build(text, url, btnName);

        String subject = "Please Verify your email.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    public String verifyUpdateEmailVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new
                BackendException("Invalid Token."));
        User user = verificationToken.getUser();
        user.setEmail(verificationToken.getUpdatingValue());
        userRepository.save(user);
        verificationTokenRepository.deleteById(verificationToken.getId());
        return "Email verified and updated successfully.";
    }

    public String deactivateUser(Long userId, String reason) {
        User currentUser = commonService.getCurrentUser();
        if (!currentUser.getId().equals(userId) && currentUser.getUserType() != UserType.Staff) {
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