package com.trablock.web.entity.location.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TypeLocationTest {

    @Test
    @DisplayName("Attraction 생성 테스트")
    void createAttraction() throws Exception {
        //given
        Attraction attraction;

        //when
        attraction = Attraction.builder()
                .locationId(1L)
                .parking(true)
                .restDate("매주 일요일")
                .useTime("09:00~22:00")
                .build();

        //then
        assertThat(attraction.getLocationId()).isEqualTo(1L);
        assertThat(attraction.isParking()).isTrue();
        assertThat(attraction.getRestDate()).isEqualTo("매주 일요일");
        assertThat(attraction.getUseTime()).isEqualTo("09:00~22:00");
    }

    @Test
    @DisplayName("Culture 생성 테스트")
    void createCulture() throws Exception {
        //given
        Culture culture;

        //when
        culture = Culture.builder()
                .locationId(1L)
                .parking(true)
                .restDate("매주 일요일")
                .useTime("09:00~22:00")
                .fee("3000원")
                .spendTime("2시간")
                .build();

        //then
        assertThat(culture.getLocationId()).isEqualTo(1L);
        assertThat(culture.getFee()).isEqualTo("3000원");
        assertThat(culture.isParking()).isTrue();
        assertThat(culture.getRestDate()).isEqualTo("매주 일요일");
        assertThat(culture.getUseTime()).isEqualTo("09:00~22:00");
        assertThat(culture.getSpendTime()).isEqualTo("2시간");

    }

    @Test
    void createFestival() throws Exception {
        //given

        //when

        //then

    }

    @Test
    void createLeports() throws Exception {
        //given

        //when

        //then

    }

    @Test
    void createLodge() throws Exception {
        //given

        //when

        //then

    }

    @Test
    void createRestaurant() throws Exception {
        //given

        //when

        //then

    }

}