<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>



<%@ page
	import="java.util.List,br.com.tcc.projetorua.model.Beneficiario,br.com.tcc.projetorua.model.Avatar"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page
	import="br.com.tcc.projetorua.model.Categoria,br.com.tcc.projetorua.model.Prestador,br.com.tcc.projetorua.security.FormatUtf8, 
	br.com.tcc.projetorua.model.Recorrencia, br.com.tcc.projetorua.model.Servico, br.com.tcc.projetorua.model.Dificuldade"%>






<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="Nicolas Marchel de Oliveira">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" type="text/css" href="css/estilo.css">


<title>Projeto Rua</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@docsearch/css@3">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">

<!-- Adicione Icone de Seta -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	integrity="sha512-..................." crossorigin="anonymous"
	referrerpolicy="no-referrer" />

<!-- Favicons -->
<link rel="apple-touch-icon"
	href="/docs/5.3/assets/img/favicons/apple-touch-icon.png"
	sizes="180x180">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon-32x32.png"
	sizes="32x32" type="image/png">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon-16x16.png"
	sizes="16x16" type="image/png">
<link rel="manifest" href="/docs/5.3/assets/img/favicons/manifest.json">
<link rel="mask-icon"
	href="/docs/5.3/assets/img/favicons/safari-pinned-tab.svg"
	color="#712cf9">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon.ico">
<meta name="theme-color" content="#712cf9">
<link
	href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&display=swap"
	rel="stylesheet">

<!-- Imports Tooltipster  REMOVER-->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script
	src="https://unpkg.com/tooltipster/dist/js/tooltipster.bundle.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="https://unpkg.com/tooltipster/dist/css/tooltipster.bundle.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://unpkg.com/tooltipster/dist/css/plugins/tooltipster/sideTip/themes/tooltipster-sideTip-shadow.min.css" />


<!-- Imports Tooltipster  REMOVER-->

<script>
	function limpa_formulário_cep() {
		//Limpa valores do formulário de cep.
		document.getElementById('rua').value = ("");
		document.getElementById('bairro').value = ("");
		document.getElementById('cidade').value = ("");
		document.getElementById('uf').value = ("");
		document.getElementById('ibge').value = ("");
	}

	function meu_callback(conteudo) {
		if (!("erro" in conteudo)) {
			//Atualiza os campos com os valores.
			document.getElementById('rua').value = (conteudo.logradouro);
			document.getElementById('bairro').value = (conteudo.bairro);
			document.getElementById('cidade').value = (conteudo.localidade);
			document.getElementById('uf').value = (conteudo.uf);
			document.getElementById('ibge').value = (conteudo.ibge);
		} //end if.
		else {
			//CEP não Encontrado.
			limpa_formulário_cep();
			alert("CEP não encontrado.");
		}
	}

	function pesquisacep(valor) {

		//Nova variável "cep" somente com dígitos.
		var cep = valor.replace(/\D/g, '');

		//Verifica se campo cep possui valor informado.
		if (cep != "") {

			//Expressão regular para validar o CEP.
			var validacep = /^[0-9]{8}$/;

			//Valida o formato do CEP.
			if (validacep.test(cep)) {

				//Preenche os campos com "..." enquanto consulta webservice.
				document.getElementById('rua').value = "...";
				document.getElementById('bairro').value = "...";
				document.getElementById('cidade').value = "...";
				document.getElementById('uf').value = "...";
				document.getElementById('ibge').value = "...";

				//Cria um elemento javascript.
				var script = document.createElement('script');

				//Sincroniza com o callback.
				script.src = 'https://viacep.com.br/ws/' + cep
						+ '/json/?callback=meu_callback';

				//Insere script no documento e carrega o conteúdo.
				document.body.appendChild(script);

			} //end if.
			else {
				//cep é inválido.
				limpa_formulário_cep();
				alert("Formato de CEP inválido.");
			}
		} //end if.
		else {
			//cep sem valor, limpa formulário.
			limpa_formulário_cep();
		}
	};
</script>







</head>


