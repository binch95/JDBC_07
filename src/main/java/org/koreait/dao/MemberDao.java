package org.koreait.dao;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;

public class MemberDao {


    public boolean isLoginIdDup(Connection conn, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE userId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public boolean isLoginPwDup(Connection conn, String loginPw, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE userPw = ?", loginPw);
        sql.append(" and userId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

}
