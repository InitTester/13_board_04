package com.pf.www.forum.notice.dto;

import java.util.HashMap;

public class BoardDto {
	
	private int boardSeq;
	private int boardTypeSeq;
	private String boardTypeNm;
	private String title;
	private String content;
	private int hit;
	private String delYn;
	private String regDtm;
	private int regMemberSeq;
	private String regMemberId;
	private String updateDtm;
	private int updateMemberSeq;
	private int commentcnt;
	private int filecnt;
	
	public int getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public int getBoardTypeSeq() {
		return boardTypeSeq;
	}
	public void setBoardTypeSeq(int boardTypeSeq) {
		this.boardTypeSeq = boardTypeSeq;
	}
	public String getBoardTypeNm() {
		return boardTypeNm;
	}
	public void setBoardTypeNm(String boardTypeNm) {
		this.boardTypeNm = boardTypeNm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	public int getRegMemberSeq() {
		return regMemberSeq;
	}
	public void setRegMemberSeq(int regMemberSeq) {
		this.regMemberSeq = regMemberSeq;
	}
	public String getRegMemberId() {
		return regMemberId;
	}
	public void setRegMemberId(String regMemberId) {
		this.regMemberId = regMemberId;
	}
	public String getUpdateDtm() {
		return updateDtm;
	}
	public void setUpdateDtm(String updateDtm) {
		this.updateDtm = updateDtm;
	}
	public int getUpdateMemberSeq() {
		return updateMemberSeq;
	}
	public void setUpdateMemberSeq(int updateMemberSeq) {
		this.updateMemberSeq = updateMemberSeq;
	}
	public int getCommentcnt() {
		return commentcnt;
	}
	public void setCommentcnt(int commentcnt) {
		this.commentcnt = commentcnt;
	}
	
	public int getFilecnt() {
		return filecnt;
	}
	public void setFilecnt(int filecnt) {
		this.filecnt = filecnt;
	}
	public static BoardDto setBoardDto(HashMap<String, String> params) {
		
		BoardDto dto = new BoardDto();
		dto.setTitle(params.get("title"));
		dto.setContent(params.get("trumbowyg-demo"));
		dto.setRegMemberSeq(Integer.parseInt(params.get("memberSeq")));
		dto.setBoardSeq(Integer.parseInt(params.get("boardSeq")));
		dto.setBoardTypeSeq(Integer.parseInt(params.get("boardTypeSeq")));
		
		return dto;
	}
    
}
