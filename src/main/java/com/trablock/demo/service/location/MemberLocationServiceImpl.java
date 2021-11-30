package com.trablock.demo.service.location;

import com.trablock.demo.domain.location.Location;
import com.trablock.demo.domain.location.MemberLocation;
import com.trablock.demo.repository.location.LocationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberLocationServiceImpl implements LocationService {

    private final LocationRepositoryImpl locationRepository;

    /**
     * 사용자 로케이션 등록
     * @param location
     * @return
     */

}
