package com.fujitsu.cloudlab.offer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.fujitsu.cloudlab.commons.exception.ApiErrorHandler;

@SpringBootApplication
@EnableCaching
@ImportAutoConfiguration({ApiErrorHandler.class})
public class OfferComposerApplication {

  public static void main(String[] args) {
    SpringApplication.run(OfferComposerApplication.class, args);
  }
}
