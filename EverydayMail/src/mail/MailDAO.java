package mail;

import user.User;
import user.UserDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author uhs22
 */
public class MailDAO {
    private Connection conn;
    private ResultSet rs;
    
    public MailDAO() {
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
    
    public int sendMail(String senderId, String receiverId, String title, String content){
        String sql = "insert into mail values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        if(does_it_exist_receiver(receiverId) == 0) {
            return 0;
        }
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getNext_mailNo());
            pstmt.setString(2, senderId);
            pstmt.setString(3, receiverId);
            pstmt.setString(4, title);
            pstmt.setString(5, content);
            pstmt.setString(6, getDate());
            pstmt.setInt(7, 0);
            pstmt.setInt(8, 0);
            pstmt.setInt(9, 0);
            pstmt.executeUpdate();
            return 1;
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int getNext_mailNo(){ // 메일 순번을 구해주는 메소드
        String sql = "select max(mailNo) from mail";
        
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
    
    public int does_it_exist_receiver(String recieverId){
        String sql = "select count(*) from user where userId = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recieverId);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public Mail getMail(int mailNo) {
        String sql = "select * from mail where mailNo = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mailNo);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Mail mail = new Mail();
                mail.setMailNo(rs.getInt("mailNo"));
                mail.setSenderId(rs.getString("senderId"));
                mail.setReceiverId(rs.getString("receiverId"));
                mail.setTitle(rs.getString("title"));
                mail.setContent(rs.getString("content"));
                mail.setSendDate(rs.getString("sendDate"));
                mail.setIsRead(rs.getInt("isRead"));
                mail.setIsImportant(rs.getInt("isImportant"));
                mail.setIsDelete(rs.getInt("isDelete"));
                
                return mail;
            }
            
        } catch (Exception e) {
            e.printStackTrace();     
        }
        return null;
    }
    
    public ArrayList<Mail> getReceiveMailList(String receiverId){
        String sql = "select * from mail where receiverId = ? and (isDelete = 0 or isDelete = 4) and senderId not in(select blockId from spam where userId = ?) order by sendDate desc";
        ArrayList<Mail> list = new ArrayList<Mail>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, receiverId);
            pstmt.setString(2, receiverId);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                Mail mail = new Mail();
                mail.setMailNo(rs.getInt(1));
                mail.setSenderId(rs.getString(2));
                mail.setReceiverId(rs.getString(3));
                mail.setTitle(rs.getString(4));
                mail.setContent(rs.getString(5));
                mail.setSendDate(rs.getString(6));
                mail.setIsRead(rs.getInt(7));
                mail.setIsImportant(rs.getInt(8));
                mail.setIsDelete(rs.getInt(9));
                list.add(mail);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<Mail> getSendMailList(String senderId){
        String sql = "select * from mail where senderId = ? and (isDelete = 0 or isDelete = 1 or isDelete = 3) order by sendDate desc";
        ArrayList<Mail> list = new ArrayList<Mail>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, senderId);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                Mail mail = new Mail();
                mail.setMailNo(rs.getInt(1));
                mail.setSenderId(rs.getString(2));
                mail.setReceiverId(rs.getString(3));
                mail.setTitle(rs.getString(4));
                mail.setContent(rs.getString(5));
                mail.setSendDate(rs.getString(6));
                mail.setIsRead(rs.getInt(7));
                mail.setIsImportant(rs.getInt(8));
                mail.setIsDelete(rs.getInt(9));
                list.add(mail);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<Mail> getTrashMailList(String receiverId) {
        String sql = "select * from mail where receiverId = ? and (isDelete = 1 or isDelete = 5) and senderId not in(select blockId from spam where userId = ?) order by sendDate desc";
        ArrayList<Mail> list = new ArrayList<Mail>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, receiverId);
            pstmt.setString(2, receiverId);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                Mail mail = new Mail();
                mail.setMailNo(rs.getInt(1));
                mail.setSenderId(rs.getString(2));
                mail.setReceiverId(rs.getString(3));
                mail.setTitle(rs.getString(4));
                mail.setContent(rs.getString(5));
                mail.setSendDate(rs.getString(6));
                mail.setIsRead(rs.getInt(7));
                mail.setIsImportant(rs.getInt(8));
                mail.setIsDelete(rs.getInt(9));
                list.add(mail);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int readMail(int mailNo) {
        String sql = "update mail set isRead = 1 where mailNo = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mailNo);
            pstmt.executeUpdate();
            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int nowDeleteBit(int mailNo) {
        String sql = "select isDelete from mail where mailNo = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mailNo);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("isDelete");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    
    public int deleteAction(int mailNo, int addDeleteBit) {
        String sql = "update mail set isDelete = ? where mailNo = ?";
        int isDelete = nowDeleteBit(mailNo) + addDeleteBit;
        
        if (isDelete == 7) {
            // 레코드에서 완전 삭제
            return deleteMail(mailNo);
            
        } else {
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, isDelete);
                pstmt.setInt(2, mailNo);
                pstmt.executeUpdate();
                return 1;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }      
    }
    
    public int deleteMail(int mailNo) {
        String sql = "delete from mail where mailNo = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mailNo);
            pstmt.executeUpdate();
            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int importantMail(int mailNo, boolean chbSelected) {
        String sql = "update mail set isImportant = ? where mailNo = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (chbSelected) {
                // 현재 체크박스가 체크되어 있을 시 (미등록 -> 등록)
                pstmt.setInt(1, 1); // 중요메일 등록
            } else {
                // 현재 체크박스가 체크되어 있지 않을 시(등록 -> 미등록)
                pstmt.setInt(1, 0); // 중요메일 등록
            }
            pstmt.setInt(2, mailNo);
            pstmt.executeUpdate();
            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int restoreMail(int mailNo) {
        String sql = "update mail set isDelete = ? where mailNo = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, nowDeleteBit(mailNo) - 1);
            pstmt.setInt(2, mailNo);
            pstmt.executeUpdate();
            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;   
    }
}
