package br.com.algaworks.algamoney.enumerator;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Estado {

	AC("Acre"), AL("Alagoas"), AP("Amapá"), AM("Amazonas"), BA("Bahia"), CE("Ceará"), DF("Distrito Federal"), ES(
			"Espírito Santo"), GO("Goiás"), MA("Maranhão"), MT("Mato Grosso"), MS("Mato Grosso do Sul"), MG(
					"Minas Gerais"), PA("Pará"), PB("Paraíba"), PR("Paraná"), PE("Pernambuco"), PI("Piauí"), RJ(
							"Rio de Janeiro"), RN("Rio Grande do Norte"), RS("Rio Grande do Sul"), RO("Rondônia"), RR(
									"Roraima"), SC("Santa Catarina"), SP("São Paulo"), SE("Sergipe"), TO("Tocantins");

	private String nome;

	Estado(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonCreator
	public static Estado fromName(String name) {
		for (Estado estado : Estado.values()) {
			if (estado.name().equalsIgnoreCase(name)) {
				return estado;
			}
		}
		return null;
	}

	public static Estado fromNome(String nome) {
		for (Estado estado : Estado.values()) {
			if (estado.nome.equalsIgnoreCase(nome)) {
				return estado;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return String.valueOf(nome);
	}
}
