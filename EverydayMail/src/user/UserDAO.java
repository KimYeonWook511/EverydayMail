package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uhs22
 */
public class UserDAO {
    private Connection conn;        // 자바와 데이터베이스를 연결
    // PrepareStatement를 전역변수처럼 선언할 경우 메소드를 중복실행 시 충돌발생, 따라서 사용 시 마다 생성
    private ResultSet rs;       // 결과값 받아오기
    
    public UserDAO() {
        try {
                String dbURL = "jdbc:mysql://localhost:3306/mail";
                String dbID = "root";
                String dbPassword = "root";
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        }catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public User getUser(String userId) {
        // 사용자 정보를 가져오는 메소드
        String sql = "select * from user where userId = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserName(rs.getString("userName"));
                user.setUserQuestion(rs.getInt("userQuestion"));
                user.setUserAnswer(rs.getString("userAnswer"));
                
                return user;
            }        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int login(String userId, String userPassword) {
	String sql = "select userPassword from user where userId = ?";
	try {
		PreparedStatement pstmt = conn.prepareStatement(sql); //sql쿼리문을 대기 시킨다
		pstmt.setString(1, userId); //첫번째 '?'에 매개변수로 받아온 'userID'를 대입
		rs = pstmt.executeQuery(); //쿼리를 실행한 결과를 rs에 저장
		if(rs.next()) {
                    if(rs.getString(1).equals(userPassword)) {
                        return 1; //로그인 성공
                    }else {
                        return 0; //비밀번호 틀림
                    }
		}
		return -1; //아이디 없음
	}catch (Exception e) {
		e.printStackTrace();
	}
	return -2; //오류
    }
    
    public int duplicateCheck(String userId){
        String sql = "select count(userId) from user where userId = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql); //sql쿼리문을 대기 시킨다
            pstmt.setString(1, userId); //첫번째 '?'에 매개변수로 받아온 'userID'를 대입
            rs = pstmt.executeQuery(); //쿼리를 실행한 결과를 rs에 저장
            
            if(rs.next()) {
		if(rs.getInt(1) == 0) {
                    return 1; // 회원가입 성공
		} else {
                    return 0; // 회원가입 실패
                }
            }
        } catch (Exception e) {
		e.printStackTrace();
	}
        return -1;
    }
    
    public int join(String userId, String userPassword, String userName, int userQuestion, String userAnswer){
        int dupCheck = this.duplicateCheck(userId); // 아이디 중복 확인
        
        if (dupCheck == 0) {
            // 이미 아이디 존재
            return 0;
        } else if (dupCheck == -1) {
            // 아이디 검사중 DB오류
            return -1;
        }
       
        String sql = "insert into user values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql); //sql쿼리문을 대기 시킨다
            pstmt.setString(1, userId); //첫번째 '?'에 매개변수로 받아온 'userID'를 대입
            pstmt.setString(2, userPassword);
            pstmt.setString(3, userName);
            pstmt.setInt(4, userQuestion);
            pstmt.setString(5, userAnswer);
            pstmt.executeUpdate();
            
            return 1;          
        } catch (Exception e) {
		e.printStackTrace();
	}
        return -1;
    }
    
    public String passwordFind(String userId, int userQuestion, String userAnswer) {
        String sql = "select * from user where userId = ? and userQuestion = ? and userAnswer = ?";
        
        try  {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, userQuestion);
            pstmt.setString(3, userAnswer);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("userPassword");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "DB Error";
        }      
        return null;
    }
    
    public String getName(String userId) {
        String sql = "select userName from user where userId = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("userName");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "DB Error";
        }
        return null;
    }
}
