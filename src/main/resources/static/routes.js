function controleRotasGet(url){
    switch(url){
        case "/logout":
            gerarSwal(url);
            break;
             case "/editUsuario":
                                        $.get(url,function(data){
                                            $(".container").html(data);
                                            $("#salvar").click(salvarPerfil);
                                        });
                                        break;
        default:
            $.get(url,function(data){
                $(".container").html(data);
            });
    }
}