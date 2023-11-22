package com.MedApp.historico2.Service;

import com.MedApp.historico2.Model.M_Resposta;
import com.MedApp.historico2.Model.M_Usuario;
import com.MedApp.historico2.Repository.R_Usuario;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class S_Usuario {

    private static R_Usuario r_usuario;

    public S_Usuario(R_Usuario r_usuario) {
        this.r_usuario = r_usuario;
    }

    public static M_Usuario verificaLogin(String CPF, String senha){
        CPF = S_Generico.limparNumero(CPF);

        if(S_Generico.textoEstaVazio(CPF) || S_Generico.textoEstaVazio(senha)){
            return null;
        }

        return r_usuario.buscarPorCpfSenha(Long.parseLong(CPF), senha);
    }

    public static M_Resposta cadastrarUsuario(String nome, String email, String CPF, String senha, String relacao) {
        boolean podeSalvar = true;
        String mensagem = "";

        if(S_Generico.textoEstaVazio(nome)){
            podeSalvar = false;
            mensagem += "|Nome inválido| ";
        }
        if(!S_Generico.validarEmail(email)){
            podeSalvar = false;
            mensagem += "|E-Mail inválido| ";
        }
        if(S_Generico.textoEstaVazio(CPF) || !S_Generico.validarCPF(CPF)){
            podeSalvar = false;
            mensagem += "|CPF inválido| ";
        }
        if(S_Generico.textoEstaVazio(relacao)){
            podeSalvar = false;
            mensagem += "|Relação inválida| ";
        }
        if(S_Generico.textoEstaVazio(senha) || !S_Generico.validarSenha(senha)){
            podeSalvar = false;
            mensagem += "|Senha inválida| ";
        }

        if(podeSalvar){
            M_Usuario m_usuario = new M_Usuario();
            m_usuario.setNome(nome);
            m_usuario.setEmail(email);
            m_usuario.setCPF(Long.parseLong(S_Generico.limparNumero(CPF)));
            m_usuario.setId_relacao(Long.parseLong(S_Generico.limparNumero(relacao)));
            m_usuario.setSenha(senha);

            try{
                r_usuario.save(m_usuario);
                mensagem += "Cadastro realizado com sucesso, clique em OK para continuar";
            }catch (DataIntegrityViolationException e){
                mensagem += "Algo deu errado, tente novamente";
                podeSalvar = false;
            }
        }
        return new M_Resposta(podeSalvar,mensagem);
    }

    public static M_Resposta updateUsuario(String nome,String email, String senhaAtual,
                                           String novaSenha, String confSenha, Object usuario){
        boolean podeEditar = false;
        String mensagem = "";

        M_Usuario m_usuario = (M_Usuario) usuario;

        if(senhaAtual.equals(m_usuario.getSenha())){
            podeEditar = true;
            if(!novaSenha.equals(confSenha) && !S_Generico.textoEstaVazio(novaSenha) && !S_Generico.validarSenha(novaSenha) && !S_Generico.validarSenha(confSenha)){
                podeEditar = false;
                mensagem += "A nova senha deve ser igual a confirmação de senha, e a senha deve ser uma senha valida";
            }

            if(podeEditar){
                m_usuario.setNome(nome);
                m_usuario.setEmail(email);
                if(!S_Generico.textoEstaVazio(novaSenha)) {
                    m_usuario.setSenha(novaSenha);
                }
                try {
                    r_usuario.save(m_usuario);
                    mensagem += "Perfil atualizado com sucesso";
                }catch (DataIntegrityViolationException e){
                    podeEditar = false;
                    mensagem += "Falha ao tentar atualizar o cadastro: "+ e.getMessage();
                }
            }
        }else{
            mensagem += "Senha inválida, o cadastro não pode ser editado!";
        }
        return new M_Resposta(podeEditar,mensagem);
    }
}
