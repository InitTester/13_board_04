package com.pf.www.forum.notice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.pf.www.forum.notice.dto.BoardCommentDto;

@Repository
public class BoardCommentDao extends JdbcTemplate{
	
	private final static Logger log = LoggerFactory.getLogger(BoardCommentDao.class);
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		super.setDataSource(dataSource);
	}
	
	/* 게시글 댓글 리스트 */
	public List<BoardCommentDto> getboardCommentList(Integer boardSeq, Integer boardTypeSeq){
		
		String sql = "SELECT a.comment_seq, "
				+ " 		 a.lvl, "
				+ " 		 a.content, "
				+ " 		 a.board_seq, "
				+ " 		 a.board_type_seq, "
				+ " 		 a.member_seq, "
				+ " 		 a.parent_seq, "
				+ " 		 a.reg_dtm, "
				+ " 		 a.update_dtm, "
				+ " 		 a.delete_dtm, "
				+ "			 m.member_nm "
				+ "  FROM ( SELECT p.comment_seq, "
				+ "	               p.lvl, "
				+ "	               p.content, "
				+ "	               p.board_seq, "
				+ "	               p.board_type_seq, "
				+ "	               p.member_seq, "
				+ "	               IFNULL(p.parent_comment_seq, s.parent_comment_seq) AS parent_seq, "
				+ "	               p.reg_dtm, "
				+ "	               p.update_dtm, "
				+ "	               p.delete_dtm "
				+ "	       FROM board_comment p "
				+ "	 	   LEFT JOIN board_comment s on s.parent_comment_seq = p.comment_seq) a "
				+ "  JOIN member m on a.member_seq = m.member_seq "
				+ "  WHERE a.board_seq = ? "
				+ "  AND a.board_type_seq = ? "
				+ "  GROUP BY a.comment_seq "
				+ "  ORDER BY IFNULL(parent_seq, 999999999), a.reg_dtm, a.comment_seq;";
		
		Object[] args = { boardSeq, boardTypeSeq };
		
		return query(sql, new BoardCommentMapper(), args);
	}
	
	/* 게시글 댓글 추가 */
	public int addComment(BoardCommentDto boardCommentDto) {
		
		String sql = " INSERT INTO board_comment (lvl, "
											  + " content, "
											  + " board_seq, "
											  + " board_type_seq, "
											  + " member_seq, "
											  + " parent_comment_seq, "
											  + " reg_dtm) "
				+ " VALUES(?, ?, ?, ?, ?, ?, DATE_FORMAT(NOW()  ,'%Y%m%d%H%i%s'))";
		
		Integer lvl = boardCommentDto.getLvl() == null ? 0 : boardCommentDto.getLvl();
		String content = boardCommentDto.getContent();
		int boardSeq = boardCommentDto.getBoardSeq();
		int boardTypeSeq = boardCommentDto.getBoardTypeSeq();
		int memberSeq = boardCommentDto.getMemberSeq();
		Integer parentCommentSeq = boardCommentDto.getParentCommentSeq()==0? null : boardCommentDto.getParentCommentSeq();
		
		log.info("addComment :: lvl : "  + lvl + ", content : "  + content + ", boardSeq : "  + boardSeq + ", boardTypeSeq : "  + boardTypeSeq
				 + ", memberSeq : "  + memberSeq + ", parentCommentSeq : "  + parentCommentSeq);
		
		Object[] args = { lvl, content, boardSeq, boardTypeSeq, memberSeq, parentCommentSeq};
		
		return update(sql, args);
	}
	
	/* 게시글 댓글 업데이트 */
	public int modifyBoardComment(HashMap<String, String> params) {
		
	    String sql = " UPDATE board_comment "
	    		+ " SET content = ? ,"
                + "     update_dtm = DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') "
                + " WHERE comment_seq=? ";
    
	    String content = params.get("content");
	    int commentSeq = Integer.parseInt(params.get("commentSeq"));
	    
	    log.info("modifyBoardComment :: content :" + content + ", commentSeq : " + commentSeq);
    
	    Object[] args = {content,commentSeq};
	    
	    return update(sql, args);
	}		
	
	/*게시글 댓글 삭제*/
	public int deleteBoardComment(HashMap<String, String> params) {
		
	    String sql = " DELETE FROM board_comment "
                + " WHERE comment_seq=? ";
    
	    int commentSeq = Integer.parseInt(params.get("commentSeq"));
    
	    Object[] args = {commentSeq};
	    
	    return update(sql, args);
	}	
	
	/* Mapper */
	/*댓글 전체 가져오기*/
	class BoardCommentMapper implements ResultSetExtractor<List<BoardCommentDto>> {
		
		@Override
		public List<BoardCommentDto> extractData(ResultSet rs) throws SQLException, DataAccessException {

			// TODO Auto-generated method stub
			ArrayList<BoardCommentDto> list = new ArrayList<>();
			
			BoardCommentDto dto = null;
			
			while(rs.next()) {
				dto = new BoardCommentDto();
				dto.setCommentSeq(rs.getInt("comment_seq"));
				dto.setLvl(rs.getInt("lvl"));
				dto.setBoardSeq(rs.getInt("board_seq"));
				dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
				dto.setContent(rs.getString("content"));
				dto.setParentCommentSeq(rs.getInt("parent_seq"));
				dto.setRegDtm(rs.getString("reg_dtm"));
				dto.setMemberSeq(rs.getInt("member_seq"));
				dto.setUpdateDtm(rs.getString("update_dtm"));
				dto.setDeleteDtm(rs.getString("delete_dtm"));
				dto.setMemberNm(rs.getString("member_nm"));
				
				list.add(dto);
			}
			return list;
		}
	}

}
