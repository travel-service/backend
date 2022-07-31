package com.trablock.web.entity.location;

import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.BlockLocationDto;
import com.trablock.web.dto.location.MarkLocationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    @DisplayName("엔티티 생성 테스트")
    void create() throws Exception {

        // given
        Location location;

        // when
        location = Location.builder()
                .name("우리집")
                .areaCode(123)
                .address1("경기도 수원시")
                .address2("권선구")
                .coords(new Coords(123.1234, 37.123))
                .image("url:123")
                .type(LocationType.LODGE)
                .isMember(true)
                .build();

        //then
        assertThat(location.getName()).isEqualTo("우리집");
        assertThat(location.getAreaCode()).isEqualTo(123);
        assertThat(location.getAddress1()).isEqualTo("경기도 수원시");
        assertThat(location.getAddress2()).isEqualTo("권선구");
        assertThat(location.getCoords()).isEqualTo(new Coords(123.1234, 37.123));
        assertThat(location.getImage()).isEqualTo("url:123");
        assertThat(location.getType()).isEqualTo(LocationType.LODGE);
        assertThat(location.getIsMember()).isTrue();
    }

    @Test
    @DisplayName("toBlockLocationDto 테스트")
    void toBlockLocationDto() throws Exception {
        // given
        Location location = Location.builder()
                .name("우리집")
                .areaCode(123)
                .address1("경기도 수원시")
                .address2("권선구")
                .coords(new Coords(123.1234, 37.123))
                .image("url:123")
                .type(LocationType.LODGE)
                .isMember(true)
                .build();

        // when
        BlockLocationDto blockLocationDto = location.toBlockLocationDto();

        //then
        assertThat(blockLocationDto.getName()).isEqualTo(location.getName());
        assertThat(blockLocationDto.getImage()).isEqualTo(location.getImage());
        assertThat(blockLocationDto.getAddress1()).isEqualTo(location.getAddress1());
        assertThat(blockLocationDto.getAddress2()).isEqualTo(location.getAddress2());
        assertThat(blockLocationDto.getType()).isEqualTo(location.getType());
    }

    @Test
    @DisplayName("toMarkLocationDto 테스트")
    void toMarkLocationDto() throws Exception {
        // given
        Location location = Location.builder()
                .name("우리집")
                .areaCode(123)
                .address1("경기도 수원시")
                .address2("권선구")
                .coords(new Coords(123.1234, 37.123))
                .image("url:123")
                .type(LocationType.LODGE)
                .isMember(true)
                .build();

        // when
        MarkLocationDto markLocationDto = location.toMarkLocationDto();

        //then
        assertThat(markLocationDto.getName()).isEqualTo(location.getName());
        assertThat(markLocationDto.getCoords()).isEqualTo(location.getCoords());
        assertThat(markLocationDto.getType()).isEqualTo(location.getType());
    }

    @Test
    void test() throws Exception {
        //given
        Location location = Location.builder()
                .name("우리집")
                .areaCode(123)
                .address1("경기도 수원시")
                .address2("권선구")
                .coords(new Coords(123.1234, 37.123))
                .image("url:123")
                .type(LocationType.LODGE)
                .isMember(true)
                .build();
        //when
        location.changeName("너네집");

        //then
        assertThat(location.getName()).isEqualTo("너네집");

    }

}