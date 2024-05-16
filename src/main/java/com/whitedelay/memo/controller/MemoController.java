package com.whitedelay.memo.controller;

import com.whitedelay.memo.dto.MemoRequestDto;
import com.whitedelay.memo.dto.MemoResponseDto;
import com.whitedelay.memo.service.MemoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 모두 json으로 반환할 것이기 때문에, @Controller대신 @RestController을 이용
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) { // 생성자 주입
        this.memoService = memoService;
    }

    /**
     * getMemoList 메모 리스트 출력
     * @return List<MemoResponseDto> 메모 정보가 담긴 리스트
     */
    @GetMapping("")
    public List<MemoResponseDto> getMemoList() {
        return memoService.getMemoList();
    }

    /**
     * searchMemo 메모 내용 검색
     * @param keyword 검색할 내용
     * @return 검색된 메모 정보가 담긴 리스트
     */
    @GetMapping("/search")
    public List<MemoResponseDto> searchMemo(@RequestParam("keyword")String keyword) {
        return memoService.searchMemo(keyword);
    }

    /**
     * postMemo 메모 작성
     * @param memoRequestDto 메모에 적을 내용
     * @return long 작성된 메모 아이디
     */
    @PostMapping("")
    public Long postMemo(@RequestBody MemoRequestDto memoRequestDto) { // @RequestBody를 적지 않으면 null값이 들어옴! DTO 사용 시 반드시 써야 함
        return memoService.postMemo(memoRequestDto);
    }

    /**
     * updateMemo 메모 수정
     * @param id 수정할 메모 아이디
     * @param memoRequestDto 수정할 내용
     * @return long 수정된 메모 아이디
     */
    @PutMapping("/{id}")
    public Long updateMemo(@PathVariable("id") Long id, @RequestBody MemoRequestDto memoRequestDto) {
        return memoService.updateMemo(id, memoRequestDto);
    }

    /**
     * deleteMemo 메모 삭제
     * @param id 삭제할 메모 아이디
     * @return long 삭제된 메모 아이디
     */
    @DeleteMapping("/{id}")
    public Long deleteMemo(@PathVariable("id") Long id) {
        return memoService.deleteMemo(id);
    }
}
