package com.trablock.web.service;

import com.trablock.web.entity.plan.Day;
import com.trablock.web.repository.ConceptRepository;
import com.trablock.web.repository.DayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayService {

    @PersistenceContext
    EntityManager em;
    private final DayRepository dayRepository;

    @Transactional
    public void saveDay(Day day) {
        dayRepository.save(day);
    }
}
