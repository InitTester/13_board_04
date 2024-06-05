package com.pf.www.forum.notice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pf.www.forum.notice.dto.BoardAttachDto;

@Repository
public class BoardAttachDao extends JdbcTemplate {
	
	private final static Logger log = LoggerFactory.getLogger(BoardAttachDao.class);

	@Autowired
	public void setDataSource(DataSource ds) {
		super.setDataSource(ds);
	}
	
	/* 게시글 파일 저장 */
	public int addBoardAttach(BoardAttachDto attchdto) {
		String sql = " INSERT INTO board_attach "
				+ " (board_seq, "
				+ "  board_type_seq, "
				+ "  org_file_nm, "
				+ "  save_path, "
				+ "  chng_file_nm, "
				+ "  file_size, "
				+ "  file_type, "
				+ "  access_uri, "
				+ "  reg_dtm) "
				+ " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, DATE_FORMAT(now(),'%Y%m%d%H%i%s')) ";
		
		int boardSeq = attchdto.getBoardSeq();
		int boardTypeSeq = attchdto.getBoardTypeSeq();
		String OrgFileNm = attchdto.getOrgFileNm();
		String SavePath = attchdto.getSavePath();
		String ChngFileNm = attchdto.getChngFileNm();
		long FileSize = attchdto.getFileSize();
		String FileType = attchdto.getFileType();
		String AccessUri = attchdto.getAccessUri();
		
		Object[] args = {boardSeq, boardTypeSeq, OrgFileNm, SavePath, ChngFileNm, FileSize, FileType, AccessUri};
		
		return update(sql, args);
	}
	
	public int getBoardAttachCnt(Integer boardSeq, Integer boardTypeSeq) {
		
		String sql = " SELECT COUNT(1) "
		   + " FROM board_attach "
		   + " WHERE board_seq = ? " 
		   + " AND board_type_seq = ? ";
		
		Object[] args = { boardSeq, boardTypeSeq };
		
		return queryForObject(sql, Integer.class, args);		
	}
	
	/* 게시글 파일 다운로드 가능 데이터 조회 */
	public BoardAttachDto getBoardAttach(Integer boardSeq, Integer boardTypeSeq) {
		
		String sql = " SELECT attach_seq, "
					      + " board_seq, "
					      + " board_type_seq, "
					      + " org_file_nm, "
					      + " save_path, "
					      + " chng_file_nm, "
					      + " file_size, "
					      + " file_type, "
					      + " access_uri, "
					      + " reg_dtm "
				   + " FROM board_attach "
				   + " WHERE board_seq = ? " 
				   + " AND board_type_seq = ? ";
		
		Object[] args = { boardSeq, boardTypeSeq };
		
		return queryForObject(sql, new BoardAttachRowMapper(), args);		
	}
	
	public BoardAttachDto getBoardAttach(Integer attachSeq) {
		
		String sql = " SELECT attach_seq, "
					      + " board_seq, "
					      + " board_type_seq, "
					      + " org_file_nm, "
					      + " save_path, "
					      + " chng_file_nm, "
					      + " file_size, "
					      + " file_type, "
					      + " access_uri, "
					      + " reg_dtm "
				   + " FROM board_attach "
				   + " WHERE attach_seq = ? ";
		
		Object[] args = { attachSeq };
		
		return queryForObject(sql, new BoardAttachRowMapper(), args);		
	}

	
	/* 게시글 파일 다운로드 가능 데이터 전체 조회 */
	public List<BoardAttachDto> getBoardAttachList(Integer boardSeq, Integer boardTypeSeq) {
		
		String sql = " SELECT attach_seq, "
					      + " board_seq, "
					      + " board_type_seq, "
					      + " org_file_nm, "
					      + " save_path, "
					      + " chng_file_nm, "
					      + " file_size, "
					      + " file_type, "
					      + " access_uri, "
					      + " reg_dtm "
				   + " FROM board_attach "
				   + " WHERE board_seq = ? "
				   + " AND board_type_seq = ? ";
		
		Object[] args = {boardSeq, boardTypeSeq};
		
		return query(sql, new BoardAttachListMapper(), args);
	}
	
	/* 게시글 파일 다운로드수 증가 */
	public int updateDownloadCnt(int attachSeq) {
		String sql = "UPDATE board_attach "
				+ " SET download_cnt = download_cnt + 1"
				+ " WHERE attach_seq = ?";
		
		Object[] args = {attachSeq};
		
		return update(sql, args);
	}
	
	/* 게시글 파일 전체 삭제 */
	public int deleteBoardAttach(Integer boardSeq, Integer boardTypeSeq) {
		
	    String sql = " DELETE FROM board_attach "
                + " WHERE board_seq=? "
                + " AND board_type_seq=? ";
    
	    Object[] args = {boardSeq, boardTypeSeq};
	    
	    return update(sql, args);
	}
	
	// 첨부파일 삭제
	public int deleteOneBoardAttach(Integer attachSeq) {
		
		String sql = " DELETE FROM "
				   + " board_attach "
				   + " WHERE attach_seq = ? ";
		
		Object[] args = { attachSeq };
		
		return update(sql, args);
	} 
	
	/* Mapper */	
	/* 하나의 파일 정보 로우 매퍼 */
	class BoardAttachRowMapper implements RowMapper<BoardAttachDto>{
		@Override
		public BoardAttachDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			BoardAttachDto dto = new BoardAttachDto();
			dto.setAttachSeq(rs.getInt("attach_seq"));
			dto.setBoardSeq(rs.getInt("board_seq"));
			dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
			dto.setOrgFileNm(rs.getString("org_file_nm"));
			dto.setSavePath(rs.getString("save_path"));
			dto.setChngFileNm(rs.getString("chng_file_nm"));
			dto.setFileSize(rs.getLong("file_size"));
			dto.setFileType(rs.getString("file_type"));
			dto.setAccessUri(rs.getString("access_uri"));
			dto.setRegDtm(rs.getString("reg_dtm"));
			
			return dto;
		}
	}
	
	class BoardAttachListMapper implements ResultSetExtractor<List<BoardAttachDto>>{		
		@Override
		public List<BoardAttachDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
			ArrayList<BoardAttachDto> list = new ArrayList<>();
			
			BoardAttachDto dto = null;
			while(rs.next()) {
				dto = new BoardAttachDto();
				dto.setAttachSeq(rs.getInt("attach_seq"));
				dto.setBoardSeq(rs.getInt("board_seq"));
				dto.setBoardTypeSeq(rs.getInt("board_type_seq"));
				dto.setOrgFileNm(rs.getString("org_file_nm"));
				dto.setSavePath(rs.getString("save_path"));
				dto.setChngFileNm(rs.getString("chng_file_nm"));
				dto.setFileSize(rs.getInt("file_size"));
				dto.setFileType(rs.getString("file_type"));
				dto.setAccessUri(rs.getString("access_uri"));
				dto.setRegDtm(rs.getString("reg_dtm"));
				
				list.add(dto);
			}
			return list;
		}
	}
}
