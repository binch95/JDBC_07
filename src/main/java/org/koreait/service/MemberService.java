package org.koreait.service;

import org.koreait.dao.MemberDao;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService() {
        this.memberDao = new MemberDao();
    }

    public boolean isLoginIdDup(Connection conn, String loginId) {
        return memberDao.isLoginIdDup(conn, loginId);
    }
    public boolean isLoginPwDup(Connection conn, String loginPw, String loginId) {
        return memberDao.isLoginPwDup(conn, loginPw,loginId);
    }
}