<body class="">

	<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
        <symbol id="bootstrap" viewBox="0 0 118 94">
            <title>Bootstrap</title>
            <path fill-rule="evenodd" clip-rule="evenodd"
			d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z">
            </path>
        </symbol>

    </svg>




	<div id="header">
		<div class="container-fluid p-0" id="cabecalho">
			<header
				class=" px-4 d-flex flex-wrap align-items-center mx-auto justify-content-center justify-content-between justify-content-md-between border-bottom">

				<div class="col-md-2 col-sm-1  mb-2 mb-md-0">
					<a href="/ProjetoRua/controller?call=Home "
						class="mt-1 d-inline-flex link-body-emphasis text-decoration-none">

						<img src="img/projetoRua.png" alt="Logo ProjetoRua"
						style="width: 65px; height: auto;">


					</a>
				</div>




				<ul
					class="nav col-12 col-md-8 col-sm-11  mb-2 justify-content-center mb-md-0 ">
					<li><a href="/ProjetoRua/controller?call=Home "
						class="nav-link px-1 custom-link mx-1 d-none d-md-block">Home
					</a></li>
					<li><a href="/ProjetoRua/controller?call=ProcurarServicosForm"
						class="nav-link px-1 custom-link mx-1">Preciso de Ajuda </a></li>


					<c:if test="${ empty usuarioLogado}">

						<li><a
							href="/ProjetoRua/controller?call=CadastrarPrestadorAjudaForm"
							class="nav-link px-1 custom-link mx-1">Oferecer Ajuda </a></li>

					</c:if>

					<c:if test="${ not empty usuarioLogado}">




						<li>

							<div class=" dropdown">

								<a class="nav-link custom-link dropdown-toggle" href="#"
									role="button" data-bs-toggle="dropdown" aria-expanded="false">Oferecer
									Ajuda </a>
								<ul class="dropdown-menu">
									<li><a class="dropdown-item"
										href="/ProjetoRua/controller?call=ListarServicoForm">Listar
											Serviços</a></li>
									<li><a class="dropdown-item"
										href="/ProjetoRua/controller?call=ListarHistoriasForm">Listar
											Histórias</a></li>

								</ul>
							</div>
						</li>

					</c:if>




					<c:if test="${ empty usuarioLogado}">

						<li><a
							href="/ProjetoRua/controller?call=CadastrarBeneficiarioForm"
							class="nav-link px-1 custom-link mx-1">Conte Sua História </a></li>

					</c:if>

					<c:if test="${ not empty usuarioLogado}">

						<li><a href="/ProjetoRua/controller?call=ListarHistoriaForm"
							class="nav-link px-1 custom-link mx-1">Conte Sua História </a></li>


					</c:if>




					<li><a href="#"
						class="nav-link px-1 custom-link mx-1 d-none d-md-block">Sobre</a></li>

					<li>
						<!-- 						<ul class="nav-link px-2 custom-link mx-1 list-unstyled"> -->
						<!-- 							<li>Novo Item</li> --> <!-- 						</ul>  --> <c:if
							test="${not empty usuarioLogado}">
							<a href="#"
								class="d-block link-dark text-decoration-none dropdown-toggle"
								id="dropdownUser1" data-bs-toggle="dropdown"
								aria-expanded="false"> <span id="userInitial"
								class="rounded-circle "></span>
							</a>


							<ul class="dropdown-menu text-small "
								aria-labelledby="dropdownUser1">



								<c:if test="${ usuarioLogado != null}">



									<c:choose>
										<c:when
											test="${usuarioLogado.getClass().getName()  eq 'br.com.tcc.projetorua.model.Beneficiario'}">

											<li><a class="dropdown-item"
												href="/ProjetoRua/controller?call=ListarHistoriaForm">
													Minha Historia</a></li>

										</c:when>
										<c:otherwise>
											<li><a class="dropdown-item"
												href="/ProjetoRua/controller?call=ListarServicoForm">Meus
													Serviços </a></li>

										</c:otherwise>
									</c:choose>




								</c:if>




								<c:if test="${not empty usuarioLogado}">
									<li><hr class="dropdown-divider"></li>
									<li><a class="dropdown-item"
										href="/ProjetoRua/controller?call=Logout">Logout</a></li>
								</c:if>


							</ul>
						</c:if> <c:if test="${ empty usuarioLogado}">
							<button type="button" id="openModalButton"
								class="btn btn-login btn-outline-light me-2"
								data-bs-toggle="modal" data-bs-target="#loginModal">Login</button>
						</c:if> <c:if test="${ not empty usuarioLogado}">
							<button type="button" style="display: none;" id="openModalButton"
								data-bs-toggle="modal" data-bs-target="#loginModal">Login</button>
						</c:if>




					</li>

				</ul>

				<div class="col-md-2 "></div>


			</header>
		</div>
	</div>



	<main id="principal">



		<div class="container">
			<div class="d-flex justify-content-center ">
				<form class="my-2 row g-3" accept-charset="UTF-8"
					action="/ProjetoRua/controller" method="post">

					<div class="col-md-2"></div>
					<div class="col-md-8">
						<label for="validationDefault01" class="form-label">Relate
							sua História </label>

						<textarea class="form-control" name="descricao"
							id="validationDefault01"
							placeholder="Tente contar o processo pelo qual chegou a condição de rua..."
							rows="3" maxlength="1200" required></textarea>
					</div>

					<div class="col-md-2 d-none d-md-block"></div>





					<div class="col-md-2 d-none d-md-block"></div>

					<div class="col-md-2 col-sm-6">

						<label for="validationSexo" class="form-label">Gênero </label> <select
							class="form-control" id="validationSexo" name="genero">
							<option value="---">---</option>
							<option value="Feminino">Feminino</option>
							<option value="Masculino">Masculino</option>
							<option value="Intersexo">Intersexo</option>

						</select>

					</div>
					<div class="col-md-2 col-sm-6">

						<label for="validationRaca" class="form-label">Raça/Cor</label> <select
							class="form-control" id="validationRaca" name="raca">
							<option value="---">---</option>
							<option value="Branca">Branca</option>
							<option value="Preta">Preta</option>
							<option value="Indigena">Indigena</option>
							<option value="Parda">Parda</option>
							<option value="Amarela">Amarela</option>

						</select>

					</div>

					<div class="col-md-2 col-sm-6">
						<label for="validationDataNascimento" class="form-label ">Data
							de Nascimento</label> <input class="form-control" type="date"
							id="validationDataNascimento" name="dataNascimento" required>
					</div>

					<div class="col-md-2 col-sm-6">
						<label for="validationAnos" class="form-label">Tempo nas
							Ruas</label> <input class="form-control" name="tempoNasRuas"
							type="number" id="validationAnos" value=""
							placeholder="Valor em Anos" required />
					</div>
					<div class="col-md-2"></div>





					<div class="col-md-2"></div>


					<div class="col-md-3">
						
					<button type="button" class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#avatarModal">
    Escolha seu Avatar &nbsp;&nbsp;<i class="fas fa-arrow-right"></i>
