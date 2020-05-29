package com.fujitsu.cloudlab.offer.Util;

import com.fujitsu.cloudlab.commons.exception.ApiException;
import com.fujitsu.cloudlab.commons.util.CommonUtils;
import com.fujitsu.cloudlab.offer.json.model.Offer;
import com.fujitsu.cloudlab.offer.json.model.OffersList;
import com.fujitsu.cloudlab.offer.json.model.Price;
import com.fujitsu.cloudlab.offer.json.model.Product;
import com.fujitsu.cloudlab.offer.json.model.SearchCriteria;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OfferComposerUtil {
	@Value("${offer.exp.duration}") private long expTime;
	Random random = new Random();

	private static final List<String> productDescist = Arrays.asList(new String[]{"SAMSUNG S1 Phone", "SAMSUNG S2 Phone", "SAMSUNG S3 Phone", "DELL DESKTOP 1", "DELL DESKTOP 2", "DELL DESKTOP 3"});
	private static final List<String> productCodeList = Arrays.asList(new String[]{"SMSNG1", "SMSNG2", "SMSNG3", "DELLDSKTP1", "DELLDSKTP2", "DELLDSKTP3"});
	static List<Product> productList = new ArrayList<>();
	static {
		Random random = new Random();
		Product product = null;
		Price price = null;
		for(int i =0; i< productCodeList.size(); i++) {
			product = new Product();
			product.setProductCode(productCodeList.get(i));
			product.setProductDescription(productDescist.get(i));
			price = new Price();
			price.setCurrency("USD");
			price.setValue((double)Math.round((100 + (300 - 100) * random.nextDouble() * (i+1/3)) * 100d) / 100d);
			product.setProductPrice(price);
			productList.add(product);
		}
	}
	
	public List<Product> searchProducts() {
		//List<Product> matchedProducts = new ArrayList<>();
		//productList.stream().filter(product -> product.getProductDescription().contains(searchString)).forEach(matchedProducts::add);
		//productList.forEach(p -> productList.stream().filter(p1 -> p.getProductCode().equals(p1.getProductCode())).forEach(matchedProducts::add));
		//return CommonUtils.isNotNullAndEmpty(matchedProducts)?matchedProducts: productList;
		return productList;
	}
	
	public static List<Product> products(){
		return productList;
	}
	
	public OffersList composeOffer(SearchCriteria searchCriteria) throws ApiException {
	    List<Product> list = new ArrayList<>();
	    searchCriteria.getProducts().forEach(p -> productList.stream().filter(p1 -> p.getProductCode().equals(p1.getProductCode())).forEach(list::add));
	    if(! CommonUtils.isNotNullAndEmpty(list))
	    	throw new ApiException("", "NOT FOUND", "", null);
	    
	    OffersList offerList = new OffersList();
	    List<Offer> offers = new ArrayList<>();
	    list.forEach(product -> {
	    	Offer offer = new Offer();
		    long currDate = System.currentTimeMillis();
		    offer.setCustomer(searchCriteria.getCustomer());
		    offer.setOfferCreationDate(new Date(currDate).toString());
		    offer.setOfferId(UUID.randomUUID());
		    offer.setOfferExpirationDate(new Date(currDate+expTime).toString());
		    offer.setProduct(product);
		    offer.setOfferPrice(product.getProductPrice());
		    offer.setOfferType("TYPE");
		    offers.add(offer);
	    });
	    offerList.setOffers(offers);
	    return offerList;
	  }
	
}
