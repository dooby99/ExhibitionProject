package com.project.BodaProject.service;

import com.project.BodaProject.domain.User.UserEntity;
import com.project.BodaProject.dto.UserDto;
import com.project.BodaProject.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private EntityManager entityManager;

    public UserDto register(UserDto userDto) {
        return entityToDto(userRepository.save(dtoToEntity(userDto)));
    }

    public UserDto update(UserDto userDto) {
        return entityToDto(userRepository.save(dtoToEntity(userDto)));
    }

    public UserEntity dtoToEntity(UserDto userDto) {
        var entity = UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .createT(userDto.getCreateT())
                .build();
        return entity;
    }

    public UserDto entityToDto(UserEntity userEntity) {
        var dto = UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .CreateT(userEntity.getCreateT())
                .build();
        return dto;
    }

    //유저 정보 확인. 이름과 이메일 검색. null->false
    public boolean isExistsUserInfo(String name, String value) {
        Optional<UserEntity> user = null;
        switch (name) {
            case "name":
                user = userRepository.findByName(value);
                break;
            case "email":
                user = userRepository.findByEmail(value);
                break;
        }
        return user != null;
    }
    public UserDto login(UserDto userDto) {

        Optional<UserEntity> byEmail = userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다.)
            UserEntity userEntity = byEmail.get();
            if (userEntity.getPassword().equals(userDto.getPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                UserDto dto = UserDto.toUserDto(userEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인 실패)
                return null;
            }
        } else {
            //조회 결과가 없다(해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }
}
