package com.example.pizzaria;

import org.mindrot.jbcrypt.BCrypt;
import JDBC.Conexao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs =null;
        String emailInput;
        String senhaInput;
        String tipo;
        RequestDispatcher dispatcher = null;

//        Coletando os inputs do usuário:
        emailInput = request.getParameter("email");
        senhaInput = request.getParameter("senha");

//        Conectando ao database e coletando dados para análise e direcionamento da saída para o usuário:
        try {
            conn = Conexao.conectar();
            pstm = conn.prepareStatement("SELECT * FROM usuarios where email = '"+emailInput+"'");
            rs = pstm.executeQuery();

            if(rs.next()){
                if(BCrypt.checkpw(senhaInput, rs.getString("senha"))){
                    tipo = rs.getString("tipo");
                    if(tipo == "cliente") {
                        response.sendRedirect("PIcliente.html");
                    } else {
                        response.sendRedirect("PIAdmin.html");
                    }
                }else{
//                    Senha incorreta:
                    request.setAttribute("erroSenha","Senha incorreta");
                    dispatcher = request.getRequestDispatcher("login.jsp");
                    dispatcher.forward(request,response);
                }
            }else{
//                Email incorreto:
                request.setAttribute("erroEmail", "Email incorreto");
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
}
