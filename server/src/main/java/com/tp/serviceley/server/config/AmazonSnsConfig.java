package com.tp.serviceley.server.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmazonSnsConfig {
    @Value("${snsAccessKey}")
    private String awsAccessKey;

    @Value("${snsSecretKey}")
    private String awsSecretKey;

    @Value("${snsRegion}")
    private String region;

    // @Primary annotation gives a higher preference to a bean (when there are multiple beans of the same type).
    @Primary
    // @Bean annotation tells that a method produces a bean that is to be managed by the spring container.
    @Bean
    public AmazonSNSClient amazonSNSClient() {
        return (AmazonSNSClient) AmazonSNSClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }
}
