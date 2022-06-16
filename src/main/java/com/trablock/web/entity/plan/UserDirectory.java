package com.trablock.web.entity.plan;

import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.enumtype.Status;
import lombok.*;

import javax.persistence.*;

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

    private String directoryName;

    @Enumerated(EnumType.STRING)
    private Status status;

    //==비지니스 로직==//
    /**
     * user 디렉터리 삭제
     */
    public void delete() {
        if (getStatus() == Status.DELETE) {
            throw new IllegalStateException("이미 삭제된 디렉터리입니다.");
        }

        this.setStatus(Status.DELETE);
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

