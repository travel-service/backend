package com.trablock.web.controller;

import com.querydsl.core.util.StringUtils;
import com.trablock.web.config.jwt.JwtTokenService;
import com.trablock.web.domain.LocationType;
import com.trablock.web.dto.location.*;
import com.trablock.web.dto.location.type.TypeLocationDto;
import com.trablock.web.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final JwtTokenService jwtTokenService;

    /**
     * 로케이션의 details를 반환
     * -> 지도의 markup이나 block 을 클릭할 때 response
     */
    @GetMapping("/locations/{locationId}")
    public ResponseEntity<TypeLocationDto> viewLocationDetails(@PathVariable("locationId") Long locationId, @RequestParam String locationType) {
        return ResponseEntity.ok(locationService.getLocationDetails(locationId, LocationType.fromValue(StringUtils.capitalize(locationType))));
    }

    /**
     * 지도에 MarkUp 되는 location
     */
    @GetMapping("/locations/mark")
    public ResponseEntity<Map<String, List<MarkLocationDto>>> viewMarkLocationsOnMap() {
        return ResponseEntity.ok().body(locationService.getMarkLocationList());
    }

    @GetMapping("locations/member")
    public ResponseEntity<MarkAndBlockLocationListDto> viewMemberLocations(HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);
        return ResponseEntity.ok().body(locationService.getMemberLocationList(memberId));
    }

    /**
     * Block으로 표현되는 location
     */
    @GetMapping("/locations/block")
    public ResponseEntity<Map<String, List<BlockLocationDto>>> viewBlockLocationList() {
        return ResponseEntity.ok().body(locationService.getBlockLocationList());
    }

    /**
     * 멤버 로케이션 추가
     * 멤버 로케이션 생성 시, Location이 먼저 생성되고
     * 그 이후 TypeLocation, Information, MemberLocation이 순차적으로 생성되어야 한다.
     */
    @PostMapping("/locations/member")
    public ResponseEntity<Long> memberLocationAdd(@RequestBody LocationWrapperDto wrapperDto, HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);
        wrapperDto.getMemberLocation().setMemberId(memberId);
        return new ResponseEntity<Long>(locationService.createLocationByMember(wrapperDto), HttpStatus.CREATED);
    }

    /**
     * 멤버 로케이션 삭제.
     * 로케이션 정보는 남겨두고 MemberLocation만 삭제하도록 하였다.
     */
    @DeleteMapping("/locations/member/{locationId}")
    public ResponseEntity<String> memberLocationRemove(@PathVariable("locationId") Long locationId, HttpServletRequest request) {
        Long memberId = jwtTokenService.tokenToUserId(request);
        return locationService.deleteLocationByMember(locationId, memberId) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }


    /**
     * MemberLocation과 관련된 문제?
     * =============================
     * 남이 공개한거를 가져올수있다 문제x
     * 남이 공개 안한거를 가져올수있다 문제o
     * 내가 공개한거를 내가 가져온다 문제x
     * 내가 공개한거를 내가 못가져온다 문제o
     */
}
