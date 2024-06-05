package com.pf.www.forum.notice.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pf.www.forum.notice.dao.BoardAttachDao;
import com.pf.www.forum.notice.dao.BoardDao;
import com.pf.www.forum.notice.dto.BoardAttachDto;
import com.pf.www.forum.notice.dto.BoardDto;
import com.pf.www.forum.notice.util.FileUtil;

@Service
public class BoardService {
	private final static Logger log = LoggerFactory.getLogger(BoardService.class);

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BoardAttachDao attachDao;
	
	@Autowired
	private FileUtil fileUtil;

	/* 게시글 리스트 */
	public List<BoardDto> getList(HashMap<String, String> params, Integer page, Integer size) {
		return boardDao.getList(params, page, size);
	}

	/* 게시글 디테일 */
	public BoardDto getBoardDetail(Integer board_seq) {
		return boardDao.getBoardDetail(board_seq);
	}
	
	/* 게시글 작성 
	 * 기존의 게시글 추가 하는 부분에 추가적으로 파일 데이터가 있다면 추가로 저장
	 *  1. 기존 게시글 저장 시 생성 되는 pk(boardSeq) 값은 어떻게 불러오는가 ?
	 *      springFramework 에서 지원 되는 기능으로 KeyHolder라는 인터페이스가 있다
	 *      키값이 여러개인 경우 Map에 저장되어 사용 가능하다.
	 *  2. 보통 insert/update/delete 는 int 이지만 boolean로 처리 하기도 한다
	 *  	이유는 ? 성공 또는 실패 표시, 결과의 간결한 표현, 일관성 유지, 간단한 사용으로 간결한 표현이 가능하다 */
	public boolean addBoard(HashMap<String, String> params, MultipartFile[] mfs) {
		
		/* boardSeq */
		int pk = boardDao.addBoard(params); 
		params.put("boardSeq", String.valueOf(pk));
		
		log.info("addBoard :: pk : " + pk);
		
		File destFile = null;
		
		try {
			// DTO 생성, 값 매핑-> 테이블 저장
			for(MultipartFile mf : mfs) {
				/* 파일이 1~3개 등록시 등록된 정보가 없으면 추가 안되게 처리 */
				if(!mf.isEmpty()) {
					// 물리적 파일 저장
					destFile = fileUtil.saveFile(mf);
					
					/*uuid file name*/
					String unqFileNm = UUID.randomUUID().toString().replaceAll("-", "");
					
					log.info("addBoard :: unqFileNm : " + unqFileNm);
					params.put("unqFileNm", unqFileNm);
					BoardAttachDto attachDto = new BoardAttachDto().setBoardAatachDto(params, mf, destFile);
					
					attachDao.addBoardAttach(attachDto);
				}
			}
			return true;
		}catch(DataAccessException e) {
			log.info(" addBoard :: e.getMessage()={}", e.getMessage());		
			return false;				
		} catch(Exception e) {
			/* 오류가 났을 때 저장된 파일 삭제 시켜주자
			 * 쓸데없는 파일이 많이 쌓여 있으면 디스크가 터질 일이 있기 때문에 오류난 
			 * 파일은 삭제 해주는 것이 좋다.
			 */
			if(!ObjectUtils.isEmpty(destFile)) {
				destFile.delete();
				log.info(" addBoard :: e.getMessage()={}", e.getMessage());	
			}
			return false;
		}
	}
	
	/* 게시글 파일 다운로드 가능 데이터 */
	public BoardAttachDto getBoardFileInfo(Integer boardSeq, Integer boardTypeSeq) {
		return attachDao.getBoardAttach(boardSeq, boardTypeSeq);
	}
	
	public BoardAttachDto getBoardFileInfo(Integer attachSeq) {
		return attachDao.getBoardAttach(attachSeq);
	}	

	/* 게시글 파일 다운로드 가능 전체 데이터 */
	public List<BoardAttachDto> getBoardFileInfoList(Integer boardSeq, Integer boardTypeSeq) {
		return attachDao.getBoardAttachList(boardSeq, boardTypeSeq);
	}
	
	/* 전체 다운로드 파일 업데이트, 압축파일 생성 */
	public File getDownloadFiles(Integer boardSeq, Integer boardTypeSeq) {
		List<BoardAttachDto> attachDtos = attachDao.getBoardAttachList(boardSeq, boardTypeSeq);
		
		/* 다운로드 수 증가 */
		for(BoardAttachDto attachDto : attachDtos) {
			attachDao.updateDownloadCnt(attachDto.getAttachSeq());
		}
		
		return fileUtil.makeZipFiles(attachDtos);
	}
	
	public int deleteBoardFile(Integer boardSeq, Integer boardTypeSeq) {		
		return attachDao.deleteBoardAttach(boardSeq, boardTypeSeq);
	}

