package com.keb.fmhj.result.presentaion;


import com.keb.fmhj.result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    //임시매개변수-> 멤버 아이디
    @GetMapping("/lists")
    public ResponseEntity<?> getResultList(Long memberId){
        return ResponseEntity.ok().body(resultService.getResultList(memberId));
    }

    @GetMapping("/{resultId}")
    public ResponseEntity<?> getResultDetail(Long memberId, @PathVariable Long resultId){
        return ResponseEntity.ok().body(resultService.getResultDetail(memberId, resultId));
    }
}
