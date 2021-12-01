package com.trablock.demo.domain.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberLocations {

    @OneToMany(targetEntity = MemberLocation.class, mappedBy = "member",
            cascade = PERSIST, fetch = EAGER)
    private static final List<MemberLocation> memberLocations = new ArrayList<>();
}
