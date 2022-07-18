package com.trablock.web.service.plan.interfaceC;

import com.trablock.web.controller.form.UserDirectoryForm;
import com.trablock.web.dto.plan.DirectoryNameUpdateDto;
import com.trablock.web.entity.plan.UserDirectory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserDirectoryService {

    void saveUserDirectory(UserDirectory userDirectory);

    void deleteUserDirectory(UserDirectoryForm userDirectoryForm, Long memberId);

    List<UserDirectory> findMainUserDirectoryMain(HttpServletRequest request);

    void createUserDirectory(HttpServletRequest request, UserDirectoryForm userDirectoryForm, HttpServletResponse response);

    void updateDirectoryName(Long id, DirectoryNameUpdateDto directoryNameUpdateDto, Long memberId);

    List<UserDirectory> findUserDirectory(HttpServletRequest request);
}
