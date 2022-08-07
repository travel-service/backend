package com.trablock.web.service.plan;

import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.converter.Converter.CreateUserDirectory;
import com.trablock.web.converter.Converter.UpdatePlanName;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.entity.member.Gender;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.member.MemberInfo;
import com.trablock.web.entity.member.MemberProfile;
import com.trablock.web.entity.plan.UserDirectory;
import com.trablock.web.global.HTTPStatus;
import com.trablock.web.repository.member.MemberRepository;
import com.trablock.web.repository.plan.UserDirectoryRepository;
import com.trablock.web.service.plan.interfaceC.UserDirectoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserDirectoryServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserDirectoryService userDirectoryService;

    @Autowired
    UserDirectoryRepository userDirectoryRepository;

    Member member;

    @BeforeEach
    void init() {
        Member initMember = new Member("username", "1234",
                new MemberProfile("nickname", "bio"),
                new MemberInfo("19980102", Gender.MALE, "wkdwoo@kakao.com"),
                new ArrayList<>(), true);

        member = memberRepository.save(initMember);
    }

    @Test
    @DisplayName("사용자 디렉터리 생성 test")
    public void createUserDirectoryTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        String message = "디렉터리가 정상적으로 생성되었습니다.";

        //when
        CreateUserDirectory createUserDirectory = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm);

        UserDirectory userDirectory = userDirectoryRepository.findById(2L).orElseThrow();

        //then
        assertThat(createUserDirectory.getUserDirectoryId()).isEqualTo(userDirectory.getId());
        assertThat(createUserDirectory.getHttpStatus()).isEqualTo(HTTPStatus.Created.getCode());
        assertThat(createUserDirectory.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("사용자 디렉터리 생성 시 인가 되지 않는 사용자 예외 test")
    public void createUserDirectoryMemberExceptionTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        String errorMessage = "존재하지 않은 사용자입니다..";

        //when
        CreateUserDirectory createUserDirectory = userDirectoryService.createUserDirectory(80L, userDirectoryForm);

        //then
        assertThat(createUserDirectory.getHttpStatus()).isEqualTo(HTTPStatus.InternalServerError.getCode());
        assertThat(createUserDirectory.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("사용자 디렉터리 생성 5개 이상 생성시 터지는 예외 test")
    public void createUserDirectoryCountExceptionTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm1 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm2 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm3 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm4 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm5 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm1);
        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm2);
        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm3);
        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm4);
        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm5);

        //when
        UserDirectoryForm exceptionCreateUserDirectoryForm5 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        CreateUserDirectory exception = userDirectoryService.createUserDirectory(member.getId(), exceptionCreateUserDirectoryForm5);

        String errorMessage = "디렉터리는 5개 까지만 생성 가능합니다.";

        //then
        assertThat(exception.getHttpStatus()).isEqualTo(HTTPStatus.BadRequest.getCode());
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("사용자 디렉터리 삭제 test")
    public void deleteUserDirectoryCountTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm1 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm2 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm3 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        CreateUserDirectory userDirectory1 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm1);
        CreateUserDirectory userDirectory2 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm2);
        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm3);

        List<Long> userDirectors = new ArrayList<>();

        userDirectors.add(userDirectory1.getUserDirectoryId());
        userDirectors.add(userDirectory2.getUserDirectoryId());

        UserDirectoryForm deleteUserDirectoryForm = UserDirectoryForm.builder()
                .userDirectoryId(userDirectors)
                .build();

        //when
        userDirectoryService.deleteUserDirectory(deleteUserDirectoryForm, member.getId());

        //then
    }

    @Test
    @DisplayName("user directory GET 요청 test")
    public void findMainUserDirectoryMainTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm1 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm2 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm3 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        CreateUserDirectory userDirectory1 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm1);
        CreateUserDirectory userDirectory2 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm2);
        CreateUserDirectory userDirectory3 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm3);

        List<Long> userDirectors = new ArrayList<>();

        userDirectors.add(userDirectory1.getUserDirectoryId());
        userDirectors.add(userDirectory2.getUserDirectoryId());
        userDirectors.add(userDirectory3.getUserDirectoryId());

        UserDirectoryForm userDirectoryForm = UserDirectoryForm.builder()
                .userDirectoryId(userDirectors)
                .build();

        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm);

        //when
        List<UserDirectory> mainUserDirectoryMain = userDirectoryService.findMainUserDirectoryMain(member.getId());

        //then
        assertThat(mainUserDirectoryMain.get(0).getId()).isEqualTo(userDirectory1.getUserDirectoryId());
        assertThat(mainUserDirectoryMain.get(1).getId()).isEqualTo(userDirectory2.getUserDirectoryId());
        assertThat(mainUserDirectoryMain.get(2).getId()).isEqualTo(userDirectory3.getUserDirectoryId());
    }

    @Test
    @DisplayName("user directory 이름 변경 test")
    public void updateDirectoryNameTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        CreateUserDirectory userDirectory1 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm);

        DirectoryNameUpdateDto updateDirectoryNameUpdateDto = DirectoryNameUpdateDto.builder()
                .directoryName("제주도 맛집 및 레저")
                .build();

        //when
        UpdatePlanName updatePlanName = userDirectoryService.updateDirectoryName(userDirectory1.getUserDirectoryId(), updateDirectoryNameUpdateDto, member.getId());

        //then
        UserDirectory userDirectory = userDirectoryRepository.findById(userDirectory1.getUserDirectoryId()).orElseThrow();

        String message = "디렉터리 이름이 정상적으로 변경되었습니다.";

        assertThat(userDirectory.getDirectoryName()).isEqualTo(updateDirectoryNameUpdateDto.getDirectoryName());
        assertThat(updatePlanName.getHttpStatus()).isEqualTo(HTTPStatus.Created.getCode());
        assertThat(updatePlanName.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("user directory 이름 변경시 존재하지 않는 디렉터리 예외 test")
    public void updateDirectoryNameExceptionTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm);

        DirectoryNameUpdateDto updateDirectoryNameUpdateDto = DirectoryNameUpdateDto.builder()
                .directoryName("제주도 맛집 및 레저")
                .build();

        //when
        UpdatePlanName updatePlanName = userDirectoryService.updateDirectoryName(10L, updateDirectoryNameUpdateDto, member.getId());

        //then
        String errorMessage = "존재하지 않는 디렉터리입니다.";

        assertThat(updatePlanName.getHttpStatus()).isEqualTo(HTTPStatus.BadRequest.getCode());
        assertThat(updatePlanName.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("user가 생성한 디렉터리 불러오기 test")
    public void findUserDirectoryTest() throws Exception {
        //given
        UserDirectoryForm userDirectoryForm1 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm2 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        UserDirectoryForm userDirectoryForm3 = UserDirectoryForm.builder()
                .directoryName("제주도 맛집")
                .build();

        CreateUserDirectory userDirectory1 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm1);
        CreateUserDirectory userDirectory2 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm2);
        CreateUserDirectory userDirectory3 = userDirectoryService.createUserDirectory(member.getId(), userDirectoryForm3);

        //when
        List<UserDirectory> userDirectory = userDirectoryService.findUserDirectory(member.getId());

        //then
        assertThat(userDirectory.get(0).getId()).isEqualTo(userDirectory1.getUserDirectoryId());
        assertThat(userDirectory.get(1).getId()).isEqualTo(userDirectory2.getUserDirectoryId());
        assertThat(userDirectory.get(2).getId()).isEqualTo(userDirectory3.getUserDirectoryId());
    }
}