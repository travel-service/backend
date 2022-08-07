package com.trablock.web.service.plan;

import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.global.HTTPStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.interfaceC.UserDirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.trablock.web.converter.Converter.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDirectoryServiceImpl implements UserDirectoryService {

    private final UserDirectoryRepository userDirectoryRepository;
    private final MemberRepository memberRepository;

    //user 디렉터리 삭제
    @Override
    @Transactional
    public void deleteUserDirectory(UserDirectoryForm userDirectoryForm, Long memberId) {
        for (int i = 0; i < userDirectoryForm.getUserDirectoryId().size(); i++) {
            userDirectoryRepository.deleteById(userDirectoryForm.getUserDirectoryId().get(i));
        }
    }

    //user directory GET 요청
    @Override
    public List<UserDirectory> findMainUserDirectoryMain(Long memberId) {
        return userDirectoryRepository.findMemberIdForList(memberId);
    }

    //user directory 생성
    @Override
    @Transactional
    public CreateUserDirectory createUserDirectory(Long memberId, UserDirectoryForm userDirectoryForm) {

        if (memberRepository.findMemberId(memberId) == null) {
            String errorMessage = "존재하지 않은 사용자입니다..";

            return new CreateUserDirectory(HTTPStatus.InternalServerError.getCode(), errorMessage, null);
        }

        int memberIdForCount = userDirectoryRepository.findMemberIdForCount(memberId);

        if (memberIdForCount >= 5) {
            String errorMessage = "디렉터리는 5개 까지만 생성 가능합니다.";

            return new CreateUserDirectory(HTTPStatus.BadRequest.getCode(), errorMessage, null);
        } else {
            UserDirectory userDirectory = UserDirectory.builder()
                    .directoryName(userDirectoryForm.getDirectoryName())
                    .member(memberRepository.findById(memberId).orElseThrow())
                    .build();

            UserDirectory userDirectories = userDirectoryRepository.save(userDirectory);

            String message = "디렉터리가 정상적으로 생성되었습니다.";

            return new CreateUserDirectory(HTTPStatus.Created.getCode(), message, userDirectories.getId());
        }
    }

    // user directory 이름 변경
    @Override
    @Transactional
    public UpdatePlanName updateDirectoryName(Long userDirectoryId,
                                    DirectoryNameUpdateDto directoryNameUpdateDto,
                                    Long memberId) {

        if (userDirectoryRepository.findUserDirectoryById(userDirectoryId, memberId) == null) {
            String errorMessage = "존재하지 않는 디렉터리입니다.";

            return new UpdatePlanName(HTTPStatus.BadRequest.getCode(), errorMessage);
        } else {
            UserDirectory userDirectory = userDirectoryRepository.findUserDirectoryById(userDirectoryId, memberId);
            userDirectory.updateName(directoryNameUpdateDto);

            String message = "디렉터리 이름이 정상적으로 변경되었습니다.";

            return new UpdatePlanName(HTTPStatus.Created.getCode(), message);
        }
    }

    /**
     * user가 생성한 디렉터리 불러오기
     * @param memberId
     * @return
     */
    @Override
    public List<UserDirectory> findUserDirectory(Long memberId) {
        return userDirectoryRepository.findUserDirectoryById(memberId);
    }
}