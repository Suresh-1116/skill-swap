package com.suresh.skillSwap.controllers;

import com.suresh.skillSwap.dto.ExchangeRequestDto;
import com.suresh.skillSwap.dto.ExchangeRequestPatchDto;
import com.suresh.skillSwap.dto.ExchangeRequestResponse;
import com.suresh.skillSwap.servicies.ExchangeRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange-requests")
public class ExchangeRequestController {
    final private ExchangeRequestService exchangeRequestService;
    public ExchangeRequestController(ExchangeRequestService exchangeRequestService) {
        this.exchangeRequestService = exchangeRequestService;
    }

    /*================= EXCHANGE REQUEST CONTROLLER LAYER <====================*/
    @PostMapping
    public ResponseEntity<ExchangeRequestResponse> createExchangeRequest(@Valid @RequestBody ExchangeRequestDto exchangeRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(exchangeRequestService.createExchangeRequest(exchangeRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRequestResponse>> getExchangeRequests(){
        return ResponseEntity.ok(exchangeRequestService.getExchangeRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRequestResponse> getExchangeRequest(@PathVariable(name = "id") Long requestId){
        return ResponseEntity.ok(exchangeRequestService.getExchangeRequest(requestId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRequestResponse> updateExchangeRequest(@PathVariable(name = "id") Long requestId,
                                                                         @Valid @RequestBody ExchangeRequestDto exchangeRequestDto){
        return ResponseEntity.ok(exchangeRequestService.updateExchangeRequest(requestId, exchangeRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExchangeRequestResponse> partialUpdateExchangeRequest(@PathVariable(name = "id") Long requestId,
                                                                                @Valid @RequestBody ExchangeRequestPatchDto exchangeRequestPatchDto){
        return ResponseEntity.ok(exchangeRequestService.partialUpdateExchangeRequest(requestId, exchangeRequestPatchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchangeRequest(@PathVariable(name = "id") Long requestId){
        exchangeRequestService.deleteExchangeRequest(requestId);
        return ResponseEntity.noContent().build();
    }
}
