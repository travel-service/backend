package com.trablock.demo.dto.location;

import com.trablock.demo.domain.location.Coords;
import com.trablock.demo.domain.location.Information;
import com.trablock.demo.domain.location.Location;
import com.trablock.demo.domain.location.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public class LocationDto {

    private Long id;
    private String name;
    private Coords coords;
    private byte[] locationImg;
    private Information information;
    private LocationType locationType;

    /**
     * Location response Dto
     */
    public static class Response {

        private static ModelMapper modelMapper = new ModelMapper();

        public static Response of(Location location) {
            final Response dto = modelMapper.map(location, Response.class);
            return dto;
        }

        /**
         * 이 부분 학습 필요. AskList..?
         */
//        public static List<Response> of(List<Location> locationList) {
//            return LocationAskList.stream()
//                    .map(Response::of)
//                    .collect(Collectors.toList());
//        }

    }

    @Builder
    public static class Create {

    }


}
