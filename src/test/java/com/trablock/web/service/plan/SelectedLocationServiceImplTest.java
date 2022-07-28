package com.trablock.web.service.plan;

import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.SelectedLocationForm;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.SelectedLocation;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.SelectedLocationRepository;
import com.trablock.web.service.plan.interfaceC.SelectedLocationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SelectedLocationServiceImplTest {

    @Autowired
    SelectedLocationService selectedLocationService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SelectedLocationRepository selectedLocationRepository;

    Plan plan;
    Member member;
    Location location1;
    Location location2;
    Location location3;

    @BeforeEach
    void init() {
        Member initMember = new Member("username", "1234",
                new MemberProfile("nickname", "bio"),
                new MemberInfo("19980102", Gender.MALE, "wkdwoo@kakao.com"),
                new ArrayList<>(), true);

        member = memberRepository.save(initMember);

        Plan plan1 = Plan.builder()
                .depart("test-depart-1")
                .name("test-name-1")
                .planComplete(PlanComplete.UNFINISHED)
                .planStatus(PlanStatus.MAIN)
                .thumbnail("test-thumbnail-1")
                .periods(1)
                .member(member)
                .build();

        plan = planRepository.save(plan1);

        Location loc3 = Location.builder()
                .name("test1")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();

        location3 = locationRepository.save(loc3);

        Location loc1 = Location.builder()
                .name("test1")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();

        location1 = locationRepository.save(loc1);

        Location loc2 = Location.builder()
                .name("test1")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();

        location2 = locationRepository.save(loc2);
    }

    @Test
    @DisplayName("SelectedLocation 생성 test")
    public void createSelectedLocationTest() throws Exception {
        //given
        List<Long> selectedLocationList = new ArrayList<>();

        selectedLocationList.add(location1.getId());
        selectedLocationList.add(location2.getId());
        selectedLocationList.add(location3.getId());

        Form form = Form.builder()
                .selectedLocationForm(
                        SelectedLocationForm.builder()
                                .selectedLocation(selectedLocationList)
                                .build()
                )
                .build();

        //when
        List<SelectedLocation> selectedLocation = selectedLocationService.createSelectedLocation(form, plan.getId());

        //then
        assertThat(selectedLocation.get(0).getLocation().getId()).isEqualTo(location1.getId());
        assertThat(selectedLocation.get(1).getLocation().getId()).isEqualTo(location2.getId());
        assertThat(selectedLocation.get(2).getLocation().getId()).isEqualTo(location3.getId());
    }

    @Test
    @DisplayName("SelectedLocation 삭제 test")
    public void removeSelectedLocationTest() throws Exception {
        //given
        List<Long> selectedLocationList = new ArrayList<>();

        selectedLocationList.add(location1.getId());
        selectedLocationList.add(location2.getId());
        selectedLocationList.add(location3.getId());

        Form form = Form.builder()
                .selectedLocationForm(
                        SelectedLocationForm.builder()
                                .selectedLocation(selectedLocationList)
                                .build()
                )
                .build();

        selectedLocationService.createSelectedLocation(form, plan.getId());

        //when
        selectedLocationService.removeSelectedLocation(plan);

        long deleteCount = selectedLocationRepository.count();

        //then
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    @DisplayName("SelectedLocation 수정 test")
    public void updateSelectedLocationTest() throws Exception {
        //given
        List<Long> selectedLocationList = new ArrayList<>();

        selectedLocationList.add(location1.getId());
        selectedLocationList.add(location2.getId());

        Form form = Form.builder()
                .selectedLocationForm(
                        SelectedLocationForm.builder()
                                .selectedLocation(selectedLocationList)
                                .build()
                )
                .build();

        selectedLocationService.createSelectedLocation(form, plan.getId());

        List<Long> updateSelectedLocationList = new ArrayList<>();

        updateSelectedLocationList.add(location3.getId());

        Form updateForm = Form.builder()
                .selectedLocationForm(
                        SelectedLocationForm.builder()
                                .selectedLocation(updateSelectedLocationList)
                                .build()
                )
                .build();

        //when
        assertThat(selectedLocationRepository.count()).isEqualTo(2);

        List<SelectedLocation> updateSelectedLocation = selectedLocationService.updateSelectedLocation(plan.getId(), updateForm);

        long updateCount = selectedLocationRepository.count();

        //then
        assertThat(updateCount).isEqualTo(1);
        assertThat(updateSelectedLocation.get(0).getLocation().getId()).isEqualTo(location3.getId());
    }

    @Test
    @DisplayName("Plan 으로 LocationId List로 받아오는 지 test")
    public void findLocationIdListTest() throws Exception {
        //given
        List<Long> selectedLocationList = new ArrayList<>();

        selectedLocationList.add(location1.getId());
        selectedLocationList.add(location2.getId());
        selectedLocationList.add(location3.getId());

        Form form = Form.builder()
                .selectedLocationForm(
                        SelectedLocationForm.builder()
                                .selectedLocation(selectedLocationList)
                                .build()
                )
                .build();

        List<SelectedLocation> selectedLocation = selectedLocationService.createSelectedLocation(form, plan.getId());

        //when
        List<Long> locationIdList = selectedLocationService.findLocationIdList(plan);

        //then
        assertThat(locationIdList.get(0)).isEqualTo(selectedLocation.get(0).getLocation().getId());
        assertThat(locationIdList.get(1)).isEqualTo(selectedLocation.get(1).getLocation().getId());
        assertThat(locationIdList.get(2)).isEqualTo(selectedLocation.get(2).getLocation().getId());
    }
}