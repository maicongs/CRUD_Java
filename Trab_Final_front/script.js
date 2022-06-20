var idlivros = 0;
listar();
var myModal = new bootstrap.Modal(document.getElementById('cadastro'));

function listar(){
  var requestOptions = {
      method: 'GET',
      redirect: 'follow'
    };
  
    fetch("http://localhost:8080/TrabFinal_LP3_Java/RestLivros", requestOptions)
      .then(response => response.json())
      .then(function(result){
        var dados = "<th>Titulo</th>";
        dados += "<th>Autor</th>";
        dados += "<th>Ano de Lançamento</th>";
        dados += "<th></th>";
        dados += "<th></th>";
        for (const i in result) {
          dados += "<tr>"
            + "<td>" + result[i].titulo + "</td>"
            + "<td>" + result[i].autor + "</td>"
            + "<td>" + result[i].ano_lancamento + "</td>"
            + "<td><a class='btn btn-primary' onclick='alterar(" + result[i].idlivros + ")'> Alterar</a></td>"
            + "<td><a class='btn btn-danger' onclick='excluir(" + result[i].idlivros + ")'> Excluir</a></td>"
            + "</tr>"
        }
        document.getElementById("dados").innerHTML = dados;
      })
      .catch(error => console.log('error', error));
    }

    function novo(){
      idlivros = 0;

      document.getElementById("titulo").value = "";
      document.getElementById("autor").value = "";
      document.getElementById("ano_lancamento").value = "";
      myModal.show();
    }

    function salvar(){
      var metodo;
      if (idlivros > 0){
        metodo = "PUT";
      }else{
        metodo = "POST";
      }

      myModal.hide();
      var l = {};
      l.idlivros = idlivros;
      l.titulo = document.getElementById("titulo").value;
      l.autor = document.getElementById("autor").value;
      l.ano_lancamento = document.getElementById("ano_lancamento").value;
      var raw = JSON.stringify(l);
      console.log(raw);
      if(p.titulo == ""){
        alert("Titulo é obrigatório");
        return;
      }
      var requestOptions = {
        method: metodo,
        body: raw,
        redirect: 'follow'
      };

      fetch("http://localhost:8080/TrabFinal_LP3_Java/RestLivros", requestOptions)
        .then(response => response.text())
        .then(function(result){
          listar();
        })
        .catch(error => console.log('error', error));
      }

      function alterar(id){
        idlivros = id;

        var requestOptions = {
          method: 'GET',
          redirect: 'follow'
        };
        
        fetch("http://localhost:8080/TrabFinal_LP3_Java/RestLivros/" + idlivros, requestOptions)
          .then(response => response.json())
          .then(function(result){
            console.log(result);
            document.getElementById("titulo").value = result.titulo;
            document.getElementById("autor").value = result.autor;
            document.getElementById("ano_lancamento").value = result.ano_lancamento;
            myModal.show();
          })
          .catch(error => console.log('error', error));
      }

      function excluir(idlivros){
        

        var requestOptions = {
          method: 'DELETE',
          redirect: 'follow'
        };
        
        fetch("http://localhost:8080/TrabFinal_LP3_Java/RestLivros" + idlivros, requestOptions)
          .then(response => response.text())
          .then(function(result){
            listar();
          })
          .catch(error => console.log('error', error));
      }