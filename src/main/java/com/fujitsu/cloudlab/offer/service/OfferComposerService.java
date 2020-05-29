package com.fujitsu.cloudlab.offer.service;

import com.fujitsu.cloudlab.commons.exception.ApiException;
import com.fujitsu.cloudlab.offer.Util.OfferComposerUtil;
import com.fujitsu.cloudlab.offer.json.model.OffersList;
import com.fujitsu.cloudlab.offer.json.model.Product;
import com.fujitsu.cloudlab.offer.json.model.SearchCriteria;
import com.fujitsu.cloudlab.offer.repository.OfferDataRepository;
import com.google.gson.Gson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OfferComposerService {

	@Value("${offer.exp.duration}")
	  private long expTime;

	@Autowired OfferDataRepository offerDataRepository;
	@Autowired OfferComposerUtil offerComposerUtil; 
  

  public OffersList createOffer(SearchCriteria searchCriteria) throws ApiException {
	  OffersList offersList = offerComposerUtil.composeOffer(searchCriteria);
    offerDataRepository.save(offersList);
    return offersList;
  }

public List<Product> getProducts(String transactionId) {
	return offerComposerUtil.searchProducts();
}
}
