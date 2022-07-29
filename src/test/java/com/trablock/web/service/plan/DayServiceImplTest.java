package com.trablock.web.service.plan;

import com.trablock.web.controller.form.DayForm;
import com.trablock.web.controller.form.Form;
import com.trablock.web.controller.form.OneDayOneLocationForm;
import com.trablock.web.entity.location.Coords;
import com.trablock.web.entity.location.Location;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.Day;
import com.trablock.web.entity.plan.MovingData;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.enumtype.PlanComplete;
import com.trablock.web.entity.plan.enumtype.PlanStatus;
import com.trablock.web.repository.location.LocationRepository;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.DayRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.service.plan.interfaceC.DayService;
import com.trablock.web.service.plan.interfaceC.PlanService;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DayServiceImplTest {

    @Autowired
    DayService dayService;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    PlanService planService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    LocationRepository locationRepository;

    Plan testPlan1;
    Member member;
    Location location;
    List<Day> day;

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

        testPlan1 = planRepository.save(plan1);

        Location loc = Location.builder()
                .name("test1")
                .areaCode(123)
                .address1("경기도 수원시 팔달구")
                .address2("권광로180번길 53-26")
                .coords(Coords.builder()
                        .latitude(37.123).longitude(127.123).build()
                ).build();

        location = locationRepository.save(loc);

        OneDayOneLocationForm oneDayOneLocationForm1 = OneDayOneLocationForm.builder()
                .days(1)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test-arriveTime")
                                .movingTime("test-movingTime")
                                .startTime("test-startTime")
                                .stayTime("test-stayTime")
                                .vehicle("test-vehicle")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation")
                .build();


        OneDayOneLocationForm oneDayOneLocationForm2 = OneDayOneLocationForm.builder()
                .days(2)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test2-arriveTime")
                                .movingTime("test2-movingTime")
                                .startTime("test2-startTime")
                                .stayTime("test2-stayTime")
                                .vehicle("test2-vehicle")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation")
                .build();


        List<OneDayOneLocationForm> travel = new ArrayList<>();
        List<OneDayOneLocationForm> travel2 = new ArrayList<>();

        travel.add(oneDayOneLocationForm1);
        travel2.add(oneDayOneLocationForm2);

        List<List<OneDayOneLocationForm>> travelDays = new ArrayList<>();

        travelDays.add(travel);
        travelDays.add(travel2);


        Form form = Form.builder()
                .dayForm(
                        DayForm.builder()
                                .travelDay(travelDays)
                                .build()
                )
                .build();

        day = dayService.createDay(form, testPlan1.getId());
    }


    @Test
    @DisplayName("Day 생성 test")
    public void createDayTest() throws Exception {
        //given
        OneDayOneLocationForm oneDayOneLocationForm1 = OneDayOneLocationForm.builder()
                .days(1)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test-arriveTime")
                                .movingTime("test-movingTime")
                                .startTime("test-startTime")
                                .stayTime("test-stayTime")
                                .vehicle("test-vehicle")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation")
                .build();

        OneDayOneLocationForm oneDayOneLocationForm1_1 = OneDayOneLocationForm.builder()
                .days(1)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test1_1-arriveTime")
                                .movingTime("test1_1-movingTime")
                                .startTime("test1_1-startTime")
                                .stayTime("test1_1-stayTime")
                                .vehicle("test1_1-vehicle")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation")
                .build();

        OneDayOneLocationForm oneDayOneLocationForm2_1 = OneDayOneLocationForm.builder()
                .days(2)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test2-arriveTime")
                                .movingTime("test2-movingTime")
                                .startTime("test2-startTime")
                                .stayTime("test2-stayTime")
                                .vehicle("test2-vehicle")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation")
                .build();

        OneDayOneLocationForm oneDayOneLocationForm2_2 = OneDayOneLocationForm.builder()
                .days(2)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test3-arriveTime")
                                .movingTime("test3-movingTime")
                                .startTime("test3-startTime")
                                .stayTime("test3-stayTime")
                                .vehicle("test3-vehicle")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation")
                .build();

        List<OneDayOneLocationForm> travel = new ArrayList<>();

        List<OneDayOneLocationForm> travel2 = new ArrayList<>();

        travel.add(oneDayOneLocationForm1);
        travel.add(oneDayOneLocationForm1_1);
        travel2.add(oneDayOneLocationForm2_1);
        travel2.add(oneDayOneLocationForm2_2);

        List<List<OneDayOneLocationForm>> travelDays = new ArrayList<>();

        travelDays.add(travel);
        travelDays.add(travel2);


        Form form = Form.builder()
                .dayForm(
                        DayForm.builder()
                                .travelDay(travelDays)
                                .build()
                )
                .build();

        //when
        List<Day> day = dayService.createDay(form, testPlan1.getId());

        //then
        assertThat(day.get(0).getCopyLocationId()).isEqualTo(oneDayOneLocationForm1.getCopyLocationId());
        assertThat(day.get(0).getMovingData().getStartTime()).isEqualTo(oneDayOneLocationForm1.getMovingData().getStartTime());
        assertThat(day.get(1).getCopyLocationId()).isEqualTo(oneDayOneLocationForm1_1.getCopyLocationId());
        assertThat(day.get(1).getMovingData().getStartTime()).isEqualTo(oneDayOneLocationForm1_1.getMovingData().getStartTime());
        assertThat(day.get(2).getCopyLocationId()).isEqualTo(oneDayOneLocationForm2_1.getCopyLocationId());
        assertThat(day.get(2).getMovingData().getStartTime()).isEqualTo(oneDayOneLocationForm2_1.getMovingData().getStartTime());
        assertThat(day.get(3).getCopyLocationId()).isEqualTo(oneDayOneLocationForm2_2.getCopyLocationId());
        assertThat(day.get(3).getMovingData().getStartTime()).isEqualTo(oneDayOneLocationForm2_2.getMovingData().getStartTime());
    }

    @Test
    @DisplayName("day 삭제 test")
    public void removeDayTest() throws Exception {
        //given
        long count = dayRepository.count();

        assertThat(count).isEqualTo(2);

        //when
        dayService.removeDay(testPlan1);

        long deleteCount = dayRepository.count();

        //then
        assertThat(deleteCount).isEqualTo(0);
    }


    @Test
    @DisplayName("day 수정 test")
    public void updateDayTest() throws Exception {
        //given
        OneDayOneLocationForm updateOneDayOneLocationForm = OneDayOneLocationForm.builder()
                .days(1)
                .movingData(
                        MovingData.builder()
                                .arriveTime("test-arriveTime-update")
                                .movingTime("test-movingTime-update")
                                .startTime("test-startTime-update")
                                .stayTime("test-stayTime-update")
                                .vehicle("test-vehicle-update")
                                .build()
                )
                .locationId(location.getId())
                .copyLocationId("test-copyLocation-update")
                .build();

        List<OneDayOneLocationForm> travel = new ArrayList<>();
        travel.add(updateOneDayOneLocationForm);

        List<List<OneDayOneLocationForm>> travelDays = new ArrayList<>();
        travelDays.add(travel);


        Form form = Form.builder()
                .dayForm(
                        DayForm.builder()
                                .travelDay(travelDays)
                                .build()
                )
                .build();


        //when
        List<Day> days = dayService.updateDay(testPlan1.getId(), form);

        long count = dayRepository.count();

        //then
        assertThat(count).isEqualTo(1);
        assertThat(days.get(0).getCopyLocationId()).isEqualTo(updateOneDayOneLocationForm.getCopyLocationId());
        assertThat(days.get(0).getId()).isNotEqualTo(day.get(0).getId());
        assertThat(days.get(0).getMovingData().getStartTime()).isEqualTo(updateOneDayOneLocationForm.getMovingData().getStartTime());
        assertThat(days.get(0).getMovingData().getMovingTime()).isEqualTo(updateOneDayOneLocationForm.getMovingData().getMovingTime());
        assertThat(days.get(0).getMovingData().getArriveTime()).isEqualTo(updateOneDayOneLocationForm.getMovingData().getArriveTime());
        assertThat(days.get(0).getMovingData().getVehicle()).isEqualTo(updateOneDayOneLocationForm.getMovingData().getVehicle());
        assertThat(days.get(0).getMovingData().getStayTime()).isEqualTo(updateOneDayOneLocationForm.getMovingData().getStayTime());
    }

    @Test
    @DisplayName("PlanId를 통해 Day를 List 형태로 불러오는지 test")
    public void findDayIdForPlanIdToListTest() throws Exception {
        //given

        //when
        List<Day> dayList = dayService.findDayIdForPlanIdToList(testPlan1.getId());

        //then
        assertThat(dayList.get(0).getId()).isEqualTo(day.get(0).getId());
        assertThat(dayList.get(0).getDays()).isEqualTo(day.get(0).getDays());
        assertThat(dayList.get(0).getCopyLocationId()).isEqualTo(day.get(0).getCopyLocationId());
        assertThat(dayList.get(0).getMovingData().getStayTime()).isEqualTo(day.get(0).getMovingData().getStayTime());
        assertThat(dayList.get(0).getMovingData().getMovingTime()).isEqualTo(day.get(0).getMovingData().getMovingTime());
        assertThat(dayList.get(0).getMovingData().getStartTime()).isEqualTo(day.get(0).getMovingData().getStartTime());
        assertThat(dayList.get(0).getMovingData().getVehicle()).isEqualTo(day.get(0).getMovingData().getVehicle());
        assertThat(dayList.get(0).getMovingData().getArriveTime()).isEqualTo(day.get(0).getMovingData().getArriveTime());

        assertThat(dayList.get(1).getId()).isEqualTo(day.get(1).getId());
        assertThat(dayList.get(1).getDays()).isEqualTo(day.get(1).getDays());
        assertThat(dayList.get(1).getCopyLocationId()).isEqualTo(day.get(1).getCopyLocationId());
        assertThat(dayList.get(1).getMovingData().getStayTime()).isEqualTo(day.get(1).getMovingData().getStayTime());
        assertThat(dayList.get(1).getMovingData().getMovingTime()).isEqualTo(day.get(1).getMovingData().getMovingTime());
        assertThat(dayList.get(1).getMovingData().getStartTime()).isEqualTo(day.get(1).getMovingData().getStartTime());
        assertThat(dayList.get(1).getMovingData().getVehicle()).isEqualTo(day.get(1).getMovingData().getVehicle());
        assertThat(dayList.get(1).getMovingData().getArriveTime()).isEqualTo(day.get(1).getMovingData().getArriveTime());
    }
}