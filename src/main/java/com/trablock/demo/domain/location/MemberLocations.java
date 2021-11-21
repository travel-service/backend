package com.trablock.demo.domain.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberLocations {

    @OneToMany(mappedBy = "member" ,cascade = CascadeType.ALL)
    private List<MemberLocation> memberLocations = new ArrayList<>();


}
