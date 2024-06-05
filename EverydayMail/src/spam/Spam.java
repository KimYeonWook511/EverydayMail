/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam;

/**
 *
 * @author ccoma
 */
public class Spam {
    private int spamNo;
    private String userId;
    private String blockId;
    private String blockDate;
    
    public int getSpamNo() {
        return spamNo;
    }

    public void setSpamNo(int spamNo) {
        this.spamNo = spamNo;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }
    
    public String getBlockDate() {
        return blockDate;
    }

    public void setBlockDate(String blockDate) {
        this.blockDate = blockDate;
    }
}
