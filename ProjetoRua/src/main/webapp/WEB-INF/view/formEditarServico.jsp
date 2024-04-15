<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>

<c:url value="/controller" var="controller" />

<%@ page
	import="java.util.List,br.com.tcc.projetorua.model.Beneficiario,br.com.tcc.projetorua.model.Prestador"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>


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






	<div class="container" id="cabecalho">
		<header
			class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
			<div class="col-md-2 col-sm-2  mb-2 mb-md-0">
				<a href="/"
					class="d-inline-flex link-body-emphasis text-decoration-none">
					<svg class="bi" width="40" height="32" role="img"
						aria-label="Bootstrap">
              <use xlink:href="#bootstrap" />
            </svg>
				</a>
			</div>




			<ul
				class="nav col-12 col-md-8 col-sm-8  mb-2 justify-content-center mb-md-0 ">
				<li><a href="/ProjetoRua/controller?call=Home "
					class="nav-link px-2 custom-link mx-1 ">Home </a></li>
				<li><a href="/ProjetoRua/controller?call=ProcurarServicosForm"
					class="nav-link px-2 custom-link mx-1">Preciso de Ajuda </a></li>


				<c:if test="${ empty usuarioLogado}">

					<li><a
						href="/ProjetoRua/controller?call=CadastrarPrestadorAjudaForm"
						class="nav-link px-2 custom-link mx-1">Oferecer Ajuda </a></li>

				</c:if>

				<c:if test="${ not empty usuarioLogado}">




					<li><a href="/ProjetoRua/controller?call=ListarServicoForm"
						class="nav-link px-2 custom-link mx-1">Oferecer Ajuda </a></li>

				</c:if>




				<c:if test="${ empty usuarioLogado}">

					<li><a
						href="/ProjetoRua/controller?call=CadastrarBeneficiarioForm"
						class="nav-link px-2 custom-link mx-1">Conte Sua História </a></li>

				</c:if>

				<c:if test="${ not empty usuarioLogado}">

					<li><a
						href="/ProjetoRua/controller?call=ListarHistoriaForm"
						class="nav-link px-2 custom-link mx-1">Conte Sua História </a></li>


				</c:if>




				<li><a href="#" class="nav-link px-2 custom-link mx-1">Sobre</a></li>



			</ul>

			<div class="col-md-2 col-sm-2  text-end">
				<div class="dropdown text-end">

					<c:if test="${not empty usuarioLogado}">
						<a href="#"
							class="d-block link-dark text-decoration-none dropdown-toggle"
							id="dropdownUser1" data-bs-toggle="dropdown"
							aria-expanded="false"> <span id="userInitial"
							class="rounded-circle "
							style="border: 2px solid #000; width: 30px; height: 30px; border-radius: 50%; display: inline-block; text-align: center;"></span>
						</a>
					</c:if>


					<ul class="dropdown-menu text-small"
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
				</div>
				<c:if test="${ empty usuarioLogado}">
					<button type="button" id="openModalButton"
						class="btn btn-outline-primary me-2" data-bs-toggle="modal"
						data-bs-target="#loginModal">Login</button>
				</c:if>

				<c:if test="${ not empty usuarioLogado}">
					<button type="button" style="display: none;" id="openModalButton"
						data-bs-toggle="modal" data-bs-target="#loginModal">Login</button>
				</c:if>





			</div>


		</header>
	</div>




	<main id="principal">

		<div class="container">
			<div class="d-flex justify-content-center ">
				<form class="row g-3" accept-charset="UTF-8"
					action="/ProjetoRua/controller" method="post">

					<div class="col-md-2"></div>
					<div class="col-md-8">
						<label for="validationDefault02" class="form-label ">Descrição</label>

						<textarea class="form-control" name="descricao"
							id="validationDefault02" rows="3" maxlength="1500" required></textarea>
					</div>

					<div class="col-md-2"></div>

					<!-- segunda linha -->
					<div class="col-md-2"></div>
					<div class="col-md-3 ">

						<div class="px-2">
							<div class="row">
								<label for="validationHoraInicio" class="form-label ">Hora
									Inicial</label> <input class="form-control" type="time"
									id="validationHoraInicio" name="horaInicio" required>
							</div>
							<div class="row">
								<label for="validationHoraFinal" class="form-label ">Hora
									Final</label> <input class="form-control" type="time"
									id="validationHoraFinal" name="horaFinal" required>
							</div>
						</div>



					</div>

					<div class="col-md-3">

						<div class="px-2">
							<div class="row">
								<label for="validationDataInicio" class="form-label ">Data
									Inicial</label> <input class="form-control" type="date"
									id="validationDataInicio" name="dataInicial" required>
							</div>

							<div class="row">
								<label for="validationDataFinal" class="form-label ">Data
									Final</label> <input class="form-control" type="date"
									id="validationDataFinal" name="dataFinal" required>
							</div>
						</div>




					</div>

					<div class="col-md-2">


						<div class="px-2">
							<div class="row">
								<label for="validationCategoria" class="form-label ">Categoria</label>
								<select class="form-control" id="validationCategoria"
									name="categoria">
									<option value="Alimentacao">Alimentação</option>
									<option value="Abrigo">Abrigo</option>
									<option value="Saude">Saúde</option>
									<option value="Emprego">Emprego</option>
								</select>
							</div>

							<div class="row">
								<label for="validationFrequencia" class="form-label ">Frequência</label>
								<select class="form-control" id="validationFrequencia"
									name="frequencia">
									<option value="diario">diário</option>
									<option value="semanal">semanal</option>
									<option value="quinzenal">quinzenal</option>
									<option value="mensal">mensal</option>
								</select>



							</div>
						</div>

					</div>
					<div class="col-md-2"></div>




					<!-- terceira linha -->

					<div class="col-md-2"></div>


					<div class="col-md-3">
						<label for="cep" class="form-label">Cep</label> <input
							class="form-control" name="cep" type="text" id="cep" value=""
							size="10" maxlength="9" onblur="pesquisacep(this.value);"
							required />
					</div>

					<input type="hidden" class="form-control" id="ibge" name="ibge"
						readonly required>

					<div class="col-md-5">
						<label for="rua" class="form-label">Rua</label> <input type="text"
							class="form-control" id="rua" name="rua" required>
					</div>
					<div class="col-md-2"></div>
					<!-- quarta linha -->

					<div class="col-md-2"></div>

					<div class="col-md-2">
						<label for="bairro" class="form-label">Bairro</label> <input
							type="text" class="form-control" id="bairro" name="bairro"
							value="" required>
					</div>

					<div class="col-md-2">
						<label for="cidade" class="form-label">Cidade</label> <input
							type="text" class="form-control" id="cidade" name="cidade"
							readonly required>
					</div>
					<div class="col-md-2">
						<label for="uf" class="form-label">Estado</label> <input
							type="text" class="form-control" id="uf" name="uf" readonly
							required>
					</div>
					<div class="col-md-2">
						<label for="numero" class="form-label">Número</label> <input
							type="text" class="form-control" id="numero" name="numero"
							required>
					</div>
					<div class="col-md-2"></div>

					<!-- quinta linha -->
					
					
					

					<input type="hidden" name="call" value="CadastrarServico">
					

					<!-- sexta linha -->
					<div class="col-md-2"></div>

					<div class="col-md-8">


						<a href="/ProjetoRua/controller?call=ListarServicoForm"
							class="btn btn-primary col-md-3 col-sm-4 mx-0 px-0">Cancelar</a>
						<div class="btn  col-md-1"></div>


						<button
							class="btn btn-primary col-md-3 col-sm-4  mr-2 px-0 float-end"
							type="submit">Salvar</button>

					</div>

					





				</form>




			</div>
		</div>


	</main>



	<!-- Modal de login -->
	<div class="modal fade" id="loginModal" tabindex="-1"
		aria-labelledby="loginModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered">
			<div class="modal-content">
				<!-- Conteúdo do modal de login -->
				<div class="modal-header">
					<h5 class="modal-title" id="loginModalLabel">Conecte-se</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<section class="vh-80 gradient-custom">
						<div class="container py-4 h-100">
							<div
								class="row d-flex justify-content-center align-items-center h-100">
								<div class="col-12 col-md-8 col-lg-6 col-xl-5">
									<form action="/ProjetoRua/controller" method="post">

										<div class="card bg-dark text-white"
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
														Não tem uma conta? <a href="#!"
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

	<div class="container" id="rodape">
		<footer>
			<div
				class="row row-cols-1 row-cols-sm-2 row-cols-md-5 pt-4 pb-3 mt-3 mb-3 border-top border-bottom">


				<div class="col mb-3">
					<a href="/"
						class="d-flex align-items-center mb-3 link-body-emphasis text-decoration-none">
						<svg class="bi me-2" width="40" height="32">
              <use xlink:href="#bootstrap" />
            </svg>
					</a>
					<p class="text-body-secondary">&copy; 2023</p>
				</div>

				<div class="col mb-3"></div>

				<div class="col mb-3">
					<h5 class="fw-bold">Links Rápidos</h5>
					<ul class="nav flex-column">
						<li class="nav-item mb-2"><a 		class="fw-normal nav-link p-0 text-body-secondary custom-link-rodape" href="/ProjetoRua/controller?call=Home">Home</a></li>
						<li class="fw-normal nav-item mb-2">
						<a class=" nav-link p-0 text-body-secondary custom-link-rodape" href="/ProjetoRua/controller?call=CadastrarHistoriaForm">Conte Sua História</a></li>
						<li class="nav-item mb-2">
						<a 	class="fw-normal nav-link p-0 text-body-secondary custom-link-rodape" href="/ProjetoRua/controller?call=ProcurarServicosForm">Procurar
								Serviços</a></li>

					</ul>
				</div>

				<div class="col mb-3">
					<h5></h5>
					<ul class="nav flex-column">
						<li class="nav-item mb-2"><a href="#"
							class="nav-link p-0 text-body-secondary custom-link-rodape "></a></li>
						<li class="nav-item mb-2"><a href="#"
							class="nav-link p-0 text-body-secondary custom-link-rodape "></a></li>
						<li class="nav-item mb-2"><a href="#"
							class="nav-link p-0 text-body-secondary custom-link-rodape "></a></li>

					</ul>
				</div>

				<div class="col mb-3">
					<h5 class="fw-bold">Contato</h5>
					<ul class="nav flex-column">
						<li class="nav-item mb-2"><a href="#"
							class="fw-normal nav-link p-0 text-body-secondary custom-link-rodape ">Sobre</a></li>
						<li class="nav-item mb-2"><a href="#"
							class="fw-normal nav-link p-0 text-body-secondary custom-link-rodape ">info@company.com</a></li>
						<li class="nav-item mb-2"><a href="#"
							class="fw-normal nav-link p-0 text-body-secondary custom-link-rodape ">00000
								0000</a></li>
					</ul>
				</div>
			</div>



			<p class="float-end text-body-secondary nav-link">
				<a class="nav-link p-0 text-body-secondary custom-link-rodape" href="#">Back to
					top</a>
			</p>

			

			<div class="nav mt-1 mb-3">
			<p>&copy; 2023 Company, Inc.</p>
			&middot; 	<a	class="mx-2 nav-link p-0 text-body-secondary custom-link-rodape" href="#">Privacy</a> 
				&middot; <a	class="mx-2 nav-link p-0 text-body-secondary custom-link-rodape" href="#">Terms</a>
			</div>


		</footer>
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


</body>

</html>