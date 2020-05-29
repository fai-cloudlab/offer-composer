package com.fujitsu.cloudlab.offer.repository;

import com.fujitsu.cloudlab.offer.json.model.Offer;
import com.fujitsu.cloudlab.offer.json.model.OffersList;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class OfferDataRepository {
	@Value("${spring.cache.redis.time-to-live}")
	  private long expTime;
 
  private ValueOperations<String,Offer> valueOperations;

  @Autowired
  OfferDataRepository(RedisTemplate<String, Offer> redisTemplate){
	  this.valueOperations = redisTemplate.opsForValue();
  }

  public void save(OffersList offersList) {
	  offersList.getOffers().forEach(offer -> valueOperations.set(offer.getOfferId().toString(), offer, expTime, TimeUnit.MILLISECONDS));
  }
  
}
