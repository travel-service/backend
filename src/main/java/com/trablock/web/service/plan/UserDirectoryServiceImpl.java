package com.trablock.web.service.plan;

import com.trablock.web.controller.form.StateChangeForm;
import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.Plan;
import com.trablock.web.entity.plan.PlanItem;
import com.trablock.web.entity.plan.Status;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.repository.plan.PlanItemRepository;
import com.trablock.web.repository.plan.PlanRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.planInterface.UserDirectoryService;
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
    private final PlanServiceImpl planServiceImpl;
    private final PlanRepository planRepository;
    private final PlanItemRepository planItemRepository;

    @Transactional
    @Override
    public void saveUserDirectory(UserDirectory userDirectory) {
        userDirectoryRepository.save(userDirectory);
    }

    //user 디렉터리 삭제
    @Transactional
    @Override
    public void deleteUserDirectory(Long userDirectoryId) {
        UserDirectory userDirectoryById = userDirectoryRepository.findUserDirectoryById(userDirectoryId);
        userDirectoryById.delete();
    }

    //user directory GET 요청
    @Override
    public List<UserDirectory> findMainUserDirectoryMain(HttpServletRequest request) {
        Member memberId = planServiceImpl.findMemberId(request);
        return userDirectoryRepository.findMemberIdForList(Optional.ofNullable(memberId));
    }

    //user directory 생성
    @Transactional
    @Override
    public void createUserDirectory(HttpServletRequest request, UserDirectoryForm userDirectoryForm, HttpServletResponse response) {

        Member memberId = planServiceImpl.findMemberId(request);

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

}