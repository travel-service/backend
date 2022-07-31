package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.converter.Converter;
import com.trablock.web.converter.Converter.CreateUserDirectory;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.dto.plan.UserDirectoryDto;
import com.trablock.web.entity.member.Member;
import com.trablock.web.entity.plan.UserDirectory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.trablock.web.converter.Converter.*;

public interface UserDirectoryService {

    void deleteUserDirectory(UserDirectoryForm userDirectoryForm, Long memberId);

    List<UserDirectory> findMainUserDirectoryMain(Long memberId);

    CreateUserDirectory createUserDirectory(Long memberId, UserDirectoryForm userDirectoryForm);

    UpdatePlanName updateDirectoryName(Long id, DirectoryNameUpdateDto directoryNameUpdateDto, Long memberId);

    List<UserDirectory> findUserDirectory(Long memberId);
}
