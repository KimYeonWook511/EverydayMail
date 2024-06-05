/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import user.User;

/**
 *
 * @author ccoma
 */
public class SpamDAO {
    private Connection conn;
    private ResultSet rs;
    
    public SpamDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/mail";
            String dbID = "root";
            String dbPassword = "root";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public String getDate(){ // 현재 시각을 추출해주는 메소드
        String sql = "select now()";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getString(1);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getNext_spamNo() { // 스팸 순번을 구해주는 메소드
        String sql = "select max(spamNo) from spam";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1)+1;
            }
            return 0;
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public Spam getSpam(String userId, String blockId) {
        String sql = "select * from spam where userId = ? and blockId = ?";
                
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, blockId);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                Spam spam = new Spam();
                spam.setSpamNo(rs.getInt("spamNo"));
                spam.setUserId(rs.getString("userId"));
                spam.setBlockId(rs.getString("blockId"));
                spam.setBlockDate(rs.getString("blockDate"));
                
                return spam;
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Spam> getSpamList(String userId) {
        String sql = "select * from spam where userId = ?";
        ArrayList<Spam> list = new ArrayList<Spam>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                Spam spam = new Spam();
                spam.setSpamNo(rs.getInt("spamNo"));
                spam.setUserId(rs.getString("userId"));
                spam.setBlockId(rs.getString("blockId"));
                spam.setBlockDate(rs.getString("blockDate"));
                
                list.add(spam);
                
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int addSpam(String userId, String blockId) {
        String sql = "insert into spam values (?, ?, ?, ?)";
        
        if (getSpam(userId, blockId) != null) {
            return 0;
        }
        
        try {
           PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, getNext_spamNo());
           pstmt.setString(2, userId);
           pstmt.setString(3, blockId);
           pstmt.setString(4, getDate());
           pstmt.executeUpdate();
           return 1;
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int spamCancel(String userId, String blockId) {
        String sql = "delete from spam where userId = ? and blockId = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, blockId);
            pstmt.executeUpdate();
            return 1;
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    } 
}
