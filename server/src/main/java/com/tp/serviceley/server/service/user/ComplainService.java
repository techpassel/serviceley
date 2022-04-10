package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.ComplainMessageRequestDto;
import com.tp.serviceley.server.dto.ComplainMessageResponseDto;
import com.tp.serviceley.server.dto.ComplainRequestDto;
import com.tp.serviceley.server.dto.ComplainResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ComplainMapper;
import com.tp.serviceley.server.mapper.ComplainMessageMapper;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.repository.*;
import com.tp.serviceley.server.service.FileUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ComplainService {
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OrderRepository orderRepository;
    private final OrderBillingRepository orderBillingRepository;
    private final OrderItemRepository orderItemRepository;
    private final StaffRepository staffRepository;
    private final ComplainMessageRepository complainMessageRepository;
    private final ComplainMessageMapper complainMessageMapper;
    private final ComplainRepository complainRepository;
    private final ComplainMapper complainMapper;
    private final FileUploadService fileUploadService;

    public ComplainResponseDto createComplain(ComplainRequestDto complainRequestDto){
        User user = userRepository.findById(complainRequestDto.getUserId()).orElseThrow(() ->
                new BackendException("User not found."));
        ServiceProvider serviceProvider = null;
        Order order = null;
        OrderBilling orderBilling = null;
        OrderItem orderItem = null;
        Staff finalRemarkBy = null;

        if(complainRequestDto.getServiceProviderId() != null){
            serviceProvider = serviceProviderRepository.findById(complainRequestDto.getServiceProviderId())
                    .orElseThrow(() -> new BackendException("Service provider not found."));
        }
        if(complainRequestDto.getOrderId() != null){
            order = orderRepository.findById(complainRequestDto.getOrderId())
                    .orElseThrow(() -> new BackendException("Order not found."));
        }
        if(complainRequestDto.getOrderBillingId() != null){
            orderBilling = orderBillingRepository.findById(complainRequestDto.getOrderBillingId())
                    .orElseThrow(() -> new BackendException("Order billing not found."));
        }
        if(complainRequestDto.getOrderItemId() != null){
            orderItem = orderItemRepository.findById(complainRequestDto.getOrderItemId())
                    .orElseThrow(() -> new BackendException("order item not found."));
        }
        if(complainRequestDto.getFinalRemarkByStaffId() != null){
            finalRemarkBy = staffRepository.findById(complainRequestDto.getFinalRemarkByStaffId())
                    .orElseThrow(() -> new BackendException("Staff with given finalRemarkByStaffId not found."));
        }
        List<ComplainMessage> messages = new ArrayList<ComplainMessage>();
        if(complainRequestDto.getId() != null){
            //i.e It is update complain request
            Complain complain = complainRepository.findById(complainRequestDto.getId()).orElseThrow(() ->
                    new BackendException("Complain not found"));
            messages = complain.getMessages();
        }
        Complain complain = complainRepository.save(complainMapper.mapToModel(complainRequestDto, user, serviceProvider,
                order, orderBilling, orderItem, finalRemarkBy, messages));

        if(complainRequestDto.getId() == null){
            //i.e It is create complain request
            String keyName= "complain/"+complain.getId();
            String file1 = fileUploadService.getUploadedFileString(keyName+"/file1",
                    complainRequestDto.getFirstMessage().getFile1());
            String file2 = fileUploadService.getUploadedFileString(keyName+"/file2",
                    complainRequestDto.getFirstMessage().getFile1());
            String file3 = fileUploadService.getUploadedFileString(keyName+"/file3",
                    complainRequestDto.getFirstMessage().getFile1());
            ComplainMessage complainMessage = complainMessageRepository.save(complainMessageMapper.mapToModel
                    (complainRequestDto.getFirstMessage(), complain, user, null, file1, file2, file3));
            complain.setMessages(Arrays.asList(new ComplainMessage[]{complainMessage}));
            complain = complainRepository.save(complain);
        }
        return complainMapper.mapToDto(complain);
    }

    public ComplainMessageResponseDto addMessageToComplain(ComplainMessageRequestDto complainMessageRequestDto){
        Complain complain = complainRepository.findById(complainMessageRequestDto.getComplainId())
                .orElseThrow(() -> new BackendException("Complain not found."));
        User user = userRepository.findById(complainMessageRequestDto.getUserId())
                .orElseThrow(() -> new BackendException("user not found."));
        Staff messageFor = staffRepository.findById(complainMessageRequestDto.getMessageForStaffId())
                .orElseThrow(() -> new BackendException("Staff with given messageForStaffId not found."));
        String keyName= "complain/"+complain.getId();
        String file1 = fileUploadService.getUploadedFileString(keyName+"/file1",
                complainMessageRequestDto.getFile1());
        String file2 = fileUploadService.getUploadedFileString(keyName+"/file2",
                complainMessageRequestDto.getFile1());
        String file3 = fileUploadService.getUploadedFileString(keyName+"/file3",
                complainMessageRequestDto.getFile1());
        ComplainMessage complainMessage = complainMessageRepository.save(complainMessageMapper.mapToModel
                (complainMessageRequestDto, complain, user, messageFor, file1, file2, file3));
        List<ComplainMessage> complainMessages = complain.getMessages();
        complainMessages.add(complainMessage);
        complain.setMessages(complainMessages);
        complainRepository.save(complain);
        return complainMessageMapper.mapToDto(complainMessage);
    }

    public void deleteComplain(Long id){
        try {
            complainRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Complain with given id doesn't exist.", e);
        }
    }

    public void deleteComplainMessage(Long id){
        try {
            complainMessageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Complain message with given id doesn't exist.", e);
        }
    }
}
