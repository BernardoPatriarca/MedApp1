function salvarPerfil(){
    let nome = $("#nome").val();
    let email = $("#email").val();
    let relacao = $("#relacao option:selected").val();
    let CPF = $("#CPF").val();
    let senhaAtual = $("#senhaAtual").val();
    let novaSenha = $("#novaSenha").val();
    let confSenha = $("#confSenha").val();

    $.ajax({
        type: "POST",
        url: "/editUsuario",
        data:{
            nome:nome,
            email:email,
            relacao:relacao,
            CPF:CPF,
            senhaAtual:senhaAtual,
            novaSenha:novaSenha,
            confSenha:confSenha
        },
        success: function(data){
            if(data.sucesso){
                alertaSucesso("Cadastro atualizado com sucesso");
            }else{
                alert(data.mensagem);
            }
        },
        error: function(){
            alert("Deu ruim");
        }
    })
}