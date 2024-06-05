package com.pf.www.forum.notice.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pf.www.forum.notice.dto.BoardCommentDto;
import com.pf.www.forum.notice.service.BoardCommentService;

@RestController
public class RestCommentController {
	
	private final static Logger log = LoggerFactory.getLogger(RestCommentController.class);
	
	@Autowired
	private BoardCommentService boardCommentService;

	@PostMapping("/forum/notice/comment.do")
	@ResponseBody
	public int addComment(@RequestBody BoardCommentDto dto, HttpServletRequest request) {
		
		HttpSession session = request.getSession();		
		//int memberSeq = !session.getAttribute("memberSeq").toString().isEmpty() ? (int)session.getAttribute("memberSeq") : 1;
		int memberSeq = 1;
		dto.setMemberSeq(memberSeq);
		
		System.out.println(dto);

		return boardCommentService.addComment(dto);
	}
}
