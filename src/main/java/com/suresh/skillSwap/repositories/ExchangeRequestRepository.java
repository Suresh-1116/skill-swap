package com.suresh.skillSwap.repositories;

import com.suresh.skillSwap.entities.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
}
