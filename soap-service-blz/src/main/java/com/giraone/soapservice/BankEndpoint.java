package com.giraone.soapservice;

import com.giraone.blz_service.DetailsType;
import com.giraone.blz_service.GetBankRequest;
import com.giraone.blz_service.GetBankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class BankEndpoint {

	private static final String NAMESPACE_URI = "http://www.giraone.com/blz-service/";

	private final BankRepository bankRepository;

	@Autowired
	public BankEndpoint(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBankRequest")
	@ResponsePayload
	public GetBankResponse getBank(@RequestPayload GetBankRequest request) {
		GetBankResponse response = new GetBankResponse();
		BankDetails bankDetails = bankRepository.findBank(request.getBlz());
		DetailsType detailsType = new DetailsType();
		detailsType.setBezeichnung(bankDetails.getBezeichnung());
		detailsType.setBic(bankDetails.getBic());
		detailsType.setOrt(bankDetails.getOrt());
		detailsType.setPlz(bankDetails.getPlz());
		response.setDetails(detailsType);
		return response;
	}
}
