package br.com.algaworks.algamoney.enumerator;
public enum ScopesOauth {
	
	READ("read"),
	WRITE("write");
	
	private String scope;
	
	ScopesOauth(String scope){
		this.scope = scope;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}

