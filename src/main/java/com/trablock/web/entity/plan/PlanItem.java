package com.trablock.web.entity.plan;

import com.trablock.web.entity.plan.enumtype.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_directory_id")
    private UserDirectory userDirectory;

    @Enumerated(EnumType.STRING)
    private Status status;

    //==비지니스 로직==//
    /**
     * user 디렉터리 삭제(Mapping 삭제)
     */
    public void delete() {
        if (getStatus() == Status.DELETE) {
            throw new IllegalStateException("이미 삭제된 디렉터리입니다.");
        }

        this.status = Status.DELETE;
    }
}

