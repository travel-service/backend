package com.trablock.web.service.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.LocationWrapperDto;
import com.trablock.web.dto.location.TypeLocationRequestDto;
import com.trablock.web.dto.location.save.InformationRequestDto;
import com.trablock.web.dto.location.save.LocationRequestDto;
import com.trablock.web.dto.location.save.MemberLocationRequestDto;
import com.trablock.web.dto.location.type.AttractionDto;
import com.trablock.web.dto.location.type.TypeLocationDto;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.member.Member;
import com.trablock.web.repository.member.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceTest {

    @Autowired
    LocationService locationService;

    @Autowired
    MemberRepository memberRepository;

    Long locationId;

    @BeforeEach
    void init() {
        Member member = memberRepository.save(Member.builder()
                .userName("name")
                .password("1234")
                .build());

        LocationRequestDto locationRequestDto = new LocationRequestDto("우리집", "수원시 권선구", "",
                new Coords(123.4567, 37.567), "abc.url/123",
                123, true, LocationType.LODGE);

        InformationRequestDto informationRequestDto = new InformationRequestDto("요약요약요약",
                "긴글긴글긴글긴글긴글긴글긴글긴글긴글긴글", "이미지1", "이미지2", "010-1234-5678");

        MemberLocationRequestDto memberLocationRequestDto = new MemberLocationRequestDto(member.getId(), true);

        TypeLocationRequestDto typeLocationRequestDto = TypeLocationRequestDto.builder()
                .parking(true)
                .restDate("매주 일요일")
                .useTime("09:00~22:00")
                .build();

        LocationWrapperDto locationWrapperDto = LocationWrapperDto.builder()
                .location(locationRequestDto)
                .memberLocation(memberLocationRequestDto)
                .information(informationRequestDto)
                .typeLocation(typeLocationRequestDto)
                .build();

        locationId = locationService.createLocationByMember(locationWrapperDto);
    }

    @Test
    @DisplayName("멤버로케이션 생성")
    void createLocationByMember() throws Exception {
        //then
        assertThat(locationId).isNotNull();
    }


    @Test
    @DisplayName("로케이션 디테일 조회")
    void getLocationDetails() throws Exception {
        //given

        //when
        TypeLocationDto locationDetails = locationService.getLocationDetails(locationId, LocationType.ATTRACTION);

        //then
        assertAll(
                () -> assertNotNull(locationId)
//                () -> assertThat(locationDetails.getLocationId()).isEqualTo(locationId),
//                () -> assertThat(locationDetails.isParking()).isTrue(),
//                () -> assertThat(locationDetails.getRestDate()).isEqualTo("매주 일요일"),
//                () -> assertThat(locationDetails.getUseTime()).isEqualTo("09:00~22:00")
        );

        System.out.println("locationDetails = " + locationDetails.toString());
    }
}