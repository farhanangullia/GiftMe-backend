/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */

@XmlType(name = "getTimeRsp", propOrder = {
		"currentTime"
	})
public class GetTimeRsp {
    private String currentTime;

    public GetTimeRsp() {
    }

    public GetTimeRsp(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
    
    
}
