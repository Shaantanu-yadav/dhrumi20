package com.gulbrandsen.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {

	private static final long serialVersionUID = 1L;
	
	private String otpKey, email, loginName ;
	private boolean otpVerified, pwdExpired, pwdReset;
	
	public boolean isOtpVerified() {
		return otpVerified;
	}

	public void setOtpVerified(boolean otpVerified) {
		this.otpVerified = otpVerified;
	}

	public String getOtpKey() {
		return otpKey;
	}

	public void setOtpKey(String otpKey) {
		this.otpKey = otpKey;
	}

	public boolean isPwdExpired() {
		return pwdExpired;
	}

	public void setPwdExpired(boolean pwdExpired) {
		this.pwdExpired = pwdExpired;
	}

	public boolean isPwdReset() {
		return pwdReset;
	}

	public void setPwdReset(boolean pwdReset) {
		this.pwdReset = pwdReset;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public static User getByEmail( Connection conn, String email ) throws Exception {
		User user = null;
		Exception ex = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "SELECT * "
				+ "FROM `gulbrandsen_users`.`user_mst`\r\n"
				+ "WHERE `login_name` = ?;";
			ps = conn.prepareStatement( sql );
			ps.setString( 1, email );
			rs = ps.executeQuery();
			if( rs.next() ) {
				user = new User();
				user.setEmail( email );
				user.setLoginName( rs.getString( "name" ) );
				//user.setOtpKey( rs.getString( "otp_key" ) );
				//user.setOtpVerified( true );
			} else {
				throw new Exception( "User not found with email: " + email );
			}
		} catch( Exception e ) { ex = e; }
		finally {
			if(conn!= null) {
				conn.close();
			}
		}
		if( ex != null ) { throw ex; }
		return user;
	}
	
	
}
