package com.pf.www.forum.notice.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pf.www.forum.notice.dto.BoardAttachDto;
import com.pf.www.forum.notice.dto.BoardDto;

@Repository
public class BoardDao extends JdbcTemplate {
	private final static Logger log = LoggerFactory.getLogger(BoardDao.class);

	@Autowired
	public void setDataSource(DataSource ds) {
		super.setDataSource(ds);
	}

	/* 게시글 총 갯수 */
	public int getTotalPage() {
		String sql = " SELECT COUNT(*) FROM board ";
		return queryForObject(sql, Integer.class);
	}

	/* 게시글 리스트 */
	public List<BoardDto> getList(HashMap<String, String> params, Integer page, Integer size) {
		
		String sql = " SELECT b.board_seq, " 
				+ " b.board_type_seq, " 
				+ " b.title, " 
				+ " b.content, " 
				+ " b.hit, "
				+ " b.del_yn, " 
				+ " b.reg_dtm, " 
				+ " b.reg_member_seq, " 
				+ " b.update_dtm, " 
				+ " b.update_member_seq, "
				+ " bt.board_type_nm, " 
				+ " m.member_id reg_member_id, " 
				+ " IFNULL(c.commentcnt, 0) AS commentcnt, "		
				+ " IFNULL(bf.filecnt, 0) AS filecnt "			
				+ " FROM board b "
				+ " JOIN board_type bt ON bt.board_type_seq = b.board_type_seq "
				+ " JOIN member m ON m.member_seq = b.reg_member_seq " 
				+ " LEFT JOIN ( SELECT board_seq, "
				+ "                    board_type_seq,"
				+ "                    COUNT(*) AS commentcnt "
				+ "              FROM board_comment "
				+ "              GROUP BY board_seq, board_type_seq ) c ON b.board_seq = c.board_seq "
				+ "                                                     AND b.board_type_seq = c.board_type_seq "
				+ " LEFT JOIN ( SELECT board_seq, "
				+ "                    board_type_seq,"
				+ "                    COUNT(*) AS filecnt "
				+ "              FROM board_attach "
				+ "              GROUP BY board_seq, board_type_seq ) bf ON b.board_seq = bf.board_seq "
				+ "                                                     AND b.board_type_seq = bf.board_type_seq "									
				+ " WHERE 1=1 " 
	            + " ORDER BY b.board_seq desc "
				+ " LIMIT ?, ?;";

		page = (page - 1) * size < 0 ? 0 : (page - 1) * size;

		log.info(" getList :: sql : " + sql);
		log.info(" getList :: page : " + page + ", size : " + size);

		Object[] args = { page, size };

		return query(sql, new BoardListMapper(), args);
	}

	/* 게시글 디테일 */
	public BoardDto getBoardDetail(Integer board_seq) {

		String sql = " SELECT b.board_seq, " 
				+ " b.board_type_seq, " 
				+ " b.title, " 
				+ " b.content, " 
				+ " b.hit, "
				+ " b.del_yn, " 
				+ " b.reg_dtm, " 
				+ " b.reg_member_seq, " 
				+ " b.update_dtm, " 
				+ " b.update_member_seq,"
				+ " bt.board_type_nm, " 
				+ " m.member_id reg_member_id, "
				+ " IFNULL(c.commentcnt, 0) AS commentcnt, "				
				+ " IFNULL(bf.filecnt, 0) AS filecnt "				
				+ " FROM board b "
				+ " JOIN board_type bt ON bt.board_type_seq = b.board_type_seq "
				+ " JOIN member m ON m.member_seq = b.reg_member_seq " 
				+ " LEFT JOIN ( SELECT board_seq, "
				+ "                    board_type_seq,"
				+ "                    COUNT(*) AS commentcnt "
				+ "              FROM board_comment "
				+ "              GROUP BY board_seq, board_type_seq ) c ON b.board_seq = c.board_seq "
				+ "                                                     AND b.board_type_seq = c.board_type_seq "
				+ " LEFT JOIN ( SELECT board_seq, "
				+ "                    board_type_seq,"
				+ "                    COUNT(*) AS filecnt "
				+ "              FROM board_attach "
				+ "              GROUP BY board_seq, board_type_seq ) bf ON b.board_seq = bf.board_seq "
				+ "                                                     AND b.board_type_seq = bf.board_type_seq "					
				+ " WHERE 1=1 " 
				+ " AND b.board_seq = ? ";

		log.info("board_seq 는 ? : " + board_seq);

		Object[] args = { board_seq };

		return queryForObject(sql, new BoardRowMapper(), args);
	}
	
	/* 게시글 작성 */
	public int addBoard(HashMap<String, String> params) {
		
		String sql = " INSERT INTO board (board_type_seq, "
									  + " title, "
									  + " content, "
									  + " hit, "
									  + " del_yn, "
									  + " reg_dtm, "
									  + " reg_member_seq) "
				+ " VALUES( ?, ?, ?, 0, 'N', DATE_FORMAT(now(),'%Y%m%d%H%i%s'), ?);";
		
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		String title = params.get("title");
		String content = params.get("trumbowyg-demo");
		int regMemberSeq = Integer.parseInt(params.get("memberSeq"));
		
		/* 저장된 pk 값을 불러오기 위한 인터페이스 */
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, boardTypeSeq);
			ps.setString(2, title);
			ps.setString(3, content);
			ps.setInt(4, regMemberSeq);
			return ps;			
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
		
