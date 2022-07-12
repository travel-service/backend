package com.trablock.web.service.plan;

import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.enumtype.Status;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.interfaceC.PlanService;
import com.trablock.web.service.plan.interfaceC.UserDirectoryService;
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
public class UserDirectoryServiceImpl implements UserDirectoryService {

    private final UserDirectoryRepository userDirectoryRepository;
    private final PlanService planService;

    @Override
    @Transactional
    public void saveUserDirectory(UserDirectory userDirectory) {
        userDirectoryRepository.save(userDirectory);
    }

    //user 디렉터리 삭제
    @Override
    @Transactional
    public void deleteUserDirectory(Long userDirectoryId) {
        UserDirectory userDirectoryById = userDirectoryRepository.findUserDirectoryById(userDirectoryId);
        userDirectoryById.delete();
    }

    //user directory GET 요청
    @Override
    public List<UserDirectory> findMainUserDirectoryMain(HttpServletRequest request) {
        Member memberId = planService.findMemberId(request);
        return userDirectoryRepository.findMemberIdForList(Optional.ofNullable(memberId));
    }

    //user directory 생성
    @Override
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
    @Override
    @Transactional
    public void updateDirectoryName(Long id, DirectoryNameUpdateDto directoryNameUpdateDto) {
        UserDirectory userDirectory = userDirectoryRepository.findUserDirectoryById(id);
        userDirectory.updateName(directoryNameUpdateDto);
    }

    /**
     * user가 생성한 디렉터리 불러오기
     * @param request
     * @return
     */
    @Override
    public List<UserDirectory> findUserDirectory(HttpServletRequest request) {

        Member member = planService.findMemberId(request);

        return userDirectoryRepository.findUserDirectoryById(member);

    }
}