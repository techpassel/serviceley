package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.ServiceProviderFileDto;
import com.tp.serviceley.server.dto.ServiceProviderRequestDto;
import com.tp.serviceley.server.dto.ServiceProviderResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceProviderMapper;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.repository.ServiceProviderRepository;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.service.FileUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceProviderService {
    ServiceProviderRepository serviceProviderRepository;
    ServiceProviderMapper serviceProviderMapper;
    FileUploadService fileUploadService;
    UserRepository userRepository;

    //private Logger logger = LoggerFactory.getLogger(ServiceProviderService.class);
    //Since we have used @Slf4j annotation, so we don't need to create Logger manually. Spring will do it for us.
    //But note that Spring will create Logger instance with name 'log' and not with name 'logger'.
    //So we can use Logger as "log.error" or "log.info" etc. directly without defining any variable with name "log".

    public ServiceProviderResponseDto createServiceProvider(ServiceProviderRequestDto serviceProviderRequestDto) {
        Long userId = serviceProviderRequestDto.getUserId();
        if (userId == null) {
            throw new BackendException("User id can't be null.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new BackendException("User not found"));
        if(user == null){
            throw new BackendException("User with given userId not found");
        }
        String keyName = "service-provider/" + userId;
        if (serviceProviderRequestDto.getId() != null) {
            if (serviceProviderRequestDto.getQualificationCertificate() != null ||
                    serviceProviderRequestDto.getImage1() != null ||
                    serviceProviderRequestDto.getImage2() != null ||
                    serviceProviderRequestDto.getImage3() != null ||
                    serviceProviderRequestDto.getIdProof() != null ||
                    serviceProviderRequestDto.getAddressProof() != null) {
                throw new BackendException("Updation of image, documents and certificates is not allowed through this api. " +
                        "You can use it for updating all fields except file type fields.");
            }
        }

        String qualificationCertificate = getUploadedFileString(keyName + "/cert", serviceProviderRequestDto.getQualificationCertificate());
        String image1 = getUploadedFileString(keyName + "/image1", serviceProviderRequestDto.getImage1());
        String image2 = getUploadedFileString(keyName + "/image2", serviceProviderRequestDto.getImage2());
        String image3 = getUploadedFileString(keyName + "/image3", serviceProviderRequestDto.getImage3());
        String idProof = getUploadedFileString(keyName + "/idProof", serviceProviderRequestDto.getIdProof());
        String addressProof = getUploadedFileString(keyName + "/addressProof", serviceProviderRequestDto.getAddressProof());
        ServiceProvider serviceProvider = serviceProviderMapper.mapToModel(serviceProviderRequestDto, user, qualificationCertificate,
                image1, image2, image3, idProof, addressProof);
        ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
        return serviceProviderMapper.mapToDto(createdServiceProvider);
    }

    //This API is meant for updating file type fields only.For update of rest fields above api will be used
    public ServiceProviderResponseDto updateServiceProviderFile(ServiceProviderFileDto serviceProviderFileDto) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderFileDto.getId()).
                orElseThrow(() -> new BackendException("Service provider not found"));
        String oldFilePath = null;
        String keyName = "service-provider/" + serviceProvider.getUser().getId();

        switch (serviceProviderFileDto.getKey()){
            case "qualificationCertificate":
                oldFilePath = serviceProvider.getQualificationCertificate();
                serviceProvider.setQualificationCertificate(getUploadedFileString(keyName+"/cert", serviceProviderFileDto.getFile()));
                break;
            case "image1":
                oldFilePath = serviceProvider.getImage1();
                serviceProvider.setImage1(getUploadedFileString(keyName+"/image1", serviceProviderFileDto.getFile()));
                break;
            case "image2":
                oldFilePath = serviceProvider.getImage2();
                serviceProvider.setImage2(getUploadedFileString(keyName+"/image2", serviceProviderFileDto.getFile()));
                break;
            case "image3":
                oldFilePath = serviceProvider.getImage3();
                serviceProvider.setImage3(getUploadedFileString(keyName+"/image3", serviceProviderFileDto.getFile()));
                break;
            case "idProof":
                oldFilePath = serviceProvider.getIdProof();
                serviceProvider.setIdProof(getUploadedFileString(keyName+"/idProof", serviceProviderFileDto.getFile()));
                break;
            case "addressProof":
                oldFilePath = serviceProvider.getAddressProof();
                serviceProvider.setAddressProof(getUploadedFileString(keyName+"/addressProof", serviceProviderFileDto.getFile()));
                break;
        }
        ServiceProvider updatedServiceProvider = serviceProviderRepository.save(serviceProvider);
        //Code to delete old files from s3
        if (oldFilePath != null) fileUploadService.deleteFile(oldFilePath);
        return serviceProviderMapper.mapToDto(updatedServiceProvider);
    }

    public String getUploadedFileString(String keyName, MultipartFile file) {
        try {
            if (file != null) {
                return fileUploadService.uploadFile(keyName, file);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public void deleteServiceProvider(Long id) {
        try {
            ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow(() -> new BackendException("Service provider not found."));
            Long userID = serviceProvider.getUser().getId();
            String keyName = "service-provider/" + userID;
            serviceProviderRepository.deleteById(id);
            //userRepository.deleteById(userID);
            /*
                We have commented above code as we don't need to delete ServiceProvider user explicitly
                Whenever we will remove a service provider its corresponding user will also be removed.
                It is because we have defined "cascade = CascadeType.REMOVE" in their relationship.
             */
            //Code to delete all files of the service provider using keyName is needed to be implemented.
            fileUploadService.deleteFolderAllFiles(keyName);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service unit with given id doesn't exist.", e);
        }
    }


}