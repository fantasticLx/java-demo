package org.zp.javaee.tools.ip;

import org.zp.javaee.tools.ip.entry.IPSeeker;

public class IpUtil {

	public static String getIpAddress(String ip) {
		try{
			return IPSeeker.getInstance().getAddress(ip);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "未知区域";
	}

}
