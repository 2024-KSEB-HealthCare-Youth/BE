package com.keb.fmhj.result.service;

import com.keb.fmhj.member.domain.dto.request.DiagnosisResult;
import com.keb.fmhj.result.domain.response.LastResultDetail;
import com.keb.fmhj.result.domain.response.ResultList;
import com.keb.fmhj.result.domain.response.SavedResult;

import java.util.List;

public interface ResultService {

    List<ResultList> getResultList(String loginId);
    LastResultDetail getResultDetail(String LoginId,Long resultId);
    SavedResult saveResult(String loginId, DiagnosisResult diagnosisResult);
}
