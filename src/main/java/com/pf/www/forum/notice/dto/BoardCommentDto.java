package com.pf.www.forum.notice.dto;

import java.util.HashMap;

public class BoardCommentDto {
	private Integer commentSeq;
	private Integer lvl;
	private String content;
	private int boardSeq;
	private int boardTypeSeq;
	private int memberSeq;
	private int parentCommentSeq;
	private String regDtm;
	private String updateDtm;
	private String deleteDtm;
	private String memberNm;
	
	public Integer getCommentSeq() {
		return commentSeq;
	}
	public void setCommentSeq(Integer commentSeq) {
		this.commentSeq = commentSeq;
	}
	public Integer getLvl() {
		return lvl;
	}
	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
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
	public int getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(int memberSeq) {
		this.memberSeq = memberSeq;
	}
	public int getParentCommentSeq() {
		return parentCommentSeq;
	}
	public void setParentCommentSeq(int parentCommentSeq) {
		this.parentCommentSeq = parentCommentSeq;
	}
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	public String getUpdateDtm() {
		return updateDtm;
	}
	public void setUpdateDtm(String updateDtm) {
		this.updateDtm = updateDtm;
	}
	public String getDeleteDtm() {
		return deleteDtm;
	}
	public void setDeleteDtm(String deleteDtm) {
		this.deleteDtm = deleteDtm;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	
	public static BoardCommentDto setBoardCommentDto(HashMap<String, String> params) {
		
		BoardCommentDto commentDto = new BoardCommentDto();
		commentDto.setCommentSeq(Integer.parseInt("commentSeq"));
		commentDto.setBoardSeq(Integer.parseInt(params.get("boardSeq")));
		commentDto.setBoardTypeSeq(Integer.parseInt(params.get("boardTypeSeq")));
		commentDto.setContent(params.get("content"));
		
		return commentDto;
	}
	
	@Override
	public String toString() {
		return "BoardCommentDto [commentSeq=" + commentSeq + ", lvl=" + lvl + ", content=" + content + ", boardSeq="
				+ boardSeq + ", boardTypeSeq=" + boardTypeSeq + ", memberSeq=" + memberSeq + ", parentCommentSeq="
				+ parentCommentSeq + ", regDtm=" + regDtm + ", updateDtm=" + updateDtm + ", deleteDtm=" + deleteDtm
				+ ", memberNm=" + memberNm + "]";
	}
}
