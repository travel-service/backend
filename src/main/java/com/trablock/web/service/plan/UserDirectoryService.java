package com.trablock.web.service.plan;

import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.enumtype.Status;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDirectoryService {

    private final UserDirectoryRepository userDirectoryRepository;
    private final PlanService planService;

    @Transactional
    public void saveUserDirectory(UserDirectory userDirectory) {
        userDirectoryRepository.save(userDirectory);
    }

    //user 디렉터리 삭제
    @Transactional
    public void deleteUserDirectory(Long userDirectoryId) {
        UserDirectory userDirectoryById = userDirectoryRepository.findUserDirectoryById(userDirectoryId);
        userDirectoryById.delete();
    }

    //user directory GET 요청
    public List<UserDirectory> findMainUserDirectoryMain(HttpServletRequest request) {
        Member memberId = planService.findMemberId(request);
        return userDirectoryRepository.findMemberIdForList(Optional.ofNullable(memberId));
    }

    //user directory 생성
    @Transactional
    public void createUserDirectory(HttpServletRequest request, UserDirectoryForm userDirectoryForm, HttpServletResponse response) {

        Member memberId = planService.findMemberId(request);

        int memberIdForCount = userDirectoryRepository.findMemberIdForCount(memberId);

        if (memberIdForCount >= 6) {
            response.setStatus(512, "디렉터리는 5개 까지만 생성 가능합니다.");
        } else {
            UserDirectory userDirectory = UserDirectory.builder()
                    .directoryName(userDirectoryForm.getDirectoryName())
                    .member(memberId)
                    .status(Status.UNDELETE)
                    .build();

            saveUserDirectory(userDirectory);
        }
    }

    // user directory 이름 변경
    @Transactional
    public void updateDirectoryName(Long id, DirectoryNameUpdateDto directoryNameUpdateDto) {
        UserDirectory userDirectory = userDirectoryRepository.findUserDirectoryById(id);
        System.out.println("userDirectory.getId() = " + userDirectory.getId());
        System.out.println("userDirectory.getDirectoryName() = " + userDirectory.getDirectoryName());
        userDirectory.updateName(directoryNameUpdateDto);

        System.out.println("userDirectory.getDirectoryName() = " + userDirectory.getDirectoryName());

    }

    /**
     * user가 생성한 디렉터리 불러오기
     * @param request
     * @return
     */
    public List<UserDirectory> findUserDirectory(HttpServletRequest request) {

        Member member = planService.findMemberId(request);

        return userDirectoryRepository.findUserDirectoryById(member);

    }
}