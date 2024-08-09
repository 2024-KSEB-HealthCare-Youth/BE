package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.item.domain.Category;
import com.keb.fmhj.item.domain.Item;
import com.keb.fmhj.item.domain.repository.ItemRepository;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.MypageReqeustDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.dto.response.MypageResponseDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import com.keb.fmhj.resultItem.domain.ResultItem;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResultRepository resultRepository;
    private final ItemRepository itemRepository;

    // 회원 등록
    @Transactional
    public void join(SignUpDto signUpDtoDto) {

        validateExistMember(signUpDtoDto);

        Member joinMember = Member.builder()
                .loginId(signUpDtoDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(signUpDtoDto.getPassword()))
                .name(signUpDtoDto.getName())
                .nickName(signUpDtoDto.getNickName())
                .gender(signUpDtoDto.getGender())
                .age(signUpDtoDto.getAge())
                .phoneNumber(signUpDtoDto.getPhoneNumber())
                .email(signUpDtoDto.getEmail())
                .profileImage(signUpDtoDto.getProfileImage())
                .isAdmin(signUpDtoDto.getIsAdmin())
                .build();

        memberRepository.save(joinMember);
    }

    // 단일 회원 조회
    @Transactional(readOnly = true)
    public MemberDetailDto getMemberDetails(String loginId) {

        Member member = ensureMemberExists(loginId);
        return MemberDetailDto.toDto(member);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<MemberDetailDto> getAllMemberDetails() {

        return memberRepository
                .findAll()
                .stream()
                .map(MemberDetailDto::toDto)
                .collect(Collectors.toList());
    }

    // 회원 수정
    @Transactional
    public void updateMember(String loginId, UpdateMemberDto updateDto) {

        Member member = ensureMemberExists(loginId);
        validateMemberOwner(member, loginId);
        member.update(updateDto.getName(), updateDto.getNickName(), updateDto.getEmail(), updateDto.getPhoneNumber(), updateDto.getAge(), updateDto.getGender(), updateDto.getProfileImage());

        memberRepository.save(member);
    }

    // 회원 삭제
    @Transactional
    public void deleteMember(String loginId) {

        Member member = ensureMemberExists(loginId);
        validateMemberOwner(member, loginId);

        memberRepository.delete(member);
    }

    // 마이페이지
    @Transactional
    public MypageResponseDto getMypage(String loginId, MypageReqeustDto requestDto) {

        Member member = ensureMemberExists(loginId);

        // 결과 저장
        Result result = Result.builder()
                .member(member)
                .advancedSkinType(Optional.ofNullable(requestDto.getAdvancedSkinType()).orElse(Collections.emptyList()))
                .basicSkinType(requestDto.getBasicSkinType())
                .faceImage(requestDto.getFaceImage())
                .probability(requestDto.getProbabilities())
                .details(requestDto.getResultDetails())
                .resultItems(new ArrayList<>()) // 초기화
                .build();

        List<Item> cosResultItem = requestDto.getCosNames().stream()
                .map(name -> {
                    int index = requestDto.getCosNames().indexOf(name);
                    String imgPath = requestDto.getCosPaths().get(index);
                    return Item.builder()
                            .name(name)
                            .itemImage(imgPath)
                            .category(Category.COSMETIC)
                            .build();
                }).collect(Collectors.toList());

        List<Item> nutrResultItem = requestDto.getNutrNames().stream()
                .map(name -> {
                    int index = requestDto.getNutrNames().indexOf(name);
                    String imgPath = requestDto.getNutrPaths().get(index);
                    return Item.builder()
                            .name(name)
                            .itemImage(imgPath)
                            .category(Category.NUTRIENT)
                            .build();
                }).collect(Collectors.toList());

        itemRepository.saveAll(cosResultItem);
        itemRepository.saveAll(nutrResultItem);

        List<ResultItem> cosResultItems = cosResultItem.stream()
                .map(item -> ResultItem.builder()
                        .result(result)
                        .item(item)
                        .build())
                .collect(Collectors.toList());

        List<ResultItem> nutrResultItems = nutrResultItem.stream()
                .map(item -> ResultItem.builder()
                        .result(result)
                        .item(item)
                        .build())
                .collect(Collectors.toList());

        result.getResultItems().addAll(cosResultItems);
        result.getResultItems().addAll(nutrResultItems);

        resultRepository.save(result);

        return MypageResponseDto.builder()
                .name(member.getName())
                .nickname(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .probabilities(result.getProbability())
                .resultDetails(result.getDetails())
                .basicSkinType(result.getBasicSkinType())
                .advancedSkinType(result.getAdvancedSkinType().stream().toList())
                .build();
    }

    @Transactional
    public MypageResponseDto getResult(String loginId){

        Member member = ensureMemberExists(loginId);
        Result result = resultRepository.findLatestResultByMemberId(member.getId());

        return MypageResponseDto.builder()
                .name(member.getName())
                .nickname(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .probabilities(result.getProbability())
                .resultDetails(result.getDetails())
                .basicSkinType(result.getBasicSkinType())
                .advancedSkinType(result.getAdvancedSkinType().stream().toList())
                .build();
    }

    private List<Item> createItems(List<String> names, List<String> paths, Category category) {
        return IntStream.range(0, names.size())
                .mapToObj(i -> Item.builder()
                        .name(names.get(i))
                        .itemImage(paths.get(i))
                        .category(category)
                        .build())
                .collect(Collectors.toList());
    }

    private List<ResultItem> createResultItems(Result result, List<Item> items) {
        return items.stream()
                .map(item -> ResultItem.builder()
                        .result(result)
                        .item(item)
                        .build())
                .collect(Collectors.toList());
    }

    // 회원 존재 유무 검증
    private Member ensureMemberExists(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 회원 본인인지 검증
    private void validateMemberOwner(Member member, String loginId) {
        if(!member.getLoginId().equals(loginId)){
            throw YouthException.from(ErrorCode.MEMBER_NOT_AUTHENTICATED);
        }
    }

    // 회원 존재 유무 검증
    private void validateExistMember(SignUpDto joinDto) {

        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.DUPLICATE_MEMBER_LOGIN_ID);
        }
    }
}