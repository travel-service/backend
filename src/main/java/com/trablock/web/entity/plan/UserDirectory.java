package com.trablock.web.entity.plan;

import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDirectory {

    @Id @GeneratedValue
    @Column(name = "user_directory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "userDirectory", cascade = CascadeType.ALL)
    private List<PlanItem> planItems;

    private String directoryName;

    /**
     * user 디렉터리 이름 변경
     * @param directoryNameUpdateDto
     */
    public void updateName(DirectoryNameUpdateDto directoryNameUpdateDto) {
        this.directoryName = directoryNameUpdateDto.getDirectoryName();
    }
}