	/* 게시글 수정 */
	public int updateBoard(HashMap<String, String> params) {

		System.out.println(params);
		
	    String sql = " UPDATE board "
	                + " SET title = ?, "
	                + "     content = ?, "
	                + "     update_dtm = DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') "
	                + " WHERE board_seq=? "
	                + " AND board_type_seq=? ";
	    
	    String title = params.get("title");
	    String content = params.get("trumbowyg-demo");
	    int boardSeq = Integer.parseInt(params.get("boardSeq"));
	    int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
	    log.info("updateBoard :: title : " + title + ", content : " + content+ ", boardSeq : " + boardSeq+ ", boardTypeSeq : " + boardTypeSeq);
	    
	    Object[] args = {title, content, boardSeq, boardTypeSeq};
	    
	    return update(sql, args);
	}
	
	/* 게시글 삭제 */
	public int deleteBoard(HashMap<String, String> params) {
		
	    String sql = " DELETE FROM board "
                + " WHERE board_seq=? "
                + " AND board_type_seq=? ";
    
	    int boardSeq = Integer.parseInt(params.get("boardSeq"));
	    int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
    
	    Object[] args = {boardSeq, boardTypeSeq};
	    
	    return update(sql, args);
	}

	/*  좋아요/싫어요 가져오기, Y면 좋아요, N이면 싫어요
        레코드가 없을 경우 오류가 발생한다. -> 예외 던지기 필요 */
	public String getEmptyVote(Integer boardSeq, Integer boardTypeSeq, Integer memberSeq) {

		String sql = " SELECT is_like "
				   + " FROM board_vote "
			       + " WHERE board_seq = ? "
			       + " AND board_type_seq = ? "
			       + " AND member_seq = ? ";
		
		  Object[] args = {boardSeq, boardTypeSeq, memberSeq};
		  return queryForObject(sql, String.class, args);
	}

	/* 좋아요 추가 */
	public int addVote(Integer boardSeq, Integer boardTypeSeq, Integer memberSeq, String isLike, String ip) {

		String sql = " INSERT INTO board_vote "
				   + " (board_seq, "
				   + "  board_type_seq, "
				   + "  member_seq, "
				   + "  is_like, "
				   + "  ip, "
				   + "  reg_dtm) "
				   + " VALUES(?, ?, ?, ?, ?, DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));";
		
		Object[] args = { boardSeq, boardTypeSeq, memberSeq, isLike, ip };
		
		return update(sql, args);
	}

	/* 좋아요 삭제 */
	public int deleteVote(Integer boardSeq, Integer boardTypeSeq, Integer memberSeq, String isLike) {

		String sql = " DELETE FROM board_vote "
				   + " WHERE board_seq=? "
				   + " AND board_type_seq=? "
				   + " AND member_seq=? "
				   + " AND is_like = ?;";
		
		Object[] args = { boardSeq, boardTypeSeq, memberSeq, isLike };
		
		return update(sql, args);
	}
	
	/* 좋아요/싫어요에 기존 값이 있으면 업데이트 처리 */
	public int updateVote(Integer boardSeq, Integer boardTypeSeq, Integer memberSeq, String isLike, String ip) {

		log.info("isLike : "+isLike + ", memberSeq : "+memberSeq + ", ip : "+ ip );
		
		String sql = " UPDATE board_vote "
				   + " SET is_like = ?, "
				   + "  ip = ? "
				   + " WHERE board_seq=? "
				   + " AND board_type_seq=? "
				   + " AND member_seq=? ;";
		
		Object[] args = { isLike, ip, boardSeq, boardTypeSeq, memberSeq };
		
		return update(sql, args);
	}
	
	/* Mapper */
	class BoardRowMapper implements RowMapper<BoardDto> {
		@Override
		public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub

			BoardDto dto = new BoardDto();
			dto.setBoardSeq(rs.getInt("board_seq"));
			dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
			dto.setBoardTypeNm(rs.getString("board_type_nm"));
			dto.setTitle(rs.getString("title"));
			dto.setContent(rs.getString("content"));
			dto.setHit(rs.getInt("hit"));
			dto.setDelYn(rs.getString("del_yn"));
			dto.setRegDtm(rs.getString("reg_dtm"));
			dto.setRegMemberSeq(rs.getInt("reg_member_seq"));
			dto.setRegMemberId(rs.getString("reg_member_id"));
			dto.setUpdateDtm(rs.getString("update_dtm"));
			dto.setUpdateMemberSeq(rs.getInt("update_member_seq"));
			dto.setCommentcnt(rs.getInt("commentCnt"));
			dto.setFilecnt(rs.getInt("filecnt"));
			
			return dto;
		}
	}

	class BoardListMapper implements ResultSetExtractor<List<BoardDto>> {
		@Override
		public List<BoardDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
			// TODO Auto-generated method stub

			ArrayList<BoardDto> list = new ArrayList<>();
			BoardDto dto = null;

			while (rs.next()) {

				dto = new BoardDto();
				dto.setBoardSeq(rs.getInt("board_seq"));
				dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
				dto.setBoardTypeNm(rs.getString("board_type_nm"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setHit(rs.getInt("hit"));
				dto.setDelYn(rs.getString("del_yn"));
				dto.setRegDtm(rs.getString("reg_dtm"));
				dto.setRegMemberSeq(rs.getInt("reg_member_seq"));
				dto.setRegMemberId(rs.getString("reg_member_id"));
				dto.setUpdateDtm(rs.getString("update_dtm"));
				dto.setUpdateMemberSeq(rs.getInt("update_member_seq"));
				dto.setCommentcnt(rs.getInt("commentCnt"));
				dto.setFilecnt(rs.getInt("filecnt"));
				
				list.add(dto);
			}
			return list;
		}
	}
}
