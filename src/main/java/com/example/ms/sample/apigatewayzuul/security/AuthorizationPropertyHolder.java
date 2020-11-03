package com.example.ms.sample.apigatewayzuul.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorizationPropertyHolder {

    private String authorizationTokenHeaderName;
    private String authorizationTokenHeaderPrefix;
    private String tokenSecret;


}
