package org.koreait;

import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;

import java.sql.*;
import java.util.Scanner;

public class App {
    String login = "";

    public void run() {
        System.out.println("==프로그램 시작==");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            Connection conn = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String url = "jdbc:mysql://localhost:3306/Article";

            try {
                conn = DriverManager.getConnection(url, "root", "");

                int actionResult = action(conn, sc, cmd);

                if (actionResult == -1) {
                    System.out.println("==프로그램 종료==");
                    sc.close();
                    break;
                }

            } catch (SQLException e) {
                System.out.println("에러 1 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int action(Connection conn, Scanner sc, String cmd) {

        if (cmd.equals("exit")) {
            return -1;
        }

        MemberController memberController = new MemberController(sc, conn);
        ArticleController articleController = new ArticleController(sc, conn);

        if (cmd.equals("login") && login.equals("")) {
            login = memberController.dologin();
        } else if (cmd.equals("login") && !login.equals("")) {
            System.out.println("로그인 중입니다.");
        }

        if (cmd.equals("logout") && !login.equals("")) {
            login = "";
            System.out.println("로그아웃 되었습니다.");
        } else if (cmd.equals("logout") && login.equals("")) {
            System.out.println("로그인을 안했어요");
        }

        if (cmd.equals("member sign")) {
            memberController.dosign();

        } else if (cmd.equals("article write")) {
            if (login.equals("")) {
                System.out.println("로그인 후에 이용 가능합니다.");
                return 0;
            }
            articleController.dowrite(login);

        } else if (cmd.equals("article list")) {
            articleController.dolist();

        } else if (cmd.startsWith("article modify")) {
            if (login.equals("")) {
                System.out.println("로그인 후에 이용 가능합니다.");
                return 0;
            }
            articleController.domodify(cmd, login);

        } else if (cmd.startsWith("article detail")) {
            articleController.dodetail(cmd);

        } else if (cmd.startsWith("article delete")) {
            if (login.equals("")) {
                System.out.println("로그인 후에 이용 가능합니다.");
                return 0;
            }
            articleController.dodelete(cmd,login);

        }
        return 0;
    }
}