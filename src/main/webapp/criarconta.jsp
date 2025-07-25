<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="https://images.icon-icons.com/1954/PNG/512/pizzafood_122700.png">
        <title>Criar conta - Parma & Provola</title>
        <link rel="stylesheet" href="criarconta.css">
    </head>

    <body>
        <header>
            <div class="titulo">
                <img src="https://drive.google.com/thumbnail?id=15g6bc0VP88FjdOm-P5Gp0hFVA4f2qNyO"
                    alt="Logo Parma & Provola">
                <h1>Parma & Provola</h1>
            </div>
            <nav>
                <ul>
                    <li><a href="index.html">Home</a></li>
                    <li><a href="sobrenos.html">Sobre nós</a></li>
                    <li><a href="cardapio.html">Cardápio</a></li>
                    <li><a href="cardapio.html">Avaliações</a></li>
                    <li><a href="criarconta.jsp" class="ativa">Criar conta</a></li>
                    <li><a href="login.jsp">Entrar</a></li>
                </ul>
            </nav>
        </header>
        <main>
            <article>
                <h2>Criar conta:</h2>
                <form action="CriarContaServlet" method="post">
                    <div>
                        <label for="nome">Nome: </label>
                        <input type="text" id="nome" name="nome" placeholder="Digite seu nome aqui" required>
                        <div>
                            <% if (request.getAttribute("erroNome") !=null) { %>
                            <p class="erro">
                                <%= request.getAttribute("erroNome") %>
                            </p>
                            <% } %>
                        </div>
                    </div>

                    <div>
                        <label for="email">Email: </label>
                        <input type="email" id="email" name="email" placeholder="Digite seu email aqui" required>
                        <div>
                            <% if (request.getAttribute("erroEmail") !=null) { %>
                            <p class="erro">
                                <%= request.getAttribute("erroEmail") %>
                            </p>
                            <% } %>
                        </div>
                    </div>

                    <div>
                        <label for="senha">Senha: </label>
                        <input type="password" id="senha" name="senha" placeholder="Digite sua senha aqui" required>
                        <div>
                            <% if (request.getAttribute("erroSenha") !=null) { %>
                            <p class="erro">
                                <%= request.getAttribute("erroSenha") %>
                            </p>
                            <% } %>
                        </div>
                    </div>
                    <input type="submit" value="Criar Conta">
                    <div class="espaco-criou-conta">
                        <% if (request.getAttribute("contaCriada") !=null) { %>
                        <p class="criou">
                            <%= request.getAttribute("contaCriada") %>
                        </p>
                        <% } %>
                        <% if (request.getAttribute("contaErro") !=null) { %>
                        <p class="naoCriou">
                            <%= request.getAttribute("contaErro") %>
                        </p>
                        <% } %>
                    </div>
                </form>
            </article>
        </main>
    </body>

    </html>