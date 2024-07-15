package org.koreait.controller;

import org.koreait.service.MemberService;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    private Connection conn;
    private Scanner sc;
    private MemberService memberService;

    public MemberController(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
        this.memberService = new MemberService();
    }

    public void dosign() {
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;

        System.out.println("==회원가입==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoindIdDup = memberService.isLoginIdDup(conn,loginId);

            if (isLoindIdDup) {
                System.out.println(loginId + "는(은) 이미 사용중");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비번 똑바로 입력해");
                continue;
            }

            boolean loginPwCheck = true;

            while (true) {
                System.out.print("비밀번호 확인 : ");
                loginPwConfirm = sc.nextLine().trim();

                if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
                    System.out.println("비번 확인 똑바로 써");
                    continue;
                }
                if (loginPw.equals(loginPwConfirm) == false) {
                    System.out.println("일치하지 않아");
                    loginPwCheck = false;
                }
                break;
            }
            if (loginPwCheck) {
                break;
            }
        }

        while (true) {
            System.out.print("이름 : ");
            name = sc.nextLine();

            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("이름 똑바로 써");
                continue;
            }
            break;
        }


        SecSql sql = new SecSql();

        sql.append("INSERT INTO `member`");
        sql.append("SET regDate = NOW(),");
        sql.append("userId = ?,", loginId);
        sql.append("userPw= ?,", loginPw);
        sql.append("userName = ?;", name);

        int id = DBUtil.insert(conn, sql);

        System.out.println(id + "번 회원이 생성되었습니다");
    }


    public String dologin() {
        String loginId = null;
        String loginPw = null;
        int loginChance = 3;
        System.out.println("==로그인==");
        while (true) {

            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoindIdDup = memberService.isLoginIdDup(conn,loginId);

            if (!isLoindIdDup) {
                loginChance--;
                if(loginChance == 0){break;}
                System.out.println(loginId + "는(은) 없는 아이디 입니다.\n 로그인 횟수 " + loginChance + "번 남았습니다.");
                continue;
            }
            break;
        }
        while (true) {

            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                loginChance--;
                System.out.println("비밀번호 입력 횟수 " + loginChance + " 번남았습니다.");
                continue;
            }

            boolean loginPwCheck = memberService.isLoginPwDup(conn,loginPw,loginId);


            if (!loginPwCheck ) {
                loginChance--;
                if(loginChance == 0){break;}
                System.out.println("비밀번호가 틀렸습니다.\n비밀번호 입력 횟수 " + loginChance + " 번남았습니다.");
                continue;
            }
            System.out.println("로그인되었습니다.");
            return loginId;
        }
        System.out.println("아이디, 비밀번호 3회 틀렸습니다. 잠시 후에 다시 시도해주세요");
        return "";
    }

}
