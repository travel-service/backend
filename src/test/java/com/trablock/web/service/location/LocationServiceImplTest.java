package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationSaveRequestDto;
import com.trablock.web.dto.location.MarkLocationDto;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.location.MemberLocationRepository;
import com.trablock.web.service.location.mapper.LocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class LocationServiceImplTest {

    LocationRepository locationRepository;
    MemberLocationRepository memberLocationRepository;
    LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    LocationService locationService = new LocationServiceImpl(locationRepository, memberLocationRepository);
    Member member;

    @BeforeEach
    void init() {

        MemberInfo memberInfo = new MemberInfo("1998-01-02",
                Gender.MALE, "010-1234-1234", "test@test.org");

        MemberProfile memberProfile = new MemberProfile("홍길동", "image/1234");

        member = Member.builder()
                .userName("test3332")
                .password("1234")
                .realName("허균")
                .memberInfo(memberInfo)
                .memberProfile(memberProfile)
                .build();
    }

    @Test
    void viewSimpleLoc() {
        List<MarkLocationDto> markLocationDtos = locationService.getMarkLocationsWithType(LocationType.LODGE);

        for (MarkLocationDto markLocationDto : markLocationDtos) {
            System.out.println("simpleLocationDto = " + markLocationDto);
        }
    }

    @Test
    void 로케이션_생성_요청() {
        LocationSaveRequestDto requestDto = LocationSaveRequestDto.builder()
                .name("개발새발")
                .address1("경기도 안성시 석정동 비룡 5길 30")
                .address2("418-2")
                .coords(new Coords("37.1234", "128.1234"))
                .image("img/1234")
                .type(LocationType.LODGE)
                .build();

        locationService.create(requestDto);


    }


}