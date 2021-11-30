package com.trablock.demo.domain.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberLocations {

    @OneToMany(targetEntity = MemberLocation.class, mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberLocation> memberLocations = new ArrayList<>();

    public MemberLocations(List<MemberLocation> memberLocations){
        this.memberLocations = memberLocations;
    }
}
