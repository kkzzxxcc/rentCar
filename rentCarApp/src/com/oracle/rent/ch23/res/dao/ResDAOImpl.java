package com.oracle.rent.ch23.res.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.rent.ch23.common.base.AbstractBaseDAO;
import com.oracle.rent.ch23.res.vo.ResVO;

public class ResDAOImpl  extends AbstractBaseDAO implements ResDAO{
	@Override
	public List<ResVO> selectResInfo(ResVO resVO) throws SQLException, ClassNotFoundException{
		List<ResVO> resList = new ArrayList<ResVO>();
		String _resNumber = resVO.getResNumber();
		if(_resNumber != null && _resNumber.length() != 0) {
			pstmt = conn.prepareStatement("SELECT resNumber,"
										     + "resCarNumber,"
					                         + "TO_CHAR(resDate,'YYYY-MM-DD') resDate," 
										     + "TO_CHAR(useBeginDate, 'YYYY-MM-DD') useBeginDate," 
					                         +"TO_CHAR(returnDate, 'YYYY-MM-DD') returnDate,"
										     + "resUserId, "
											 + "price, "
											 + "status "
					                         +"FROM t_res  WHERE resNumber = ? ORDER BY resNumber");
			pstmt.setString(1, _resNumber);
			
		}else {
			pstmt = conn.prepareStatement("SELECT resNumber,"
				     + "resCarNumber,"
                     + "TO_CHAR(resDate,'YYYY-MM-DD') resDate," 
				     + "TO_CHAR(useBeginDate, 'YYYY-MM-DD') useBeginDate," 
                     + "TO_CHAR(returnDate, 'YYYY-MM-DD') returnDate,"
				     + "resUserId, "
					 + "price, "
					 + "status "
				     + "FROM t_res  ORDER BY resNumber");
		}
		
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String resNumber = rs.getString("resNumber");
			String resCarNumber = rs.getString("resCarNumber");
			String resDate = rs.getString("resDate");
			String useBeginDate = rs.getString("useBeginDate");
			String returnDate = rs.getString("returnDate");
			String resUserId = rs.getString("resUserId");
			double price = rs.getDouble("price");
			String status = rs.getString("status");

			
			ResVO _resVO = new ResVO();
			
			_resVO.setResNumber(resNumber);
			_resVO.setResCarNumber(resCarNumber);
			_resVO.setResDate(resDate);
			_resVO.setUseBeginDate(useBeginDate);
			_resVO.setReturnDate(returnDate);
			_resVO.setResUserId(resUserId);
			_resVO.setPrice(price);
			_resVO.setStatus(status);
			
			resList.add(_resVO);
		} // end while
		rs.close();
		return resList;

	}


	@Override
	public void insertResInfo(ResVO resVO) throws SQLException, ClassNotFoundException{
		pstmt = conn.prepareStatement("INSERT INTO t_res (resNumber, resCarNumber, resDate, useBeginDate, returnDate, resUserId, price, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		pstmt.setString(1, resVO.getResNumber());
		pstmt.setString(2, resVO.getResCarNumber());
		pstmt.setString(3, resVO.getResDate());
		pstmt.setString(4, resVO.getUseBeginDate());
		pstmt.setString(5, resVO.getReturnDate());
		pstmt.setString(6, resVO.getResUserId());
		pstmt.setDouble(7, resVO.getPrice());
		pstmt.setString(8, resVO.getStatus());

		
		pstmt.executeUpdate();	
		
	}


	@Override
	public void updateResInfo(ResVO resVO) throws SQLException, ClassNotFoundException{
		pstmt = conn.prepareStatement("UPDATE t_res SET  resCarNumber = ?, resDate = ?, useBeginDate = ?, returnDate = ?, resUserId = ?  , price = ?, status = ?"+
	                                  "WHERE resNumber = ? ");
		pstmt.setString(1, resVO.getResCarNumber());
		pstmt.setString(2, resVO.getResDate());
		pstmt.setString(3, resVO.getUseBeginDate());
		pstmt.setString(4, resVO.getReturnDate());
		pstmt.setString(5, resVO.getResUserId());
		pstmt.setDouble(7, resVO.getPrice());
		pstmt.setString(8, resVO.getStatus());
		pstmt.setString(6, resVO.getResNumber());
		
		pstmt.executeUpdate();		
	}


/*
	public void updateResInfo(ResVO resVO, double price, String status) throws SQLException, ClassNotFoundException {
		pstmt = conn.prepareStatement("UPDATE t_res SET resCarNumber = ?, resDate = ?, useBeginDate = ?, returnDate = ?, resUserId = ?, price = ?, status = ? WHERE resNumber = ?");
		pstmt.setString(1, resVO.getResCarNumber());
		pstmt.setString(2, resVO.getResDate());
		pstmt.setString(3, resVO.getUseBeginDate());
		pstmt.setString(4, resVO.getReturnDate());
		pstmt.setString(5, resVO.getResUserId());
		pstmt.setDouble(6, price);
		pstmt.setString(7, status);
		pstmt.setString(8, resVO.getResNumber());
		pstmt.executeUpdate();
	}

 */

	@Override
	public void deleteResInfo(ResVO resVO) throws SQLException, ClassNotFoundException{
		pstmt = conn.prepareStatement("DELETE t_res WHERE resNumber = ?");
		pstmt.setString(1, resVO.getResNumber());
		pstmt.executeUpdate();
	}

	@Override
	public double getTotalRevenue() throws SQLException, ClassNotFoundException {
		double totalRevenue = 0.0;
		pstmt = conn.prepareStatement("SELECT SUM(price) AS totalRevenue FROM t_res");
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			totalRevenue = rs.getDouble("totalRevenue");
		}
		rs.close();
		return totalRevenue;
	}

	@Override
	public double getPeriodRevenue(String period) throws SQLException, ClassNotFoundException {
		double periodRevenue = 0.0;
		// 입력받은 기간을 이용하여 매출을 조회하는 SQL 쿼리를 작성합니다.
		pstmt = conn.prepareStatement("SELECT SUM(price) AS periodRevenue FROM t_res WHERE resDate BETWEEN ? AND ?");
		// 입력받은 기간을 SQL 쿼리에 설정합니다.
		String[] dates = period.split("~");
		pstmt.setString(1, dates[0].trim()); // 시작일
		pstmt.setString(2, dates[1].trim()); // 종료일
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			periodRevenue = rs.getDouble("periodRevenue");
		}
		rs.close();
		return periodRevenue;
	}




}