	public int deleteBoardFileOne(Integer attachSeq) {		
		return attachDao.deleteOneBoardAttach(attachSeq);
	}
	
	/* 게시글 수정 */
	public int updateBoard(HashMap<String, String> params, MultipartFile[] mfs) {
		
		int cnt = 0;

		File destFile = null;
		
		try {
			cnt = boardDao.updateBoard(params);
			
			// DTO 생성, 값 매핑-> 테이블 저장
			for(MultipartFile mf : mfs) {
				/* 파일이 1~3개 등록시 등록된 정보가 없으면 추가 안되게 처리 */
				if(!mf.isEmpty()) {
					// 물리적 파일 저장
					destFile = fileUtil.saveFile(mf);
					
					/*uuid file name*/
					String unqFileNm = UUID.randomUUID().toString().replaceAll("-", "");
					
					log.info("updateBoard :: unqFileNm : " + unqFileNm);
					params.put("unqFileNm", unqFileNm);
					BoardAttachDto attachDto = new BoardAttachDto().setBoardAatachDto(params, mf, destFile);
					
					attachDao.addBoardAttach(attachDto);
				}
			}
		}catch(DataAccessException e) {
			log.info(" updateBoard :: e.getMessage()={}", e.getMessage());		
			cnt = -1;		
		} catch(Exception e) {
			/* 오류가 났을 때 저장된 파일 삭제 시켜주자
			 * 쓸데없는 파일이 많이 쌓여 있으면 디스크가 터질 일이 있기 때문에 오류난 
			 * 파일은 삭제 해주는 것이 좋다.
			 */
			log.info(" updateBoard :: e.getMessage()={}", e.getMessage());	
			if(!ObjectUtils.isEmpty(destFile)) {
				destFile.delete();
			}
			cnt = -1;
		}
		
		return cnt;
	}
	
	/* 게시글 삭제 */
	public int deleteBoard(HashMap<String, String> params) {
		
		int boardSeq = Integer.parseInt(params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		
		if(attachDao.getBoardAttachCnt(boardSeq,boardTypeSeq) >0) {

			log.info("deleteBoardFile :: boardSeq : " + boardSeq + ", boardTypeSeq : " + boardTypeSeq);
			int cnt = attachDao.deleteBoardAttach(boardSeq, boardTypeSeq);
			
			log.info("cnt : " + cnt);
			
			if(cnt!=1)
				return cnt;
		}
		
		return boardDao.deleteBoard(params);
	}

	/* 페이징 처리를 위한 전체 페이지 */
	public HashMap<String, Integer> getTotalListPage(Integer size, Integer page) {

		HashMap<String, Integer> pageHandler = new HashMap<String, Integer>();

		/*
		 * pageHandler에 필요한 변수 선언 
		 * totalPage 전체 게시물 개수 
		 * page 현재 페이지 번호 
		 * size 페이지당 게시물 수
		 * begin 시작페이지 
		 * end 끝 페이지
		 * prev 이전 화살표 
		 * next 다음 화살표
		 */

		int totalPage = boardDao.getTotalPage();
		int totalPageSize = totalPage / size;

		int begin = page == 0 ? 1 : ((page - 1) / size) * size + 1;
		int end = Math.min(begin + size - 1, totalPageSize);

		int prev = begin;
		int next = end != totalPage ? end : totalPage;

		pageHandler.put("totalPage", totalPage);
		pageHandler.put("totalPageSize", totalPageSize);
		pageHandler.put("begin", begin);
		pageHandler.put("end", end);
		pageHandler.put("size", size);
		pageHandler.put("prev", prev);
		pageHandler.put("next", next);

		return pageHandler;
	}
	
	/* 좋아요/싫어요 */
	public int vote(Integer boardSeq, Integer boardTypeSeq, String isLike, Integer memberSeq, String ip) {
		
		log.info("test aaaa " + isLike + ", length : " + isLike.length() );
		
		int result = -1;
		
		try {
			String beforeisLike = boardDao.getEmptyVote(boardSeq, boardTypeSeq, memberSeq);
			
			log.info("beforeisLike : " + beforeisLike);
			
			if(beforeisLike.equals(isLike)) {
				 boardDao.deleteVote(boardSeq, boardTypeSeq, memberSeq, isLike);		
				 result = 0;
			}else {
				boardDao.updateVote(boardSeq, boardTypeSeq, memberSeq, isLike,ip);
				result = 2;
			}
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			boardDao.addVote(boardSeq, boardTypeSeq, memberSeq, isLike, ip);
			result = 1;
		}
		return result;
	}
	
	/* 좋아요/싫어요 조회 */
	public String getEmptyVote(Integer boardSeq, Integer boardTypeSeq, Integer memberSeq) {
		return boardDao.getEmptyVote(boardSeq, boardTypeSeq, memberSeq);
	}
	
}