</button>


					</div>

					<div class="col-md-3 d-none d-md-block"></div>

					<div class="col-md-4 d-none d-md-block"></div>

					<div class="col-md-2 d-none d-md-block"></div>


					<div class="form-check col-md-8">

						<label class="form-check-label form-label">Quais
							vulnerabilidades você possui? </label>

					</div>

					<div class="col-md-2 d-none d-md-block"></div>

					<div class="col-md-2 d-none d-md-block"></div>

					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label"
							for="flexCheckalimentacao">Alimentação </label> <input
							class="form-check-input " type="checkbox" value=""
							id="flexCheckalimentacao" onchange="toggleLikert('alimentacao')">


					</div>
					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label"
							for="flexCheckHidratacao">Hidratação </label> <input
							class="form-check-input " type="checkbox" value=""
							id="flexCheckhidratacao" onchange="toggleLikert('hidratacao')">


					</div>
					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label" for="flexCheckHigiene">Higiene
						</label> <input class="form-check-input " type="checkbox" value=""
							id="flexCheckhigiene" onchange="toggleLikert('higiene')">


					</div>
					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label"
							for="flexCheckPreconceito">Preconceito </label> <input
							class="form-check-input " type="checkbox" value=""
							id="flexCheckpreconceito" onchange="toggleLikert('preconceito')">


					</div>

					<div class="col-md-2 d-none d-md-block"></div>

					<div class="col-md-2 d-none d-md-block"></div>

					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label"
							for="flexCheckViolencia">Violência física</label> <input
							class="form-check-input " type="checkbox" value=""
							id="flexCheckviolencia" onchange="toggleLikert('violencia')">


					</div>
					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label" for="flexCheckSaude">Saúde
						</label> <input class="form-check-input " type="checkbox" value=""
							id="flexChecksaude" onchange="toggleLikert('saude')">


					</div>
					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label" for="flexCheckAbrigo">Abrigo
						</label> <input class="form-check-input " type="checkbox" value=""
							id="flexCheckabrigo" onchange="toggleLikert('abrigo')">


					</div>
					<div class="form-check col-md-2 col-sm-6">

						<label class="form-check-label form-label" for="flexCheckTrabalho">Trabalho
						</label> <input class="form-check-input " type="checkbox" value=""
							id="flexChecktrabalho" onchange="toggleLikert('trabalho')">


					</div>

					<div class="col-md-2 d-none d-md-block"></div>


					<div id="campo-likert-alimentacao" class="col-md-2"></div>


					<div id="campo-likert-alimentacao" class="likert-slider col-md-8">


						<label for="validationLikertAlimentacao" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Alimentação: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertAlimentacao" name="Alimentacao">

						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>


					</div>
					<div id="campo-likert-alimentacao" class="col-md-2"></div>


					<div id="campo-likert-hidratacao" class="col-md-2"></div>


					<div id="campo-likert-hidratacao" class="likert-slider col-md-8">
						<label for="validationLikertHidratacao" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Hidratação: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertHidratacao" name="Hidratacao">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-hidratacao" class="col-md-2"></div>


					<div id="campo-likert-higiene" class="col-md-2"></div>


					<div id="campo-likert-higiene" class="likert-slider col-md-8">
						<label for="validationLikertHigiene" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Higiene: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertHigiene" name="Higiene">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-higiene" class="col-md-2"></div>

					<div id="campo-likert-preconceito" class="col-md-2"></div>


					<div id="campo-likert-preconceito" class="likert-slider col-md-8">
						<label for="validationLikertPreconceito" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Preconceito: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertPreconceito" name="Preconceito">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-preconceito" class="col-md-2"></div>
					<div id="campo-likert-violencia" class="col-md-2"></div>


					<div id="campo-likert-violencia" class="likert-slider col-md-8">
						<label for="validationLikertViolencia" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Violência física: </label> <input
							type="range" min="0" max="7" value="0" class="slider"
							id="validationLikertViolencia" name="Violencia">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-violencia" class="col-md-2"></div>
					<div id="campo-likert-saude" class="col-md-2"></div>


					<div id="campo-likert-saude" class="likert-slider col-md-8">
						<label for="validationLikertSaude" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Saúde: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertSaude" name="Saude">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-saude" class="col-md-2"></div>


					<div id="campo-likert-abrigo" class="col-md-2"></div>


					<div id="campo-likert-abrigo" class="likert-slider col-md-8">
						<label for="validationLikertAbrigo" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Abrigo: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertAbrigo" name="Abrigo">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-abrigo" class="col-md-2"></div>

					<div id="campo-likert-trabalho" class="col-md-2"></div>


					<div id="campo-likert-trabalho" class="likert-slider col-md-8">
						<label for="validationLikertTrabalho" class="form-label"
							title="Em uma escala de 1 a 7, mensure sua vulnerabilidade."
							data-bs-trigger="hover" data-bs-toggle="tooltip"
							data-bs-placement="top">Trabalho: </label> <input type="range"
							min="0" max="7" value="0" class="slider"
							id="validationLikertTrabalho" name="Trabalho">



						<div class="likert-labels">
							<span>0</span> <span>1</span> <span>2</span> <span>3</span> <span>4</span>
							<span>5</span> <span>6</span> <span>7</span>
						</div>
					</div>
					<div id="campo-likert-trabalho" class="col-md-2"></div>

					<div class="col-md-2 d-none d-md-block"></div>

					<div class="form-check col-md-8">
						<br> <label class="form-check-label form-label"
							for="flexCheckDefault">Fica em uma região especifica? </label> <input
							class="form-check-input " type="checkbox" value=""
							id="flexCheckDefault">

					</div>

					<div class="col-md-2 d-none d-md-block"></div>



					<div class="col-md-2 campos-adicionais"></div>


					<div class="col-md-2 col-sm-6 campos-adicionais">
						<label for="cep" class="form-label">Cep</label> <input
							class="form-control" name="cep" type="text" id="cep" value=""
							size="10" maxlength="9" onblur="pesquisacep(this.value);" />
					</div>

					<div class="col-md-2 col-sm-6 campos-adicionais">
						<label for="uf" class="form-label">Estado</label> <input
							type="text" class="form-control" id="uf" name="uf" readonly>
					</div>
					<div class="col-md-2 col-sm-6 campos-adicionais">
						<label for="cidade" class="form-label">Cidade</label> <input
							type="text" class="form-control" id="cidade" name="cidade"
							readonly>
					</div>
					<div class="col-md-2 col-sm-6 campos-adicionais">
						<label for="bairro" class="form-label">Bairro</label> <input
							type="text" class="form-control" id="bairro" name="bairro"
							value="">
					</div>


					<input type="hidden" class="form-control" id="ibge" name="ibge"
						readonly> <input type="hidden" class="form-control"
						id="rua" name="rua">

					<div class="col-md-2 campos-adicionais"></div>
















					<div class="col-md-2"></div>

					<div class="col-md-8">

						<input type="hidden" name="call" value="CadastrarHistoria">
						
					
						
						<input type="hidden" name="idAvatar" id="idAvatar" value="">
						
						<a href="/ProjetoRua/controller?call=ListarHistoriaForm"
							class="btn btn-outline-light col-md-3 col-sm-5 mx-0 px-0">Cancelar</a>
						<div class="btn  col-md-1"></div>


						<button
							class="btn btn-outline-light col-md-3 col-sm-5  mr-2 px-0 float-end"
							type="submit">Salvar</button>

					</div>






				</form>




			</div>
		</div>



		<br> <br> <br> <br> <br> <br> <br>
		<br> <br> <br>




	</main>


	<!-- Modal de login -->
	<div class="modal fade" id="loginModal" tabindex="-1"
		aria-labelledby="loginModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered">
			<div class="modal-content">
				<!-- Conteúdo do modal de login -->
				<div class="modal-header">
					<h5 class="modal-title " id="loginModalLabel">Conecte-se</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<section class="vh-80 modal_background ">
						<div class="container py-4 h-100">
							<div
								class="row d-flex justify-content-center align-items-center h-100">
								<div class="col-12 col-md-8 col-lg-6 col-xl-5">
									<form action="/ProjetoRua/controller" method="post">

										<div id="modal_login" class="card  text-white"
											style="border-radius: 1rem;">
											<div class="card-body p-5 text-center">

												<div class="mb-md-5 mt-md-4 pb-3">

													<h2 class="fw-bold mb-2 text-uppercase">Conecte-se</h2>
													<p class="text-white-50 mb-3">Por favor insira seu
														login e senha!</p>

													<div class="form-outline form-white mb-4">
														<label class="form-label" for="validationLogin">Cpf
															ou Cnpj</label> <input type="text" name="login"
															class="form-control form-control-lg" id="validationLogin"
															required />

													</div>

													<div class="form-outline form-white mb-2">
														<label class="form-label" for="validationPassword">Password</label>
														<input type="password" name="senha"
															class="form-control form-control-lg"
															id="validationPassword" required />

													</div>

													<p class="small mb-2 pb-lg-2">
														<a class="text-white-50" href="#!">Esqueceu sua senha?</a>
													</p>

													<button class="btn btn-outline-light btn-lg px-6"
														type="submit">Login</button>



												</div>

												<div>
													<p class="mb-0">
														Não tem uma conta? <a
															href="/ProjetoRua/controller?call=CadastrarBeneficiarioForm"
															class="text-white-50 fw-bold">Cadastre-se</a>
													</p>
												</div>

											</div>
										</div>
										<input type="hidden" name="call" value="Login">
									</form>


								</div>
							</div>
						</div>
					</section>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<!--<button type="button" class="btn btn-primary">Login</button>-->
				</div>
			</div>
		</div>
	</div>

	<!-- Modal avatares -->
	<div class="modal fade" id="avatarModal" tabindex="-1"
		aria-labelledby="avatarModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4
						class="modal-title text-center w-100"
						id="avatarModalLabel" style="">Escolha um Avatar</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<c:forEach items="${avatares}" var="avatar" varStatus="loop">
							<div class="col-md-3 col-sm-6">
								<img
									class="avatar-hover my-3 card-img-top rounded-circle mx-auto d-flex align-items-center overflow-hidden justify-content-center"
									style="width: 168px; height: 126px; border: 5px solid #343a40; transition: border-color 0.3s;"
									src="data:image/${avatar.getExtensao()};base64,${avatar.getFoto()}"
									data-bs-dismiss="modal"
									onclick="selecionarAvatar(${avatar.getId()})"  	
									onmouseover="this.style.borderColor='#A9A9A9'; this.style.borderWidth='8px'"
									onmouseout="this.style.borderColor='#343a40'; this.style.borderWidth='5px'"
									id="avatar-${avatar.getId()}">
							</div>

						</c:forEach>
					</div>


				</div>



			</div>
		</div>
	</div>
	<!-- Modal avatares  -->





	<div id="cabec-rodape">
		<div class="container">

			<footer id="rodape">
				<div
					class=" row row-cols-1 row-cols-sm-4 row-cols-md-5 pt-2 pb-0 mt-1 mb-0 border-top border-bottom">


					<div class="col mb-3 col-sm-4">
						<a href="/"
							class="d-flex align-items-center mb-3 link-body-emphasis text-decoration-none">
							<img src="img/projetoRua_rodape.png" alt="Logo ProjetoRua" 
							 style="width: 160px;  height: auto;">
						</a>


						<!-- 						<p class="">&copy; 2023</p> -->
					</div>

					<div class="col mb-3 d-none d-md-block"></div>

					<div class="col mb-3 text-center text-sm-start col-sm-4">
						<h5 class="fw-bold">Links Rápidos</h5>
						<ul class="nav flex-column">
							<li class="nav-item mb-2"><a
								class="fw-normal nav-link p-0  custom-link-rodape"
								href="/ProjetoRua/controller?call=Home">Home</a></li>
							<li class="fw-normal nav-item mb-2"><a
								class=" nav-link p-0  custom-link-rodape"
								href="/ProjetoRua/controller?call=CadastrarHistoriaForm">Conte
									Sua História</a></li>
							<li class="nav-item mb-2"><a
								class="fw-normal nav-link p-0  custom-link-rodape"
								href="/ProjetoRua/controller?call=ProcurarServicosForm">Procurar
									Serviços</a></li>

						</ul>
					</div>

					<div class="col mb-3 d-none d-md-block"></div>

					<div class="col mb-3 text-center text-sm-start col-sm-4">
						<h5 class="fw-bold">Contato</h5>
						<ul class="nav flex-column">
							<li class="nav-item mb-2"><a href="#"
								class="fw-normal nav-link p-0  custom-link-rodape ">Sobre</a></li>
							<li class="nav-item mb-2"><a href="#"
								class="fw-normal nav-link p-0  custom-link-rodape ">info@company.com</a></li>
							<li class="nav-item mb-2"><a href="#"
								class="fw-normal nav-link p-0  custom-link-rodape ">00000
									0000</a></li>
						</ul>
					</div>
				</div>



				<div class="container">

					<div class="row">




						<div class="col-md-6  col-sm-8 nav mt-1 pt-2 mb-0">
							<p>&copy; 2023 Company, Inc.</p>
							&middot; <a class="mx-2 nav-link p-0  custom-link-rodape"
								href="#">Privacy</a> &middot; <a
								class="mx-2 nav-link p-0  custom-link-rodape" href="#">Terms</a>
						</div>

						<div class="col-md-4 d-none d-md-block"></div>
						<div class="col-md-2 col-sm-4 nav mt-1 pt-0 mb-0">
							<p class=" text-body-secondary nav-link">
								<a class=" nav-link p-0  custom-link-rodape" href="#">Back
									to top</a>
							</p>
						</div>
					</div>

				</div>





			</footer>
		</div>
	</div>
	<script src="jquery-3.7.0.min.js"></script>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
		crossorigin="anonymous"></script>

	<c:if test="${ fazerLogin}">
		<script>
			var button = document.getElementById("openModalButton");
			button.click();
		</script>
	</c:if>

	<c:if test="${ not empty usuarioLogado}">
		<script>
			var userName = "${usuarioLogado.getNome()}";
			if (userName) {
				const userInitial = userName.charAt(0).toUpperCase();

				const userInitialElement = document
						.getElementById("userInitial");
				if (userInitialElement) {
					userInitialElement.textContent = userInitial;
				}
			}
		</script>
	</c:if>


	<!-- <script>
		// Função para exibir a imagem após o upload
		document.getElementById("imageUpload").addEventListener(
				"change",
				function() {
					const imagePreview = document
							.getElementById("imagePreview");
					const fileInput = this;

					if (fileInput.files && fileInput.files[0]) {
						const reader = new FileReader();

						reader.onload = function(e) {
							imagePreview.src = e.target.result;
							imagePreview.style.display = "block";
						};

						reader.readAsDataURL(fileInput.files[0]);
					} else {
						imagePreview.src = "";
						imagePreview.style.display = "none";
					}
				});
	</script> -->



	<!-- Adicione o código JavaScript abaixo na seção <head> do seu documento HTML -->
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			const checkbox = document.getElementById("flexCheckDefault");
			const camposAdicionais = document
					.querySelectorAll(".campos-adicionais");

			checkbox.addEventListener("change", function() {
				camposAdicionais.forEach(function(element) {
					if (checkbox.checked) {
						element.style.display = "block";
					} else {
						element.style.display = "none";
					}
				});
			});
		});
	</script>
	<script>
		document.addEventListener("DOMContentLoaded",
				function() {
					const checkboxAlimentacao = document
							.getElementById("flexCheckalimentacao");
					const campoLikertAlimentacao = document
							.querySelectorAll("#campo-likert-alimentacao");

					const checkboxHidratacao = document
							.getElementById("flexCheckhidratacao");
					const campoLikertHidratacao = document
							.querySelectorAll("#campo-likert-hidratacao");

					const checkboxHigiene = document
							.getElementById("flexCheckhigiene");
					const campoLikertHigiene = document
							.querySelectorAll("#campo-likert-higiene");

					const checkboxPreconceito = document
							.getElementById("flexCheckpreconceito");
					const campoLikertPreconceito = document
							.querySelectorAll("#campo-likert-preconceito");

					const checkboxViolencia = document
							.getElementById("flexCheckviolencia");
					const campoLikertViolencia = document
							.querySelectorAll("#campo-likert-violencia");

					const checkboxSaude = document
							.getElementById("flexChecksaude");
					const campoLikertSaude = document
							.querySelectorAll("#campo-likert-saude");

					const checkboxAbrigo = document
							.getElementById("flexCheckabrigo");
					const campoLikertAbrigo = document
							.querySelectorAll("#campo-likert-abrigo");

					const checkboxTrabalho = document
							.getElementById("flexChecktrabalho");
					const campoLikertTrabalho = document
							.querySelectorAll("#campo-likert-trabalho");

					checkboxAlimentacao.addEventListener("change", function() {
						campoLikertAlimentacao.forEach(function(element) {
							if (checkboxAlimentacao.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});

					checkboxHidratacao.addEventListener("change", function() {
						campoLikertHidratacao.forEach(function(element) {
							if (checkboxHidratacao.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});
					checkboxPreconceito.addEventListener("change", function() {
						campoLikertPreconceito.forEach(function(element) {
							if (checkboxPreconceito.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});
					checkboxHigiene.addEventListener("change", function() {
						campoLikertHigiene.forEach(function(element) {
							if (checkboxHigiene.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});

					checkboxSaude.addEventListener("change", function() {
						campoLikertSaude.forEach(function(element) {
							if (checkboxSaude.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});

					checkboxViolencia.addEventListener("change", function() {
						campoLikertViolencia.forEach(function(element) {
							if (checkboxViolencia.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});

					checkboxAbrigo.addEventListener("change", function() {
						campoLikertAbrigo.forEach(function(element) {
							if (checkboxAbrigo.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});
					checkboxTrabalho.addEventListener("change", function() {
						campoLikertTrabalho.forEach(function(element) {
							if (checkboxTrabalho.checked) {
								element.style.display = "block";
							} else {
								element.style.display = "none";
							}
						});
					});

				});
	</script>


	<script>
		// Ativar o tooltip usando Tooltipster
		$(document).ready(function() {
			$('[data-bs-toggle="tooltip"]').tooltip();
		});
	</script>
<script>
    function selecionarAvatar(id) {
        // Atribui o ID ao campo de entrada oculto
        document.getElementById('idAvatar').value = id;

    }
</script>


</body>

</html>