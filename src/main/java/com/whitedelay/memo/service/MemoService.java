package com.whitedelay.memo.service;

import com.whitedelay.memo.dto.MemoRequestDto;
import com.whitedelay.memo.dto.MemoResponseDto;
import com.whitedelay.memo.entity.Memo;
import com.whitedelay.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) { // 생성자 주입
        this.memoRepository = memoRepository;
    }

    /**
     * getMemoList 메모 리스트 출력
     * @return List<MemoResponseDto> 메모 정보가 담긴 리스트
     */
    public List<MemoResponseDto> getMemoList() {
        List<MemoResponseDto> memoResponseDtoList = new ArrayList<>();
        List<Memo> memos = memoRepository.findAllByOrderByCreatedAtDesc();

        memos.stream().toList().forEach(memo -> {
            memoResponseDtoList.add(new MemoResponseDto(memo.getId(), memo.getContents(), memo.getCreatedAt()));
        });

        return memoResponseDtoList;
    }

    /**
     * searchMemo 메모 내용 검색
     * @param keyword 검색할 내용
     * @return 검색된 메모 정보가 담긴 리스트
     */
    public List<MemoResponseDto> searchMemo(String keyword) {
        List<MemoResponseDto> memoResponseDtoList = new ArrayList<>();
        List<Memo> memos = memoRepository.findByContentsContainingOrderByCreatedAtDesc(keyword);

        memos.stream().toList().forEach(memo -> {
            memoResponseDtoList.add(new MemoResponseDto(memo.getId(), memo.getContents(), memo.getCreatedAt()));
        });

        return memoResponseDtoList;
    }

    /**
     * postMemo 메모 작성
     * @param memoRequestDto 메모에 적을 내용
     * @return long 작성된 메모 아이디
     */
    public Long postMemo(MemoRequestDto memoRequestDto) {
        Memo memo = memoRepository.save(new Memo(memoRequestDto.getContents(), LocalDateTime.now())); // memo만들 때 id를 넣지 않아도 되는 이유: Memo Entity에 @GeneratedValue를 선언해주었기 때문!

        return memo.getId();
    }

    /**
     * updateMemo 메모 수정
     * @param id 수정할 메모 아이디
     * @param memoRequestDto 수정할 내용
     * @return long 수정된 메모 아이디
     */
    @Transactional
    public Long updateMemo(Long id, MemoRequestDto memoRequestDto) {
        Memo memo =  memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다."));

        memo.setContents(memoRequestDto.getContents());

        return memo.getId();
    }

    /**
     * deleteMemo 메모 삭제
     * @param id 삭제할 메모 아이디
     * @return long 삭제된 메모 아이디
     */
    public Long deleteMemo(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다."));

        memoRepository.delete(memo);

        return memo.getId();
    }
}
