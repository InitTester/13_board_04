package com.pf.www.forum.notice.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pf.www.forum.notice.dao.BoardCommentDao;
import com.pf.www.forum.notice.dto.BoardCommentDto;

@Service
public class BoardCommentService {
	
	@Autowired
	BoardCommentDao boardCommentDao;
	
	public List<BoardCommentDto> getboardCommentList(Integer boardSeq, Integer boardTypeSeq){
		return boardCommentDao.getboardCommentList(boardSeq, boardTypeSeq);
	}
	
	/* 게시글 댓글 추가 */
	public int addComment(BoardCommentDto boardCommentDto) {
		return boardCommentDao.addComment(boardCommentDto);
	}
	
	/* 게시글 댓글 삭제 */
	public int deleteComment(HashMap<String, String> params) {
		return boardCommentDao.deleteBoardComment(params);
	}
	
	/* 게시글 댓글 수정 */
	public int modifyComment(HashMap<String, String> params) {
		return boardCommentDao.modifyBoardComment(params);
	}
}
