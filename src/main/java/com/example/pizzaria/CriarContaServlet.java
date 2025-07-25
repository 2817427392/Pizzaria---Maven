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

@WebServlet(name = "CriarContaServlet", value = "/CriarContaServlet")
public class CriarContaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String nomeInput;
        char c;
        String caracteresInvalidos = "";
        String emailInput;
        String senhaInput;
        String mensagem = "";
        String senhaHash;
        RequestDispatcher dispatcher = null;
        int linhasAfetadas;

//        Coletando os inputs do usuário:
        nomeInput=request.getParameter("nome");
        emailInput = request.getParameter("email");
        senhaInput = request.getParameter("senha");

//        Conectando ao database e verificando dados:
        try {
            conn = Conexao.conectar();
//            Verificando se o nome do usuário é válido
            if (nomeInput.matches("^[A-Za-zÀ-ÿ '-]+$")) {
                pstm = conn.prepareStatement("SELECT * FROM usuarios where email = '"+emailInput+"'");
                rs = pstm.executeQuery();
                if(rs.next()){
                    mensagem = "Email já cadastrado";
                    request.setAttribute("erroEmail",mensagem);
                    dispatcher = request.getRequestDispatcher("criarconta.jsp");
                    dispatcher.forward(request,response);
                }else{
                    if (senhaInput.length() < 6) {
                        request.setAttribute("erroSenha", "A senha deve ter pelo menos 6 caracteres.");
                        dispatcher = request.getRequestDispatcher("criarconta.jsp");
                        dispatcher.forward(request,response);
                    } else if (!senhaInput.matches(".*[A-Z].*")) {
                        request.setAttribute("erroSenha", "A senha deve conter pelo menos uma letra maiúscula.");
                        dispatcher = request.getRequestDispatcher("criarconta.jsp");
                        dispatcher.forward(request,response);
                    } else if (!senhaInput.matches(".*[a-z].*")) {
                        request.setAttribute("erroSenha", "A senha deve conter pelo menos uma letra minúscula.");
                        dispatcher = request.getRequestDispatcher("criarconta.jsp");
                        dispatcher.forward(request,response);
                    } else if (!senhaInput.matches(".*\\d.*")) {
                        request.setAttribute("erroSenha", "A senha deve conter pelo menos um número.");
                        dispatcher = request.getRequestDispatcher("criarconta.jsp");
                        dispatcher.forward(request,response);
                    } else if (!senhaInput.matches(".*[^a-zA-Z0-9].*")) {
                        request.setAttribute("erroSenha", "A senha deve conter pelo menos um caractere especial.");
                        dispatcher = request.getRequestDispatcher("criarconta.jsp");
                        dispatcher.forward(request,response);
                    } else {
                        senhaHash = BCrypt.hashpw(senhaInput,BCrypt.gensalt());
                        pstm = conn.prepareStatement("INSERT INTO usuarios (nome,email,senha) VALUES ('"+nomeInput+"','"+emailInput+"','"+senhaHash+"')");
                        linhasAfetadas = pstm.executeUpdate();

//                        Verificando se a inserção ocorreu
                        if(linhasAfetadas>0){
                            request.setAttribute("contaCriada","Conta criada com sucesso!!!");
                        }else{
                            request.setAttribute("contaErro","Erro ao enviar dados ao banco de dados. Tente de novo...");
                        }
                        dispatcher = request.getRequestDispatcher("criarconta.jsp");
                        dispatcher.forward(request,response);
                    }
                }
            } else {
                for (int i = 0; i < nomeInput.length(); i++) {
                    c = nomeInput.charAt(i);
                    // Permite letras (A-Z, a-z), letras acentuadas (À-ÿ), espaço, apóstrofo, hífen
                    if (!Character.toString(c).matches("[A-Za-zÀ-ÿ '\\-]")) {
                        caracteresInvalidos += c + " ";
                    }
                }

                if (!caracteresInvalidos.isEmpty()) {
                    mensagem = "O nome contém caracteres inválidos: " + caracteresInvalidos.trim();
                }
//                Mostrando erro para o usuário:
                request.setAttribute("erroNome", mensagem);
                dispatcher = request.getRequestDispatcher("criarconta.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
}