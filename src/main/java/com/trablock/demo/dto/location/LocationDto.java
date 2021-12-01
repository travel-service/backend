package com.trablock.demo.dto.location;

import com.trablock.demo.domain.location.*;
import com.trablock.demo.domain.member.Member;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class LocationDto {

    /**
     * Location response DTO
     * Entity -> DTO
     */
    public static class Response {
        private Long id;
        private String name;
        private Coords coords;
        private String address1;
        private String address2;
        private byte[] locationImg;
        private Information information;
        private LocationType locationType;

        private static ModelMapper modelMapper = new ModelMapper();

        public static Response of(Location location) {
            final Response dto = modelMapper.map(location, Response.class);
            return dto;
        }

        public static List<Response> of(List<Location> locationList) {
            return locationList.stream()
                    .map(Response::of)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Location request DTO
     * DTO -> Entity
     */
    public static class Request {

        private Long id;
        private String name;
        private Member member;

        private String address1; // 주소를 직접 입력하진 않을 것이다.
        private String address2; // 주소, 좌표 변환은 프론트? 백엔드? 어디서?
        private Coords coords;

        private byte[] locationImg;
        private Information information;
        private LocationType locationType;

        /**
         * MemberLocation 생성
         * @return
         */
        public MemberLocation toEntity() {
            return MemberLocation.builder()
                    .name(name)
                    .address1(address1)
                    .address2(address2)
                    .coords(coords)
                    .information(information)
                    .locationType(locationType)
                    .build();
        }
        

    }
}
